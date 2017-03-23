package com.cretin.www.autoupdateproject.app;

import android.app.Application;

import com.cretin.www.autoupdateproject.R;
import com.cretin.www.cretinautoupdatelibrary.utils.CretinAutoUpdateUtils;

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CretinAutoUpdateUtils.Builder builder = new CretinAutoUpdateUtils.Builder()
                .setBaseUrl("http://101.201.31.212:8016/version/checkVersion")
                .setIgnoreThisVersion(false)
                .setShowType(CretinAutoUpdateUtils.Builder.TYPE_DIALOG)
                .setIconRes(R.mipmap.ic_launcher)
                .showLog(true)
                .setRequestMethod(CretinAutoUpdateUtils.Builder.METHOD_GET)
                .build();
        CretinAutoUpdateUtils.init(builder);
    }
}