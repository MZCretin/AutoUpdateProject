package com.cretin.www.cretinautoupdatelibrary.utils;

import android.content.Context;

import com.cretin.www.cretinautoupdatelibrary.activity.UpdateActivity;
import com.cretin.www.cretinautoupdatelibrary.model.DownloadInfo;
import com.cretin.www.cretinautoupdatelibrary.model.UpdateConfig;

/**
 * @date: on 2019-10-09
 * @author: a112233
 * @email: mxnzp_life@163.com
 * @desc: 更新组件
 */
public class AppUpdateUtils {

    private static Context mContext;
    private static AppUpdateUtils updateUtils;
    private static UpdateConfig updateConfig;

    //私有化构造方法
    private AppUpdateUtils() {

    }

    /**
     * 全局初始化
     *
     * @param context
     * @param config
     */
    public static void init(Context context, UpdateConfig config) {
        mContext = context;
        updateConfig = config;
        ResUtils.init(context);
    }

    public static AppUpdateUtils getInstance() {
        if (updateUtils == null) {
            updateUtils = new AppUpdateUtils();
        }
        return updateUtils;
    }

    /**
     * 检查更新
     */
    public void checkUpdate(DownloadInfo info) {
        if (info != null)
            UpdateActivity.launch(mContext, info);
    }
}
