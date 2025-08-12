package com.cretin.www.cretinautoupdatelibrary.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Android 15 兼容性工具类
 * 
 * @author AutoUpdateProject
 * @date 2024-12-19
 */
public class Android15CompatUtils {
    
    private static final int REQUEST_INSTALL_PACKAGES_CODE = 1003;
    
    /**
     * 检查是否支持通知权限
     * 
     * @param context
     * @return
     */
    public static boolean isNotificationPermissionSupported(Context context) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU;
    }
    
    /**
     * 检查通知权限是否已授予
     * 
     * @param context
     * @return
     */
    public static boolean hasNotificationPermission(Context context) {
        if (!isNotificationPermissionSupported(context)) {
            return true; // 低版本默认有权限
        }
        return ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) 
                == PackageManager.PERMISSION_GRANTED;
    }
    
    /**
     * 请求通知权限
     * 
     * @param activity
     * @param requestCode
     */
    public static void requestNotificationPermission(Activity activity, int requestCode) {
        if (isNotificationPermissionSupported(activity)) {
            ActivityCompat.requestPermissions(activity, 
                new String[]{Manifest.permission.POST_NOTIFICATIONS}, requestCode);
        }
    }
    
    /**
     * 检查安装权限
     * 
     * @param context
     * @return
     */
    public static boolean canInstallPackages(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return context.getPackageManager().canRequestPackageInstalls();
        }
        return true;
    }
    
    /**
     * 请求安装权限
     * 
     * @param activity
     */
    public static void requestInstallPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!canInstallPackages(activity)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                activity.startActivityForResult(intent, REQUEST_INSTALL_PACKAGES_CODE);
            }
        }
    }
    
    /**
     * 检查是否需要存储权限
     * Android 11+ 不再需要WRITE_EXTERNAL_STORAGE权限
     * 
     * @return
     */
    public static boolean needsStoragePermission() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.R;
    }
    
    /**
     * 获取应用专属存储目录
     * Android 11+ 推荐使用应用专属目录
     * 
     * @param context
     * @return
     */
    public static String getAppSpecificStoragePath(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11+ 使用应用专属外部存储
            return context.getExternalFilesDir(null).getAbsolutePath();
        } else {
            // 低版本继续使用原有逻辑
            return context.getExternalCacheDir().getAbsolutePath();
        }
    }
    
    /**
     * 是否支持前台服务类型
     * Android 14+ 需要明确指定前台服务类型
     * 
     * @return
     */
    public static boolean supportsForegroundServiceTypes() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE;
    }
    
    /**
     * 检查Edge-to-Edge显示支持
     * Android 15强制启用Edge-to-Edge
     * 
     * @return
     */
    public static boolean isEdgeToEdgeEnforced() {
        return Build.VERSION.SDK_INT >= 35; // Android 15
    }
    
    /**
     * 获取当前Android版本信息
     * 
     * @return
     */
    public static String getAndroidVersionInfo() {
        return "Android " + Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ")";
    }
}
