package com.cretin.www.cretinautoupdatelibrary.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by cretin on 2017/3/9.
 */

public abstract class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String tag = bundle.getString("type");
        if ( tag.equals("err") ) {
            String err = bundle.getString("err");
            downloadFail(err);
        } else if ( tag.equals("doing") ) {
            int progress = bundle.getInt("progress");
            downloading(progress);
            if ( progress == 100 ) {
                downloadComplete();
            }
        }
    }

    //下载完成调用
    protected abstract void downloadComplete();

    //下载中调用
    protected abstract void downloading(int progress);

    //下载中调用
    protected abstract void downloadFail(String e);
}
