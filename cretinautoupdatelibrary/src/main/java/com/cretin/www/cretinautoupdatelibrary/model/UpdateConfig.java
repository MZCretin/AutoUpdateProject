package com.cretin.www.cretinautoupdatelibrary.model;

/**
 * @date: on 2019-10-09
 * @author: a112233
 * @email: mxnzp_life@163.com
 * @desc: 更新配置
 */
public class UpdateConfig {
    private String baseUrl;
    //是否是debug状态 打印log
    private boolean debug = true;

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public UpdateConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }
}
