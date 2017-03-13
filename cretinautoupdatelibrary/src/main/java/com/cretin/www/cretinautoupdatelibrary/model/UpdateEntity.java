package com.cretin.www.cretinautoupdatelibrary.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cretin on 2017/3/8.
 */

public class UpdateEntity {
    public int versionCode = 0;
    //是否强制更新 0 不强制更新 1 hasAffectCodes拥有字段强制更新 2 所有版本强制更新
    public int isForceUpdate = 0;
    //上一个版本版本号
    public int preBaselineCode = 0;
    //版本号 描述作用
    public String versionName = "";
    //新安装包的下载地址
    public String downurl = "";
    //更新日志
    public String updateLog = "";
    //安装包大小 单位字节
    public String size = "";
    //受影响的版本号 如果开启强制更新 那么这个字段包含的所有版本都会被强制更新 格式 2|3|4
    public String hasAffectCodes = "";

    public UpdateEntity(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        this.versionCode = jsonObject.getInt("versionCode");
        this.versionName = jsonObject.getString("versionName");
        this.isForceUpdate = jsonObject.getInt("isForceUpdate");
        this.downurl = jsonObject.getString("downurl");
        this.preBaselineCode = jsonObject.getInt("preBaselineCode");
        this.updateLog = jsonObject.getString("updateLog");
        this.size = jsonObject.getString("size");
        this.hasAffectCodes = jsonObject.getString("hasAffectCodes");
    }
}
