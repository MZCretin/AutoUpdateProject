package com.cretin.www.autoupdateproject.app;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.cretin.www.autoupdateproject.R;
import com.cretin.www.autoupdateproject.model.UpdateModel;
import com.cretin.www.cretinautoupdatelibrary.model.TypeConfig;
import com.cretin.www.cretinautoupdatelibrary.model.UpdateConfig;
import com.cretin.www.cretinautoupdatelibrary.utils.AppUpdateUtils;

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //BlankJ的初始化 跟更新库没有关系哦
        Utils.init(this);

        //更新库配置
        UpdateConfig updateConfig = new UpdateConfig()
                .setDebug(true)//是否是Debug模式
                .setBaseUrl("http://www.cretinzp.com/system/versioninfo")
                .setMethodType(TypeConfig.METHOD_GET)
                .setDataSourceType(TypeConfig.DATA_SOURCE_TYPE_JSON)
                .setShowNotification(true)
                .setNotificationIconRes(R.mipmap.download_icon)
                .setUiThemeType(TypeConfig.UI_THEME_AUTO)
                .setRequestHeaders(null)
                .setRequestParams(null)
                .setModelClass(new UpdateModel());
        AppUpdateUtils.init(this, updateConfig);
    }
}