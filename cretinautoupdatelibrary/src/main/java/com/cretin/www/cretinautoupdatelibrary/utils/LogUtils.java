package com.cretin.www.cretinautoupdatelibrary.utils;

import android.os.IInterface;
import android.text.TextUtils;
import android.util.Log;

import com.cretin.www.cretinautoupdatelibrary.utils.AppUpdateUtils;

/**
 * @date: on 2019-10-11
 * @author: a112233
 * @email: mxnzp_life@163.com
 * @desc: 日志
 */
public class LogUtils {
    /**
     * Log日志工具
     *
     * @param msg
     */
    public static void log(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            if (AppUpdateUtils.getInstance().getUpdateConfig().isDebug()) {
                Log.e("【AppUpdateUtils】", msg);
            }
        }
    }
}
