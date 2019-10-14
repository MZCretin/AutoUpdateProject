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
