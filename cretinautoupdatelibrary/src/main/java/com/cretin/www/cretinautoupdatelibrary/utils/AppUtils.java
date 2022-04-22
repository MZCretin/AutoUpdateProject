package com.cretin.www.cretinautoupdatelibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;

import com.cretin.www.cretinautoupdatelibrary.R;
import com.cretin.www.cretinautoupdatelibrary.interfaces.OnDialogClickListener;

import java.io.File;

/**
 * @date: on 2019-10-10
 * @author: a112233
 * @email: mxnzp_life@163.com
 * @desc: 添加描述
 */
public class AppUtils {

    private static float density;

    /**
     * 安装app
     *
     * @param context
     * @param file
     */
    public static void installApkFile(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        if (context.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
            context.startActivity(intent);
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dpValue) {
        if (density == 0)
            density = Resources.getSystem().getDisplayMetrics().density;
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * 获取apk的安装路径
     *
     * @return
     */
    public static String getAppLocalPath(Context context,String versionName) {
        // apk 保存名称
        String apkName = AppUtils.getAppName(AppUpdateUtils.getInstance().getContext());
        return getAppRootPath(context) + "/" + apkName + "_" + versionName + ".apk";
    }

    /**
     * 获取apk存储的根目录
     *
     * @return
     */
    public static String getAppRootPath(Context context) {
        //构建下载路径
        String packageName = AppUpdateUtils.getInstance().getContext().getPackageName();
        return  context.getExternalCacheDir()+ "/" + packageName + "/apks";
    }

    /**
     * 显示通用对话框
     *
     * @param activity
     * @param msg
     * @param clickListener
     * @param cancelable
     * @param title
     * @param cancelText
     * @param okText
     */
    public static void showDialog(Activity activity, String msg, final OnDialogClickListener clickListener, boolean cancelable, String title, String cancelText, String okText) {
        if (!activity.isFinishing()) {
            new AlertDialog.Builder(activity, R.style.AlertDialog)
                    .setTitle(title)
                    .setMessage(msg)
                    .setPositiveButton(okText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (clickListener != null) {
                                clickListener.onOkClick(dialog);
                            }
                        }
                    })
                    .setNegativeButton(cancelText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (clickListener != null) {
                                clickListener.onCancelClick(dialog);
                            }
                        }
                    })
                    .setCancelable(cancelable)
                    .create()
                    .show();
        }
    }

    /**
     * 删除文件
     *
     * @param filePath
     */
    public static void deleteFile(String filePath) {
        if (filePath == null) {
            return;
        }
        File file = new File(filePath);
        try {
            if ((file.isFile())) {
                file.delete();
            }
        } catch (Exception e) {

        }
    }

    /**
     * 删除文件夹的所有文件
     *
     * @param file
     * @return
     */
    public static boolean delAllFile(File file) {
        if (file == null || !file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null)
                for (File f : files) {
                    delAllFile(f);
                }
        }
        return file.delete();
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        PackageInfo packInfo = getPackInfo(context);
        if (packInfo != null) {
            versionName = packInfo.versionName;
        }
        return versionName;
    }

    /**
     * 获得apkinfo
     *
     * @param context
     * @return
     */
    private static PackageInfo getPackInfo(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo;
    }

    /**
     * 获得apk版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        PackageInfo packInfo = getPackInfo(context);
        if (packInfo != null) {
            versionCode = packInfo.versionCode;
        }
        return versionCode;
    }
}
