package com.cretin.www.autoupdateproject.model;

/**
 * @date: on 2019-10-14
 * @author: a112233
 * @email: mxnzp_life@163.com
 * @desc: 添加描述
 */
public class ListModel {
    private boolean isForceUpdate;
    private int uiTypeValue;
    private int sourceTypeVaule;
    private boolean isCheckFileMD5;
    private boolean isAutoUpdateBackground;

    public boolean isAutoUpdateBackground() {
        return isAutoUpdateBackground;
    }

    public void setAutoUpdateBackground(boolean autoUpdateBackground) {
        isAutoUpdateBackground = autoUpdateBackground;
    }

    public boolean isCheckFileMD5() {
        return isCheckFileMD5;
    }

    public void setCheckFileMD5(boolean checkFileMD5) {
        isCheckFileMD5 = checkFileMD5;
    }

    public int getUiTypeValue() {
        return uiTypeValue;
    }

    public void setUiTypeValue(int uiTypeValue) {
        this.uiTypeValue = uiTypeValue;
    }

    public boolean isForceUpdate() {
        return isForceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        isForceUpdate = forceUpdate;
    }

    public int getSourceTypeVaule() {
        return sourceTypeVaule;
    }

    public void setSourceTypeVaule(int sourceTypeVaule) {
        this.sourceTypeVaule = sourceTypeVaule;
    }
}
