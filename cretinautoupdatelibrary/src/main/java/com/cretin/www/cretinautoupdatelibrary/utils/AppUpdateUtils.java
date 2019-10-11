package com.cretin.www.cretinautoupdatelibrary.utils;

import android.content.Context;
import android.os.Environment;

import com.cretin.www.cretinautoupdatelibrary.LogUtils;
import com.cretin.www.cretinautoupdatelibrary.activity.UpdateType1Activity;
import com.cretin.www.cretinautoupdatelibrary.activity.UpdateType2Activity;
import com.cretin.www.cretinautoupdatelibrary.activity.UpdateType3Activity;
import com.cretin.www.cretinautoupdatelibrary.activity.UpdateType4Activity;
import com.cretin.www.cretinautoupdatelibrary.activity.UpdateType5Activity;
import com.cretin.www.cretinautoupdatelibrary.interfaces.AppDownloadListener;
import com.cretin.www.cretinautoupdatelibrary.model.DownloadInfo;
import com.cretin.www.cretinautoupdatelibrary.model.UpdateConfig;
import com.cretin.www.cretinautoupdatelibrary.service.UpdateReceiver;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

import static com.cretin.www.cretinautoupdatelibrary.utils.AppUtils.getAppLocalPath;

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
    //是否初始化
    private static boolean isInit;
    //下载过程监听
    private AppDownloadListener appDownloadListener;

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
    public static void init(Context context, UpdateConfig config) {
        isInit = true;
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
        checkInit();

        //检查sdk的挂载 未挂载直接阻断
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            LogUtils.log("sdk卡未加载");
            return;
        }

        if (info != null)
            UpdateType5Activity.launch(mContext, info);
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

        final BaseDownloadTask downloadTask = FileDownloader.getImpl().create(info.getApkUrl())
                .setPath(downloadUpdateApkFilePath);

        downloadTask
                .addHeader("Accept-Encoding", "identity")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36")
                .setListener(new FileDownloadLargeFileListener() {
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
                        AppUtils.deleteFile(downloadUpdateApkFilePath);
                        AppUtils.deleteFile(downloadUpdateApkFilePath + ".temp");
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        downloadComplete(task.getPath());
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        AppUtils.deleteFile(downloadUpdateApkFilePath);
                        AppUtils.deleteFile(downloadUpdateApkFilePath + ".temp");
                        downloadError(e);
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                })
                .setAutoRetryTimes(3)
                .start();
    }

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
}
