package com.cretin.www.autoupdateproject.app;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.cretin.www.autoupdateproject.CustomActivity;
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
                .setBaseUrl("http://www.cretinzp.com/system/versioninfo")//当dataSourceType为DATA_SOURCE_TYPE_URL时，配置此接口用于获取更新信息
                .setMethodType(TypeConfig.METHOD_GET)//当dataSourceType为DATA_SOURCE_TYPE_URL时，设置请求的方法
                .setDataSourceType(TypeConfig.DATA_SOURCE_TYPE_URL)//设置获取更新信息的方式
                .setShowNotification(true)//配置更新的过程中是否在通知栏显示进度
                .setNotificationIconRes(R.mipmap.download_icon)//配置通知栏显示的图标
                .setUiThemeType(TypeConfig.UI_THEME_AUTO)//配置UI的样式，一种有12种样式可供选择
                .setRequestHeaders(null)//当dataSourceType为DATA_SOURCE_TYPE_URL时，设置请求的请求头
                .setRequestParams(null)//当dataSourceType为DATA_SOURCE_TYPE_URL时，设置请求的请求参数
                .setCustomActivityClass(CustomActivity.class)
                .setModelClass(new UpdateModel());
        AppUpdateUtils.init(this, updateConfig);
    }
}