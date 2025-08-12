package com.cretin.www.cretinautoupdatelibrary.model;

import com.liulishuo.filedownloader.util.FileDownloadHelper;

import java.util.Map;

/**
 * @date: on 2019-10-09
 * @author: a112233
 * @email: mxnzp_life@163.com
 * @desc: 更新配置
 */
public class UpdateConfig {

    //设置使用sdk请求的时候的请求链接地址
    private String baseUrl;

    //是否是debug状态 打印log
    private boolean debug = true;

    //设置样式类型 默认是随意一个样式类型
    private int uiThemeType = TypeConfig.UI_THEME_AUTO;

    //请求方式 默认GET请求
    private int methodType = TypeConfig.METHOD_GET;

    //更新信息的数据来源方式 默认用户自己提供更新信息
    private int dataSourceType = TypeConfig.DATA_SOURCE_TYPE_MODEL;

    //是否在通知栏显示进度 默认显示 显示的好处在于 如果因为网络原因或者其他原因导致下载失败的时候，可以点击通知栏重新下载
    private boolean showNotification = true;

    //通知栏下载进度提醒的Icon图标 默认为0 就是app的logo
    private int notificationIconRes;

    //请求头信息
    private Map<String, Object> requestHeaders;

    //请求参数信息
    private Map<String, Object> requestParams;

    //自定义的Activity类
    private Class customActivityClass;

    //自定义Bean类 此类必须实现LibraryUpdateEntity接口
    private Object modelClass;

    //是否需要进行文件的MD5校验
    private boolean isNeedFileMD5Check;

    //是否静默下载
    private boolean isAutoDownloadBackground;

    //在通知栏显示进度
    public static final int TYPE_NITIFICATION = 1;
    //对话框显示进度
    public static final int TYPE_DIALOG = 2;
    //对话框展示提示和下载进度
    public static final int TYPE_DIALOG_WITH_PROGRESS = 3;
    //对话框展示提示后台下载
    public static final int TYPE_DIALOG_WITH_BACK_DOWN = 4;
    //POST方法
    public static final int METHOD_POST = 3;
    //GET方法
    public static final int METHOD_GET = 4;

    //自定义下载
    private FileDownloadHelper.ConnectionCreator customDownloadConnectionCreator;

    public FileDownloadHelper.ConnectionCreator getCustomDownloadConnectionCreator() {
        return customDownloadConnectionCreator;
    }

    public UpdateConfig setCustomDownloadConnectionCreator(FileDownloadHelper.ConnectionCreator customDownloadConnectionCreator) {
        this.customDownloadConnectionCreator = customDownloadConnectionCreator;
        return this;
    }

    public boolean isAutoDownloadBackground() {
        return isAutoDownloadBackground;
    }

    public UpdateConfig setAutoDownloadBackground(boolean autoDownloadBackground) {
        isAutoDownloadBackground = autoDownloadBackground;
        return this;
    }

    public boolean isShowNotification() {
        return showNotification;
    }

    public UpdateConfig setShowNotification(boolean showNotification) {
        this.showNotification = showNotification;
        return this;
    }

    public Class getCustomActivityClass() {
        return customActivityClass;
    }

    public UpdateConfig setCustomActivityClass(Class customActivityClass) {
        this.customActivityClass = customActivityClass;
        return this;
    }

    public boolean isNeedFileMD5Check() {
        return isNeedFileMD5Check;
    }

    public UpdateConfig setNeedFileMD5Check(boolean needFileMD5Check) {
        isNeedFileMD5Check = needFileMD5Check;
        return this;
    }

    public int getNotificationIconRes() {
        return notificationIconRes;
    }

    public UpdateConfig setNotificationIconRes(int notificationIconRes) {
        this.notificationIconRes = notificationIconRes;
        return this;
    }

    public int getDataSourceType() {
        return dataSourceType;
    }

    public UpdateConfig setDataSourceType(int dataSourceType) {
        this.dataSourceType = dataSourceType;
        return this;
    }

    public Map<String, Object> getRequestHeaders() {
        return requestHeaders;
    }

    public UpdateConfig setRequestHeaders(Map<String, Object> requestHeaders) {
        this.requestHeaders = requestHeaders;
        return this;
    }

    public Map<String, Object> getRequestParams() {
        return requestParams;
    }

    public UpdateConfig setRequestParams(Map<String, Object> requestParams) {
        this.requestParams = requestParams;
        return this;
    }

    public Object getModelClass() {
        return modelClass;
    }

    public UpdateConfig setModelClass(Object modelClass) {
        this.modelClass = modelClass;
        return this;
    }

    public boolean isDebug() {
        return debug;
    }

    public UpdateConfig setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public UpdateConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public int getMethodType() {
        return methodType;
    }

    public UpdateConfig setMethodType(int methodType) {
        this.methodType = methodType;
        return this;
    }

    public int getUiThemeType() {
        return uiThemeType;
    }

    public UpdateConfig setUiThemeType(int uiThemeType) {
        this.uiThemeType = uiThemeType;
        return this;
    }
}
