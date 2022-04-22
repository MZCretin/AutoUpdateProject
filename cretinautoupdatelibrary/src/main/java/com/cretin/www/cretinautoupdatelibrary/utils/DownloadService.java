package com.cretin.www.cretinautoupdatelibrary.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.cretin.www.cretinautoupdatelibrary.R;
import com.cretin.www.cretinautoupdatelibrary.model.UpdateConfig;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class DownloadService extends Service {
    private static final int NOTIFY_ID = 0;

    private Context mContext = this;
    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private Notification.Builder builder;
    private DownFileAsyncTask downFileAsyncTask;
    private String downUrl;
    private String appName;
    private int showType;

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        downUrl = intent.getStringExtra("downUrl");
        appName = intent.getStringExtra("appName");
        showType = intent.getIntExtra("type", 0);
        if (!TextUtils.isEmpty(downUrl)) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //后台下载或者前台展示后台下载都是调用这个
                if (showType == UpdateConfig.TYPE_NITIFICATION || showType
                        == UpdateConfig.TYPE_DIALOG_WITH_BACK_DOWN) {
                    builder = new Notification.Builder(mContext);
                    RemoteViews contentView = new RemoteViews(mContext.getPackageName(), R.layout.layout_notification);
                    if (intent.getIntExtra("icRes", 0) != 0) {
                        builder.setSmallIcon(intent.getIntExtra("icRes", 0));
                        contentView.setImageViewResource(R.id.iv_icon, intent.getIntExtra("icRes", 0));
                    } else {
                        builder.setSmallIcon(R.mipmap.ic_launcher); //设置图标
                    }
                    if (TextUtils.isEmpty(appName))
                        contentView.setTextViewText(R.id.fileName, "正在下载...");
                    else
                        contentView.setTextViewText(R.id.fileName, appName + "正在下载...");
                    builder.setContent(contentView);
                    mNotification = builder.build();
                    mNotificationManager.notify(NOTIFY_ID, mNotification);
                } else {
                    mNotificationManager = null;
                }
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                final String fileName = filePath + "/" + getPackgeName(this) + "-v" + getVersionName(this) + ".apk";
                downFileAsyncTask = new DownFileAsyncTask();
                downFileAsyncTask.execute(fileName);
            } else {
                Toast.makeText(mContext, "没有挂载的SD卡", Toast.LENGTH_SHORT).show();
            }
        } else {
            throw new RuntimeException("下载地址不能为空");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (downFileAsyncTask != null) {
            downFileAsyncTask.cancel(true);
        }
    }

    //异步任务下载文件
    class DownFileAsyncTask extends AsyncTask<String, Integer, File> {

        @Override
        protected File doInBackground(String... params) {
            try {
                FileOutputStream fos = null;
                BufferedInputStream bis = null;
                HttpURLConnection httpUrl = null;
                URL url = null;
                byte[] buf = new byte[2048];
                int size = 0;

                //建立链接
                url = new URL(downUrl);
                httpUrl = (HttpURLConnection) url.openConnection();
                //设置网络连接超时时间5S
                httpUrl.setConnectTimeout(10 * 1000);
                //连接指定的资源
                httpUrl.connect();
                //获取网络输入流
                bis = new BufferedInputStream(httpUrl.getInputStream());
                //建立文件
                File file = new File(params[0]);
                if (!file.exists()) {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                }
                fos = new FileOutputStream(params[0]);

                final long total = httpUrl.getContentLength();
                long sum = 0;

                //保存文件
                while ((size = bis.read(buf)) != -1) {
                    sum += size;
                    fos.write(buf, 0, size);
                    publishProgress((int) (sum * 100 / total));
                }
                fos.close();
                bis.close();
                httpUrl.disconnect();
                return file;
            } catch (IOException e) {
                //发送特定action的广播
                Intent intent = new Intent();
                intent.setAction("android.intent.action.MY_RECEIVER");
                intent.putExtra("type", "err");
                intent.putExtra("err", e.toString());
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (!autoCancel)
                Toast.makeText(mContext, "已取消下载", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            if (file != null) {
                installApkFile(mContext, file);
            } else {
                if (showType != UpdateConfig.TYPE_DIALOG) {
                    mNotificationManager.cancel(NOTIFY_ID);
                    mNotification.contentView = null;
                }
            }
            stopSelf(); //停止服务
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int rate = values[0];
            if ((rate % 1 == 0 || rate == 100) && rate != lastPosition) {
                lastPosition = rate;
                if (showType == UpdateConfig.TYPE_NITIFICATION || showType
                        == UpdateConfig.TYPE_DIALOG_WITH_BACK_DOWN) {
                    if (rate < 100) {
                        //更新进度
                        RemoteViews contentView = mNotification.contentView;
                        contentView.setTextViewText(R.id.rate, rate + "%");
                        contentView.setProgressBar(R.id.progress, 100, rate, false);
                        // 最后别忘了通知一下,否则不会更新
                        mNotificationManager.notify(NOTIFY_ID, mNotification);
                    } else {
                        //下载完毕后变换通知形式
                        mNotificationManager.cancel(NOTIFY_ID);
                        mNotification.contentView = null;
                    }

                    autoCancel = false;
                    if (rate >= 100) {
                        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
                        autoCancel = true;
                    }
                }
                //发送特定action的广播
                Intent intent = new Intent();
                intent.setAction("android.intent.action.MY_RECEIVER");
                intent.putExtra("type", "doing");
                intent.putExtra("progress", rate);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            }
        }
    }

    private int lastPosition;
    private boolean autoCancel;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private String getVersionName(Context context) {
        String versionName = "";
        PackageInfo packInfo = getPackInfo(context);
        if (packInfo != null) {
            versionName = packInfo.versionName;
        }
        return versionName;
    }

    /**
     * 获得apkPackgeName
     *
     * @param context
     * @return
     */
    public static String getPackgeName(Context context) {
        String packName = "";
        PackageInfo packInfo = getPackInfo(context);
        if (packInfo != null) {
            packName = packInfo.packageName;
        }
        return packName;
    }

    /**
     * 获得apkinfo
     *
     * @param context
     * @return
     */
    public static PackageInfo getPackInfo(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo;
    }

    //安装
    public static void installApkFile(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //兼容7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (context.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
            context.startActivity(intent);
        }
    }
}  