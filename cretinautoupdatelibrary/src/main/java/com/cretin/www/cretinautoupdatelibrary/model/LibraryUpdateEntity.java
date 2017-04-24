package com.cretin.www.cretinautoupdatelibrary.model;

/**
 * Created by cretin on 2017/4/20.
 */

public interface LibraryUpdateEntity {
    //获取版本号
    int getVersionCodes();

    //是否强制更新 0 不强制更新 1 hasAffectCodes拥有字段强制更新 2 所有版本强制更新
    int getIsForceUpdates();

    //上一个版本版本号
    int getPreBaselineCodes();

    //版本号 描述作用
    String getVersionNames();

    //新安装包的下载地址
    String getDownurls();

    //更新日志
    String getUpdateLogs();

    //安装包大小 单位字节
    String getApkSizes();

    //受影响的版本号 如果开启强制更新 那么这个字段包含的所有版本都会被强制更新 格式 2|3|4
    String getHasAffectCodess();
}
