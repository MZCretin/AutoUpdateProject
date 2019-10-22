package com.cretin.www.cretinautoupdatelibrary.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @date: on 2019-10-09
 * @author: a112233
 * @email: mxnzp_life@163.com
 * @desc: 下载需要的信息
 */
public class DownloadInfo implements Parcelable {
    //apk下载地址
    private String apkUrl;
    //apk文件大小 单位byte
    private long fileSize;
    //生产环境最新的版本号 用于做比较
    private int prodVersionCode;
    //生产环境最新的版本名称 用于做展示
    private String prodVersionName;
    //更新日志
    private String updateLog;
    //是否强制更新 0 不强制更新 1 hasAffectCodes拥有字段强制更新 2 所有版本强制更新
    private int forceUpdateFlag;
    //受影响的版本号 如果开启强制更新 那么这个字段包含的所有版本都会被强制更新 格式 2|3|4
    private String hasAffectCodes;
    //文件MD5的校验值
    private String md5Check;

    public String getMd5Check() {
        return md5Check;
    }

    public DownloadInfo setMd5Check(String md5Check) {
        this.md5Check = md5Check;
        return this;
    }

    public String getHasAffectCodes() {
        return hasAffectCodes;
    }

    public DownloadInfo setHasAffectCodes(String hasAffectCodes) {
        this.hasAffectCodes = hasAffectCodes;
        return this;
    }

    public boolean isForceUpdateFlag() {
        return forceUpdateFlag > 0;
    }

    public int getForceUpdateFlag() {
        return forceUpdateFlag;
    }

    public DownloadInfo setForceUpdateFlag(int forceUpdateFlag) {
        this.forceUpdateFlag = forceUpdateFlag;
        return this;
    }

    public String getUpdateLog() {
        return updateLog;
    }

    public DownloadInfo setUpdateLog(String updateLog) {
        this.updateLog = updateLog;
        return this;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public DownloadInfo setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
        return this;
    }

    public long getFileSize() {
        return fileSize;
    }

    public DownloadInfo setFileSize(long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public int getProdVersionCode() {
        return prodVersionCode;
    }

    public DownloadInfo setProdVersionCode(int prodVersionCode) {
        this.prodVersionCode = prodVersionCode;
        return this;
    }

    public String getProdVersionName() {
        return prodVersionName;
    }

    public DownloadInfo setProdVersionName(String prodVersionName) {
        this.prodVersionName = prodVersionName;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.apkUrl);
        dest.writeLong(this.fileSize);
        dest.writeInt(this.prodVersionCode);
        dest.writeString(this.prodVersionName);
        dest.writeString(this.updateLog);
        dest.writeInt(this.forceUpdateFlag);
        dest.writeString(this.hasAffectCodes);
        dest.writeString(this.md5Check);
    }

    public DownloadInfo() {
    }

    protected DownloadInfo(Parcel in) {
        this.apkUrl = in.readString();
        this.fileSize = in.readLong();
        this.prodVersionCode = in.readInt();
        this.prodVersionName = in.readString();
        this.updateLog = in.readString();
        this.forceUpdateFlag = in.readInt();
        this.hasAffectCodes = in.readString();
        this.md5Check = in.readString();
    }

    public static final Creator<DownloadInfo> CREATOR = new Creator<DownloadInfo>() {
        @Override
        public DownloadInfo createFromParcel(Parcel source) {
            return new DownloadInfo(source);
        }

        @Override
        public DownloadInfo[] newArray(int size) {
            return new DownloadInfo[size];
        }
    };
}
