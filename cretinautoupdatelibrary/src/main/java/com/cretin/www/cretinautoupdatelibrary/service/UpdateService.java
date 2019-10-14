package com.cretin.www.cretinautoupdatelibrary.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.cretin.www.cretinautoupdatelibrary.utils.AppUpdateUtils;

/**
 * @date: on 2019-10-10
 * @author: a112233
 * @email: mxnzp_life@163.com
 * @desc: 添加描述
 */
public class UpdateService extends Service {

    private UpdateReceiver updateReceiver = new UpdateReceiver();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver(updateReceiver, new IntentFilter(getPackageName() + UpdateReceiver.DOWNLOAD_ONLY));
        registerReceiver(updateReceiver, new IntentFilter(getPackageName() + UpdateReceiver.RE_DOWNLOAD));
        registerReceiver(updateReceiver, new IntentFilter(getPackageName() + UpdateReceiver.CANCEL_DOWNLOAD));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(updateReceiver);
    }
}
