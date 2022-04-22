package com.cretin.www.cretinautoupdatelibrary.net;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.text.TextUtils;

import com.cretin.www.cretinautoupdatelibrary.utils.JSONHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpUtils {
    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    /**
     * GET方法 返回数据会解析成cls对象
     *
     * @param context   上下文
     * @param urlString 请求路径
     * @param listener  回调监听
     * @param cls       返回的对象
     * @param <T>       监听的泛型
     */
    public static <T> void doGet(final Context context,
                                 final String urlString,
                                 final Map<String, Object> headers,
                                 final Class<T> cls,
                                 final HttpCallbackModelListener listener) {
        // 因为网络请求是耗时操作，所以需要另外开启一个线程来执行该任务。
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                URL url;
                HttpURLConnection httpURLConnection = null;
                try {
                    // 根据URL地址创建URL对象
                    url = new URL(urlString);

                    httpURLConnection = obtainConnection(url, "", headers, false);

                    httpURLConnection.setRequestMethod("GET");

                    // 响应码为200表示成功，否则失败。
                    if (httpURLConnection.getResponseCode() == 200) {
                        // 获取网络的输入流
                        InputStream is = httpURLConnection.getInputStream();
                        BufferedReader bf = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        //最好在将字节流转换为字符流的时候 进行转码
                        StringBuffer buffer = new StringBuffer();
                        String line = "";
                        while ((line = bf.readLine()) != null) {
                            buffer.append(line);
                        }
                        bf.close();
                        is.close();
                        new ResponseCall(context, listener).doSuccess(JSONHelper.parseObject(buffer.toString(), cls));
                    } else {
                        if (listener != null) {
                            // 回调onError()方法
                            new ResponseCall(context, listener).doFail(
                                    new NetworkErrorException("response err code:" +
                                            httpURLConnection.getResponseCode()));
                        }
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        // 回调onError()方法
                        new ResponseCall(context, listener).doFail(e);
                    }
                } /*catch (IOException e) {
                    if (listener != null) {
                        // 回调onError()方法
                        new ResponseCall(context, listener).doFail(e);
                    }
                }*/ finally {
                    if (httpURLConnection != null) {
                        // 释放资源
                        httpURLConnection.disconnect();
                    }
                }
            }
        });
    }

    /**
     * /**
     * POST方法 返回数据会解析成cls对象
     *
     * @param context   上下文
     * @param urlString 请求的路径
     * @param listener  回调监听
     * @param params    参数列表
     * @param cls       对象
     * @param <T>       监听泛型
     */
    public static <T> void doPost(final Context context,
                                  final String urlString,
                                  final Map<String, Object> headers,
                                  final Map<String, Object> params, final Class<T> cls, final HttpCallbackModelListener listener) {
        final StringBuffer paramsStr = new StringBuffer();
        // 组织请求参数
        Iterator it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry element = (Map.Entry) it.next();
            paramsStr.append(element.getKey());
            paramsStr.append("=");
            paramsStr.append(element.getValue());
            paramsStr.append("&");
        }
        if (paramsStr.length() > 0) {
            paramsStr.deleteCharAt(paramsStr.length() - 1);
        }
        // 因为网络请求是耗时操作，所以需要另外开启一个线程来执行该任务。
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                URL url;
                HttpURLConnection httpURLConnection = null;
                try {
                    url = new URL(urlString);
                    httpURLConnection = obtainConnection(url, paramsStr.toString(), headers, true);

                    httpURLConnection.setRequestMethod("POST");

                    if (httpURLConnection.getResponseCode() == 200) {
                        // 获取网络的输入流
                        InputStream is = httpURLConnection.getInputStream();
                        BufferedReader bf = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        //最好在将字节流转换为字符流的时候 进行转码
                        StringBuffer buffer = new StringBuffer();
                        String line = "";
                        while ((line = bf.readLine()) != null) {
                            buffer.append(line);
                        }
                        bf.close();
                        is.close();
                        new ResponseCall(context, listener).doSuccess(JSONHelper.parseObject(buffer.toString(), cls));
                    } else {
                        new ResponseCall(context, listener).doFail(
                                new NetworkErrorException("response err code:" +
                                        httpURLConnection.getResponseCode()));
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        // 回调onError()方法
                        new ResponseCall(context, listener).doFail(e);
                    }
                } /*catch (IOException e) {
                    if (listener != null) {
                        // 回调onError()方法
                        new ResponseCall(context, listener).doFail(e);
                    }
                } */ finally {
                    if (httpURLConnection != null) {
                        // 最后记得关闭连接
                        httpURLConnection.disconnect();
                    }
                }
            }
        });
    }

    private static HttpURLConnection obtainConnection(URL url, String params, Map<String, Object> headers, boolean usePostMethod) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty("Content-type", "application/json");

        if (headers != null) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue().toString());
            }
        }

        httpURLConnection.setConnectTimeout(30000);
        httpURLConnection.setReadTimeout(30000);

        // 设置运行输入
        httpURLConnection.setDoInput(true);
        if (usePostMethod) {
            // 设置运行输出
            httpURLConnection.setDoOutput(true);
            if (!TextUtils.isEmpty(params)) {
                PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
                // 发送请求参数
                printWriter.write(params);
                // flush输出流的缓冲
                printWriter.flush();
                printWriter.close();
            }
        }
        // TODO: GET的参数会写在URL上
        return httpURLConnection;
    }
}
