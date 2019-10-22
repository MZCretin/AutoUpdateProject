package com.cretin.www.cretinautoupdatelibrary.interfaces;

/**
 * @date: on 2019-10-22
 * @author: a112233
 * @email: mxnzp_life@163.com
 * @desc: MD5检验监听 如果开启了MD5校验才会回调
 */
public interface MD5CheckListener {
    void fileMd5CheckFail(String originMD5, String localMD5);

    void fileMd5CheckSuccess();
}
