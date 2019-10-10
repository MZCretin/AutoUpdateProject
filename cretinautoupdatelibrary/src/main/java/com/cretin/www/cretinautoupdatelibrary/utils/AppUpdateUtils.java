package com.cretin.www.cretinautoupdatelibrary.utils;

import android.content.Context;

import com.cretin.www.cretinautoupdatelibrary.activity.UpdateActivity;
import com.cretin.www.cretinautoupdatelibrary.model.DownloadInfo;
import com.cretin.www.cretinautoupdatelibrary.model.UpdateConfig;
import com.cretin.www.cretinautoupdatelibrary.service.UpdateReceiver;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloader;

import static com.liulishuo.filedownloader.model.FileDownloadStatus.progress;

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

    //是否开始下载
    private static boolean isDownloading = false;

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

    /**
     * 开始下载
     * @param info
     */
    public void download(DownloadInfo info) {
        FileDownloader.setup(mContext);

        downloadUpdateApkFilePath = info.getApkLocalPath();

        final BaseDownloadTask downloadTask = FileDownloader.getImpl().create(info.getApkUrl())
                .setPath(info.getApkLocalPath());
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
                        downloadComplete();
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
     *
     * @param e
     */
    private void downloadError(Throwable e) {
        isDownloading = false;
        AppUtils.deleteFile(downloadUpdateApkFilePath);
        UpdateReceiver.send(mContext, -1);
    }

    /**
     * 下载完成
     */
    private void downloadComplete() {
        isDownloading = false;
        UpdateReceiver.send(mContext, 100);
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
    }

    /**
     * 开始下载
     */
    private void downloadStart() {
        isDownloading = true;
        UpdateReceiver.send(mContext, 0);
    }
}
