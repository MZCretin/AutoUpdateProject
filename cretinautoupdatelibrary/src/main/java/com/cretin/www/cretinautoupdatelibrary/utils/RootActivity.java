package com.cretin.www.cretinautoupdatelibrary.utils;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import com.cretin.www.cretinautoupdatelibrary.R;
import com.cretin.www.cretinautoupdatelibrary.activity.UpdateType1Activity;
import com.cretin.www.cretinautoupdatelibrary.interfaces.AppDownloadListener;
import com.cretin.www.cretinautoupdatelibrary.interfaces.AppUpdateInfoListener;
import com.cretin.www.cretinautoupdatelibrary.interfaces.MD5CheckListener;
import com.cretin.www.cretinautoupdatelibrary.interfaces.OnDialogClickListener;
import com.cretin.www.cretinautoupdatelibrary.model.DownloadInfo;
import com.cretin.www.cretinautoupdatelibrary.service.UpdateService;
import com.cretin.www.cretinautoupdatelibrary.utils.AppUpdateUtils;
import com.cretin.www.cretinautoupdatelibrary.utils.AppUtils;
import com.cretin.www.cretinautoupdatelibrary.utils.NetWorkUtils;
import com.cretin.www.cretinautoupdatelibrary.utils.ResUtils;

/**
 * @date: on 2019-10-11
 * @author: a112233
 * @email: mxnzp_life@163.com
 * @desc: 添加一些常用的处理
 */
public abstract class RootActivity extends AppCompatActivity {

    public static final String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final int PERMISSION_CODE = 1001;
    private static final int NOTIFICATION_PERMISSION_CODE = 1002;

    public DownloadInfo downloadInfo;

    private AppDownloadListener appDownloadListener = obtainDownloadListener();

    private MD5CheckListener md5CheckListener = obtainMD5CheckListener();

    private AppUpdateInfoListener appUpdateInfoListener = obtainAppUpdateInfoListener();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        downloadInfo = getIntent().getParcelableExtra("info");
        AppUpdateUtils.getInstance().addAppDownloadListener(appDownloadListener);
        AppUpdateUtils.getInstance().addAppUpdateInfoListener(appUpdateInfoListener);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        downloadInfo = getIntent().getParcelableExtra("info");
        AppUpdateUtils.getInstance().addAppDownloadListener(appDownloadListener);
        AppUpdateUtils.getInstance().addAppUpdateInfoListener(appUpdateInfoListener);
    }

    /**
     * 检查下载
     */
    private void checkDownload() {
        // 动态注册广播，8.0 静态注册收不到
        // 开启服务注册，避免直接在Activity中注册广播生命周期随Activity终止而终止
        if (AppUpdateUtils.getInstance().getUpdateConfig().isShowNotification())
            startService(new Intent(this, UpdateService.class));

        if (AppUpdateUtils.getInstance().getUpdateConfig().isAutoDownloadBackground()) {
            doDownload();
        } else {
            if (!NetWorkUtils.getCurrentNetType(this).equals("wifi")) {
                AppUtils.showDialog(this, ResUtils.getString(R.string.wifi_tips), new OnDialogClickListener() {
                    @Override
                    public void onOkClick(DialogInterface dialog) {
                        dialog.dismiss();
                        //下载
                        doDownload();
                    }

                    @Override
                    public void onCancelClick(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                }, true, ResUtils.getString(R.string.tips), ResUtils.getString(R.string.cancel), ResUtils.getString(R.string.confirm));
            } else {
                doDownload();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //存储权限已经获取
                checkDownload();
            } else {
                //权限未被给予
                AppUtils.showDialog(this, ResUtils.getString(R.string.permission_to_store), new OnDialogClickListener() {
                    @Override
                    public void onOkClick(DialogInterface dialog) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelClick(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                }, true, "", ResUtils.getString(R.string.cancel), ResUtils.getString(R.string.go_to));
            }
        } else if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //通知权限已经获取，继续检查存储权限
                requestPermission();
            } else {
                //通知权限被拒绝，但仍可以继续下载（只是无法显示通知）
                checkDownload();
            }
        }
    }

    /**
     * 取消任务
     */
    public void cancelTask() {
        AppUpdateUtils.getInstance().cancelTask();
    }

    /**
     * 执行下载
     */
    private void doDownload() {
        AppUpdateUtils.getInstance()
                .addMd5CheckListener(md5CheckListener)
                .download(downloadInfo);
    }

    public abstract AppDownloadListener obtainDownloadListener();

    /**
     * 如果需要监听MD5校验结果 自行重写此方法
     *
     * @return
     */
    public MD5CheckListener obtainMD5CheckListener() {
        return null;
    }

    /**
     * 如果需要知道是否新版本更新的信息 自行重写此方法
     *
     * @return
     */
    public AppUpdateInfoListener obtainAppUpdateInfoListener() {
        return null;
    }

    /**
     * 获取权限
     */
    public void requestPermission() {
        // Android 15 (API 35) 及以上版本的权限处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ 需要通知权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) 
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, 
                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_CODE);
                return;
            }
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11+ 不再需要WRITE_EXTERNAL_STORAGE权限
            // 使用应用专属存储目录
            checkDownload();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android 6.0-10 需要存储权限
            int writePermission = ContextCompat.checkSelfPermission(this, permission);
            if (writePermission == PackageManager.PERMISSION_GRANTED) {
                checkDownload();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSION_CODE);
            }
        } else {
            // Android 6.0以下直接执行
            checkDownload();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.dialog_enter, R.anim.dialog_out);
    }

    @Override
    public void onBackPressed() {
        if (downloadInfo != null) {
            if (!downloadInfo.isForceUpdateFlag()) {
                super.onBackPressed();
            }
        } else
            super.onBackPressed();
    }

    /**
     * 下载
     */
    public void download() {
        if (!AppUpdateUtils.isDownloading()) {
            requestPermission();
        } else {
            if (appDownloadListener != null)
                appDownloadListener.downloadStart();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUpdateUtils.getInstance().clearAllListener();
    }

    /**
     * 启动Activity
     *
     * @param context
     * @param info
     */
    public static void launchActivity(Context context, DownloadInfo info, Class cla) {
        Intent intent = new Intent(context, cla);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP + Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("info", info);
        context.startActivity(intent);
    }
}
