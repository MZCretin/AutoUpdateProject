package com.cretin.www.cretinautoupdatelibrary.utils;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.cretin.www.cretinautoupdatelibrary.activity.UpdateType10Activity;
import com.cretin.www.cretinautoupdatelibrary.activity.UpdateType11Activity;
import com.cretin.www.cretinautoupdatelibrary.activity.UpdateType12Activity;
import com.cretin.www.cretinautoupdatelibrary.activity.UpdateType1Activity;
import com.cretin.www.cretinautoupdatelibrary.activity.UpdateType2Activity;
import com.cretin.www.cretinautoupdatelibrary.activity.UpdateType3Activity;
import com.cretin.www.cretinautoupdatelibrary.activity.UpdateType4Activity;
import com.cretin.www.cretinautoupdatelibrary.activity.UpdateType5Activity;
import com.cretin.www.cretinautoupdatelibrary.activity.UpdateType6Activity;
import com.cretin.www.cretinautoupdatelibrary.activity.UpdateType7Activity;
import com.cretin.www.cretinautoupdatelibrary.activity.UpdateType8Activity;
import com.cretin.www.cretinautoupdatelibrary.activity.UpdateType9Activity;
import com.cretin.www.cretinautoupdatelibrary.interfaces.AppDownloadListener;
import com.cretin.www.cretinautoupdatelibrary.model.DownloadInfo;
import com.cretin.www.cretinautoupdatelibrary.model.LibraryUpdateEntity;
import com.cretin.www.cretinautoupdatelibrary.model.TypeConfig;
import com.cretin.www.cretinautoupdatelibrary.model.UpdateConfig;
import com.cretin.www.cretinautoupdatelibrary.net.HttpCallbackModelListener;
import com.cretin.www.cretinautoupdatelibrary.net.HttpUtils;
import com.cretin.www.cretinautoupdatelibrary.service.UpdateReceiver;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.cretin.www.cretinautoupdatelibrary.utils.AppUtils.getAppLocalPath;

/**
 * @date: on 2019-10-09
 * @author: a112233
 * @email: mxnzp_life@163.com
 * @desc: 更新组件
 */
public class AppUpdateUtils {

    private static Application mContext;
    private static AppUpdateUtils updateUtils;
    private static UpdateConfig updateConfig;
    //是否初始化
    private static boolean isInit;
    //下载过程监听
    private AppDownloadListener appDownloadListener;

    //下载任务
    private BaseDownloadTask downloadTask;

    //是否开始下载
    private static boolean isDownloading = false;

    //本地保留下载信息
    private DownloadInfo downloadInfo;

    //apk下载的路径
    private static String downloadUpdateApkFilePath = "";

    //私有化构造方法
    private AppUpdateUtils() {

    }

    /**
     * 全局初始化
     *
     * @param context
     * @param config
     */
    public static void init(Application context, UpdateConfig config) {
        isInit = true;
        mContext = context;
        updateConfig = config;
        ResUtils.init(context);
        //初始化文件下载库
        FileDownloader.setupOnApplicationOnCreate(mContext)
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(30_000) // set connection timeout.
                        .readTimeout(30_000) // set read timeout.
                ))
                .commit();
    }

    public static AppUpdateUtils getInstance() {
        if (updateUtils == null) {
            updateUtils = new AppUpdateUtils();
        }
        return updateUtils;
    }

    /**
     * 检查更新 sdk自助请求接口
     */
    public void checkUpdate() {
        checkInit();

        UpdateConfig updateConfig = getUpdateConfig();

        if (updateConfig.getDataSourceType() != TypeConfig.DATA_SOURCE_TYPE_URL) {
            LogUtils.log("使用 DATA_SOURCE_TYPE_URL 这种模式的时候，必须要配置UpdateConfig中的dataSourceType参数才为 DATA_SOURCE_TYPE_URL ");
            return;
        }

        if (TextUtils.isEmpty(updateConfig.getBaseUrl())) {
            LogUtils.log("使用 DATA_SOURCE_TYPE_URL 这种模式的时候，必须要配置UpdateConfig中的baseUrl参数不为空才可使用");
            return;
        }

        getData();
    }

    /**
     * 检查更新 调用者配置数据
     */
    public void checkUpdate(DownloadInfo info) {
        checkInit();

        if (info == null) {
            return;
        }

        //检查当前版本是否需要更新 如果app当前的版本号大于等于线上最新的版本号 不需要升级版本
        int versionCode = AppUtils.getVersionCode(mContext);
        if (versionCode >= info.getProdVersionCode()) {
            return;
        }

        //检查是否强制更新
        if (info.getForceUpdateFlag() != 0) {
            //需要强制更新
            if (info.getForceUpdateFlag() == 1) {
                //hasAffectCodes拥有字段强制更新
                String hasAffectCodes = info.getHasAffectCodes();
                if (!TextUtils.isEmpty(hasAffectCodes)) {
                    List<String> codes = Arrays.asList(hasAffectCodes.split("\\|"));
                    if (codes.contains(versionCode + "")) {
                        //包含这个版本 所以需要强制更新
                    } else {
                        //不包含这个版本 所以此版本需要强制更新
                        info.setForceUpdateFlag(0);
                    }
                }
            } else {
                //所有拥有字段强制更新
            }
        }

        //检查sdk的挂载 未挂载直接阻断
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            LogUtils.log("sdk卡未加载");
            return;
        }

        UpdateConfig updateConfig = getUpdateConfig();
        int type = updateConfig.getUiThemeType();

        if (type == TypeConfig.UI_THEME_AUTO) {
            //随机样式
            String versionName = AppUtils.getVersionName(mContext);
            type = 300 + versionName.hashCode() % 12;
        }

        //根据类型选择对应的样式
        if (type == TypeConfig.UI_THEME_A) {
            UpdateType1Activity.launch(mContext, info);
        } else if (type == TypeConfig.UI_THEME_B) {
            UpdateType2Activity.launch(mContext, info);
        } else if (type == TypeConfig.UI_THEME_C) {
            UpdateType3Activity.launch(mContext, info);
        } else if (type == TypeConfig.UI_THEME_D) {
            UpdateType4Activity.launch(mContext, info);
        } else if (type == TypeConfig.UI_THEME_E) {
            UpdateType5Activity.launch(mContext, info);
        } else if (type == TypeConfig.UI_THEME_F) {
            UpdateType6Activity.launch(mContext, info);
        } else if (type == TypeConfig.UI_THEME_G) {
            UpdateType7Activity.launch(mContext, info);
        } else if (type == TypeConfig.UI_THEME_H) {
            UpdateType8Activity.launch(mContext, info);
        } else if (type == TypeConfig.UI_THEME_I) {
            UpdateType9Activity.launch(mContext, info);
        } else if (type == TypeConfig.UI_THEME_J) {
            UpdateType10Activity.launch(mContext, info);
        } else if (type == TypeConfig.UI_THEME_K) {
            UpdateType11Activity.launch(mContext, info);
        } else if (type == TypeConfig.UI_THEME_L) {
            UpdateType12Activity.launch(mContext, info);
        }
    }

    /**
     * 开始下载
     *
     * @param info
     * @param appDownloadListener
     */
    public void download(DownloadInfo info, AppDownloadListener appDownloadListener) {
        if (appDownloadListener != null)
            this.appDownloadListener = appDownloadListener;

        checkInit();

        downloadInfo = info;

        FileDownloader.setup(mContext);

        downloadUpdateApkFilePath = getAppLocalPath(info.getProdVersionName());

        //检查下本地文件的大小 如果大小和信息中的文件大小一样的就可以直接安装 否则就删除掉 todo
//        File tempFile = new File(downloadUpdateApkFilePath);
//        if (tempFile != null && tempFile.exists()) {
//            if (tempFile.length() != info.getFileSize()) {
//                AppUtils.deleteFile(downloadUpdateApkFilePath);
//                AppUtils.deleteFile(FileDownloadUtils.getTempPath(downloadUpdateApkFilePath));
//            }
//        }

        downloadTask = FileDownloader.getImpl().create(info.getApkUrl())
                .setPath(downloadUpdateApkFilePath);
        downloadTask
                .addHeader("Accept-Encoding", "identity")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36")
                .setListener(fileDownloadListener)
                .setAutoRetryTimes(3)
                .start();
    }

    /**
     * 结束任务
     */
    public void cancelTask() {
        isDownloading = false;
        if (downloadTask != null) {
            downloadTask.pause();
        }
        UpdateReceiver.cancelDownload(mContext);
    }

    private FileDownloadListener fileDownloadListener = new FileDownloadLargeFileListener() {
        @Override
        protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
            downloadStart();
            if (totalBytes < 0) {
                downloadTask.pause();
            }
        }

        @Override
        protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
            downloading(soFarBytes, totalBytes);
            if (totalBytes < 0) {
                downloadTask.pause();
            }
        }

        @Override
        protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {
            if (appDownloadListener != null) {
                appDownloadListener.pause();
            }
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            downloadComplete(task.getPath());
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            AppUtils.deleteFile(downloadUpdateApkFilePath);
            AppUtils.deleteFile(FileDownloadUtils.getTempPath(downloadUpdateApkFilePath));
            downloadError(e);
        }

        @Override
        protected void warn(BaseDownloadTask task) {

        }
    };

    /**
     * @param e
     */
    private void downloadError(Throwable e) {
        isDownloading = false;
        AppUtils.deleteFile(downloadUpdateApkFilePath);
        UpdateReceiver.send(mContext, -1);
        if (appDownloadListener != null) {
            appDownloadListener.downloadFail(e.getMessage());
        }
        LogUtils.log("文件下载出错，异常信息为：" + e.getMessage());
    }

    /**
     * 下载完成
     *
     * @param path
     */
    private void downloadComplete(String path) {
        isDownloading = false;
        UpdateReceiver.send(mContext, 100);
        if (appDownloadListener != null) {
            appDownloadListener.downloadComplete(path);
        }
        LogUtils.log("文件下载完成，准备安装，文件地址：" + downloadUpdateApkFilePath);
        AppUtils.installApkFile(mContext, new File(path));
    }

    /**
     * 正在下载
     *
     * @param soFarBytes
     * @param totalBytes
     */
    private void downloading(long soFarBytes, long totalBytes) {
        isDownloading = true;
        int progress = (int) (soFarBytes * 100.0 / totalBytes);
        if (progress < 0) progress = 0;
        UpdateReceiver.send(mContext, progress);
        if (appDownloadListener != null) {
            appDownloadListener.downloading(progress);
        }
        LogUtils.log("文件正在下载中，进度为" + progress + "%");
    }

    /**
     * 开始下载
     */
    private void downloadStart() {
        LogUtils.log("文件开始下载");
        isDownloading = true;
        UpdateReceiver.send(mContext, 0);
        if (appDownloadListener != null) {
            appDownloadListener.downloadStart();
        }
    }

    public static boolean isDownloading() {
        checkInit();
        return isDownloading;
    }

    public UpdateConfig getUpdateConfig() {
        if (updateConfig == null) {
            return new UpdateConfig();
        }
        return updateConfig;
    }

    /**
     * 初始化检测
     *
     * @return
     */
    private static void checkInit() {
        if (!isInit) {
            throw new RuntimeException("AppUpdateUtils需要先调用init方法进行初始化才能使用");
        }
    }

    /**
     * 获取Context
     *
     * @return
     */
    public Context getContext() {
        checkInit();
        return mContext;
    }

    /**
     * 重新下载
     */
    public void reDownload() {
        appDownloadListener.reDownload();
        download(downloadInfo, appDownloadListener);
    }

    /**
     * 清除所有缓存的数据
     */
    public void clearAllData() {
        //删除任务中的缓存文件
        FileDownloader.getImpl().clearAllTaskData();
        //删除已经下载好的文件
        AppUtils.delAllFile(new File(AppUtils.getAppRootPath()));
    }

    /**
     * 获取数据
     *
     * @param type
     */
    private void getData() {
        UpdateConfig updateConfig = getUpdateConfig();
        Object modelClass = updateConfig.getModelClass();
        if (modelClass != null) {
            if (modelClass instanceof LibraryUpdateEntity) {
                if (updateConfig.getMethodType() == TypeConfig.METHOD_GET) {
                    //GET请求
                    HttpUtils.doGet(AppUpdateUtils.getInstance().getContext(), updateConfig.getBaseUrl(), updateConfig.getRequestHeaders(), updateConfig.getModelClass().getClass(), new HttpCallbackModelListener() {
                        @Override
                        public void onFinish(Object response) {
                            requestSuccess(response);
                        }

                        @Override
                        public void onError(Exception e) {
                            LogUtils.log("GET请求抛出异常：" + e.getMessage());
                        }
                    });
                } else {
                    //POST请求
                    HttpUtils.doPost(AppUpdateUtils.getInstance().getContext(), updateConfig.getBaseUrl(), updateConfig.getRequestHeaders(), updateConfig.getRequestParams(), updateConfig.getModelClass().getClass(), new HttpCallbackModelListener() {
                        @Override
                        public void onFinish(Object response) {
                            requestSuccess(response);
                        }

                        @Override
                        public void onError(Exception e) {
                            LogUtils.log("POST请求抛出异常：" + e.getMessage());
                        }
                    });
                }
            } else {
                throw new RuntimeException(modelClass.getClass().getSimpleName() + "：未实现LibraryUpdateEntity接口");
            }
        }
    }

    private void requestSuccess(Object response) {
        LibraryUpdateEntity libraryUpdateEntity = (LibraryUpdateEntity) response;
        if (libraryUpdateEntity != null) {
            checkUpdate(new DownloadInfo()
                    .setForceUpdateFlag(libraryUpdateEntity.forceAppUpdateFlag())
                    .setProdVersionCode(libraryUpdateEntity.getAppVersionCode())
                    .setFileSize(Long.parseLong(libraryUpdateEntity.getAppApkSize()))
                    .setProdVersionName(libraryUpdateEntity.getAppVersionName())
                    .setApkUrl(libraryUpdateEntity.getAppApkUrls())
                    .setHasAffectCodes(libraryUpdateEntity.getAppHasAffectCodes())
                    .setUpdateLog(libraryUpdateEntity.getAppUpdateLog()));
        }
    }
}
