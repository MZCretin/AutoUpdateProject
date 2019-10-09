package com.cretin.www.cretinautoupdatelibrary.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by cretin on 2017/3/9.
 */

public abstract class UpdateReceiver extends BroadcastReceiver {

    /**
     * 进度key
     */
    public static final String KEY_OF_PROGRESS = "PROGRESS";

    /**
     * ACTION_UPDATE
     */
    public static final String DOWNLOAD_ONLY = "teprinciple.update";

    /**
     * ACTION_RE_DOWNLOAD
     */
//        const val ACTION_RE_DOWNLOAD = "action_re_download"

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
