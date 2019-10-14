package com.cretin.www.autoupdateproject.app;

import android.app.Application;

import com.cretin.www.autoupdateproject.R;
import com.cretin.www.autoupdateproject.model.UpdateModel;
import com.cretin.www.cretinautoupdatelibrary.model.TypeConfig;
import com.cretin.www.cretinautoupdatelibrary.model.UpdateConfig;
import com.cretin.www.cretinautoupdatelibrary.utils.AppUpdateUtils;

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        UpdateConfig updateConfig = new UpdateConfig()
                .setDebug(true)
                .setBaseUrl("http://www.cretinzp.com/system/versioninfo")
                .setMethodType(TypeConfig.METHOD_GET)
                .setDataSourceType(TypeConfig.DATA_SOURCE_TYPE_JSON)
                .setShowNotification(true)
                .setNotificationIconRes(R.mipmap.download_icon)
                .setModelClass(new UpdateModel());
        AppUpdateUtils.init(this, updateConfig);
    }
}