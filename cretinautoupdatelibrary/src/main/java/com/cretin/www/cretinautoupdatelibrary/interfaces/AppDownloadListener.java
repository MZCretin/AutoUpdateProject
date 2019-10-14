package com.cretin.www.cretinautoupdatelibrary.interfaces;

/**
 * @date: on 2019-10-11
 * @author: a112233
 * @email: mxnzp_life@163.com
 * @desc: 添加描述
 */
public interface AppDownloadListener {
    void downloading(int progress);
    void downloadFail(String msg);
    void downloadComplete(String path);
    void downloadStart();
    void reDownload();
    void pause();
}
