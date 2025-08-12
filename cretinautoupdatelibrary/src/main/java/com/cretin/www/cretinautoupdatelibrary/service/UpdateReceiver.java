package com.cretin.www.cretinautoupdatelibrary.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;

import com.cretin.www.cretinautoupdatelibrary.R;
import com.cretin.www.cretinautoupdatelibrary.utils.AppUpdateUtils;
import com.cretin.www.cretinautoupdatelibrary.utils.AppUtils;
import com.cretin.www.cretinautoupdatelibrary.utils.ResUtils;

/**
 * Created by cretin on 2017/3/9.
 */

public class UpdateReceiver extends BroadcastReceiver {

    private static final String notificationChannel = "10000";

    /**
     * 进度key
     */
    public static final String PROGRESS = "app.progress";

    /**
     * ACTION_UPDATE
     */
    public static final String DOWNLOAD_ONLY = "app.update";

    /**
     * ACTION_RE_DOWNLOAD
     */
    public static final String RE_DOWNLOAD = "app.re_download";

    /**
     * 取消下载
     */
    public static final String CANCEL_DOWNLOAD = "app.download_cancel";


    public static final int REQUEST_CODE = 1001;

    private int lastProgress;

    /**
     * 发送进度
     *
     * @param context
     * @param progress
     */
    public static void send(Context context, int progress) {
        Intent intent = new Intent(context.getPackageName() + DOWNLOAD_ONLY);
        intent.putExtra(PROGRESS, progress);
        context.sendBroadcast(intent);
    }

    /**
     * 取消下载
     *
     * @param context
     */
    public static void cancelDownload(Context context) {
        Intent intent = new Intent(context.getPackageName() + CANCEL_DOWNLOAD);
        context.sendBroadcast(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        int notifyId = 1;
        if ((context.getPackageName() + DOWNLOAD_ONLY).equals(action)) {
            //下载
            int progress = intent.getIntExtra(PROGRESS, 0);

            NotificationManager systemService =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (progress != -1) {
                lastProgress = progress;
            }

            showNotification(context, notifyId, progress, notificationChannel, systemService);

            // 下载完成
            if (progress == 100) {
                downloadComplete(context, notifyId, systemService);
            }
        } else if ((context.getPackageName() + RE_DOWNLOAD).equals(action)) {
            //重新下载
            AppUpdateUtils.getInstance().reDownload();
        } else if ((context.getPackageName() + CANCEL_DOWNLOAD).equals(action)) {
            //取消下载
            NotificationManager systemService =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            downloadComplete(context, notifyId, systemService);
        }
    }

    /**
     * 下载完成
     *
     * @param context
     * @param notifyId
     * @param systemService
     */
    private void downloadComplete(Context context, int notifyId, NotificationManager systemService) {
        systemService.cancel(notifyId);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            systemService.deleteNotificationChannel(notificationChannel);
        }
    }

    /**
     * 显示通知栏
     *
     * @param context
     * @param id
     * @param progress
     * @param notificationChannel
     * @param systemService
     */
    private void showNotification(Context context, int id, int progress, String notificationChannel, NotificationManager systemService) {
        String notificationName = "notification";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(notificationChannel, notificationName, NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(false);
            channel.setShowBadge(false);
            channel.enableVibration(false);
            systemService.createNotificationChannel(channel);
        }

        Notification.Builder builder = new Notification.Builder(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(notificationChannel);
        }

        //设置图标
        int notificationIconRes = AppUpdateUtils.getInstance().getUpdateConfig().getNotificationIconRes();
        if (notificationIconRes != 0) {
            builder.setSmallIcon(notificationIconRes);
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),notificationIconRes));
        } else {
            builder.setSmallIcon(android.R.mipmap.sym_def_app_icon);
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),android.R.mipmap.sym_def_app_icon));
        }

        // 设置进度
        builder.setProgress(100, lastProgress, false);
        builder.setWhen(System.currentTimeMillis());
        builder.setShowWhen(true);
        builder.setAutoCancel(false);

        if (progress == -1) {
            Intent intent = new Intent(context.getPackageName() + RE_DOWNLOAD);
            intent.setPackage(context.getPackageName());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            builder.setContentIntent(pendingIntent);
            // 通知栏标题
            builder.setContentTitle(ResUtils.getString(R.string.download_fail));
        } else {
            // 通知栏标题
            builder.setContentTitle(AppUtils.getAppName(context) + " " + ResUtils.getString(R.string.has_download) + progress + "%");
        }

        // 设置只响一次
        builder.setOnlyAlertOnce(true);
        Notification build = builder.build();
        systemService.notify(id, build);
    }
}
