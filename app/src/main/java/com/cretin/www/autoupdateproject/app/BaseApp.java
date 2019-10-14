package com.cretin.www.autoupdateproject.app;

import android.app.Application;

import com.cretin.www.autoupdateproject.R;
import com.cretin.www.autoupdateproject.UpdateModel;
import com.cretin.www.cretinautoupdatelibrary.model.TypeConfig;
import com.cretin.www.cretinautoupdatelibrary.model.UpdateConfig;
import com.cretin.www.cretinautoupdatelibrary.model.UpdateEntity;
import com.cretin.www.cretinautoupdatelibrary.utils.AppUpdateUtils;
import com.cretin.www.cretinautoupdatelibrary.utils.CretinAutoUpdateUtils;

import java.lang.reflect.Type;

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

//        CretinAutoUpdateUtils.Builder builder = new CretinAutoUpdateUtils.Builder()
//                .setBaseUrl("http://www.cretinzp.com/system/versioninfo")
//                .setIgnoreThisVersion(true)
//                .setShowType(CretinAutoUpdateUtils.Builder.TYPE_DIALOG_WITH_BACK_DOWN)
//                .setIconRes(R.mipmap.logo)
//                .showLog(true)
//                .setRequestMethod(CretinAutoUpdateUtils.Builder.METHOD_GET)
//                .build();
//        CretinAutoUpdateUtils.init(builder);

        UpdateConfig updateConfig = new UpdateConfig()
                .setDebug(true)
                .setBaseUrl("http://www.cretinzp.com/system/versioninfo")
                .setMethodType(TypeConfig.METHOD_GET)
                .setDataSourceType(TypeConfig.DATA_SOURCE_TYPE_URL)
                .setShowNotification(true)
                .setNotificationIconRes(R.mipmap.download_icon)
                .setModelClass(new UpdateModel());
        AppUpdateUtils.init(this, updateConfig);
    }
}