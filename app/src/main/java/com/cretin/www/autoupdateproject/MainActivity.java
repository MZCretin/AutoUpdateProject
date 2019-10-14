package com.cretin.www.autoupdateproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.cretin.www.cretinautoupdatelibrary.interfaces.ForceExitCallBack;
import com.cretin.www.cretinautoupdatelibrary.model.DownloadInfo;
import com.cretin.www.cretinautoupdatelibrary.model.TypeConfig;
import com.cretin.www.cretinautoupdatelibrary.utils.AppUpdateUtils;
import com.cretin.www.cretinautoupdatelibrary.utils.AppUtils;
import com.cretin.www.cretinautoupdatelibrary.utils.CretinAutoUpdateUtils;

/**
 * 说明：\n一、Demo下载下来的App是我业余写的一个段子类的App（段子乐），定期更新大量的搞笑段子和搞笑趣图，如果您感兴趣，可以安装之后在空闲的时候娱乐娱乐，App内部也可以由用户发布属于自己的段子呢！\n二、总共有四种下载样式可供选择，demo选择的是TYPE_DIALOG_WITH_BACK_DOWN，另外还有三种模式可供选择：\n           1、TYPE_DIALOG \n           2、TYPE_NITIFICATION \n           3、TYPE_DIALOG_WITH_PROGRESS\n   详情请关注github和csdn博客，地址github有
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //清除数据
    public void clear(View view) {
        AppUpdateUtils.getInstance().clearAllData();
        Toast.makeText(this, "数据清除成功", Toast.LENGTH_SHORT).show();
    }

    //更新 之前版本的逻辑
    public void update(View view) {
        /**
         *   请大家在BaseApp里面切换不同的类型体验不同类型的更新效果
         */

        //需要处理强制更新的时候调用带参数的check方法
        CretinAutoUpdateUtils.getInstance(MainActivity.this).check(new ForceExitCallBack() {
            @Override
            public void exit() {
//                在这里退出整个app
                MainActivity.this.finish();
            }
        });


        //这里就是不处理强制更新的情况
        CretinAutoUpdateUtils.getInstance(MainActivity.this).check();

        ///-------------------------------------------------
        /**
         *  感谢网友的提示  他说：毕竟不是每个app都是web的 我是c++后台
         *  所以有必要提供一个给定数据的入口，然后根据提供的数据来进行版本的更新 所以就有了下面这个
         */
        //模拟请求到的数据
//        String json = "{'versionCode':10,'isForceUpdateFlag':0,'preBaselineCode':9,'versionName':'v1.3.0'," +
//                "'downurl':'http://jokesimg.cretinzp.com/apk/app-release_130_jiagu_sign.apk'," +
//                "'updateLog':'最新版v1.3.0\n1、全新积分抽奖上线\n2、新增积分列表，积分详情更清楚\n" +
//                "3、优化N多你可能都不知道的细节\n4、修复已知BUG','size':'15620356'," +
//                "'hasAffectCodes':'1|2|3|4|5|6|7|8|9'}";
//        try {
//            //解析成对象
//            UpdateEntity data = JSONHelper.parseObject(json, UpdateEntity.class);//反序列化
//            //对一致数据进行版本更新的控制
//            CretinAutoUpdateUtils.getInstance(MainActivity.this).check(data, new ForceExitCallBack() {
//                @Override
//                public void exit() {
//                    MainActivity.this.finish();
//                }
//            }, true);
//        } catch ( JSONException e ) {
//            e.printStackTrace();
//        }
    }


    public void update1(View view) {
        DownloadInfo info = new DownloadInfo().setApkUrl("https://apkdownload.followme.cn/Followme-official-release.apk")
//        DownloadInfo info = new DownloadInfo().setApkUrl("http://jokesimg.cretinzp.com/apk/app-release_224_jiagu_sign.apk")
                .setFileSize(31338250)
                .setProdVersionCode(19)
                .setProdVersionName("2.3.1")
                .setForceUpdateFlag(0)
                .setUpdateLog("新版本特性：\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙");
        AppUpdateUtils.getInstance().getUpdateConfig().setUiThemeType(TypeConfig.UI_THEME_B);
        AppUpdateUtils.getInstance().checkUpdate();
    }

    public void update2(View view) {
//        DownloadInfo info = new DownloadInfo().setApkUrl("https://apkdownload.followme.cn/Followme-official-release.apk")
////        DownloadInfo info = new DownloadInfo().setApkUrl("http://jokesimg.cretinzp.com/apk/app-release_224_jiagu_sign.apk")
//                .setFileSize(31338250)
//                .setProdVersionCode(19)
//                .setProdVersionName("2.3.1")
//                .setForceUpdateFlag(0)
//                .setUpdateLog("新版本特性：\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙");
//        AppUpdateUtils.getInstance().checkUpdate(info);

        AppUpdateUtils.getInstance().checkUpdate("{\"versionCode\": 23,   \"isForceUpdate\": 1, \"preBaselineCode\": 22,\"versionName\": \"v2.2.4\",\"downurl\": \"http://jokesimg.cretinzp.com/apk/app-release_224_jiagu_sign.apk\",\"updateLog\": \"v2.2.4\r\n1、优化细节和体验，更加稳定\r\n2、修复已知bug\n3、风格修改\",\"size\": \"28400193\",\"hasAffectCodes\":\"1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22\"}");
    }

    public void update3(View view) {
        DownloadInfo info = new DownloadInfo().setApkUrl("https://apkdownload.followme.cn/Followme-official-release.apk")
//        DownloadInfo info = new DownloadInfo().setApkUrl("http://jokesimg.cretinzp.com/apk/app-release_224_jiagu_sign.apk")
                .setFileSize(31338250)
                .setProdVersionCode(19)
                .setProdVersionName("2.3.1")
                .setForceUpdateFlag(0)
                .setUpdateLog("新版本特性：\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙");
        AppUpdateUtils.getInstance().checkUpdate(info);
    }

    public void update4(View view) {
        DownloadInfo info = new DownloadInfo().setApkUrl("https://apkdownload.followme.cn/Followme-official-release.apk")
//        DownloadInfo info = new DownloadInfo().setApkUrl("http://jokesimg.cretinzp.com/apk/app-release_224_jiagu_sign.apk")
                .setFileSize(31338250)
                .setProdVersionCode(19)
                .setProdVersionName("2.3.1")
                .setForceUpdateFlag(0)
                .setUpdateLog("新版本特性：\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙");
        AppUpdateUtils.getInstance().checkUpdate(info);
    }

    public void update5(View view) {
        DownloadInfo info = new DownloadInfo().setApkUrl("https://apkdownload.followme.cn/Followme-official-release.apk")
//        DownloadInfo info = new DownloadInfo().setApkUrl("http://jokesimg.cretinzp.com/apk/app-release_224_jiagu_sign.apk")
                .setFileSize(31338250)
                .setProdVersionCode(19)
                .setProdVersionName("2.3.1")
                .setForceUpdateFlag(0)
                .setUpdateLog("新版本特性：\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙");
        AppUpdateUtils.getInstance().checkUpdate(info);
    }

    public void update6(View view) {
        DownloadInfo info = new DownloadInfo().setApkUrl("https://apkdownload.followme.cn/Followme-official-release.apk")
//        DownloadInfo info = new DownloadInfo().setApkUrl("http://jokesimg.cretinzp.com/apk/app-release_224_jiagu_sign.apk")
                .setFileSize(31338250)
                .setProdVersionCode(19)
                .setProdVersionName("2.3.1")
                .setForceUpdateFlag(0)
                .setUpdateLog("新版本特性：\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙");
        AppUpdateUtils.getInstance().checkUpdate(info);
    }

    public void update7(View view) {
        DownloadInfo info = new DownloadInfo().setApkUrl("https://apkdownload.followme.cn/Followme-official-release.apk")
//        DownloadInfo info = new DownloadInfo().setApkUrl("http://jokesimg.cretinzp.com/apk/app-release_224_jiagu_sign.apk")
                .setFileSize(31338250)
                .setProdVersionCode(19)
                .setProdVersionName("2.3.1")
                .setForceUpdateFlag(0)
                .setUpdateLog("新版本特性：\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙");
        AppUpdateUtils.getInstance().checkUpdate(info);
    }

    public void update8(View view) {
        DownloadInfo info = new DownloadInfo().setApkUrl("https://apkdownload.followme.cn/Followme-official-release.apk")
//        DownloadInfo info = new DownloadInfo().setApkUrl("http://jokesimg.cretinzp.com/apk/app-release_224_jiagu_sign.apk")
                .setFileSize(31338250)
                .setProdVersionCode(19)
                .setProdVersionName("2.3.1")
                .setForceUpdateFlag(0)
                .setUpdateLog("新版本特性：\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙");
        AppUpdateUtils.getInstance().checkUpdate(info);
    }

    public void update9(View view) {
        DownloadInfo info = new DownloadInfo().setApkUrl("https://apkdownload.followme.cn/Followme-official-release.apk")
//        DownloadInfo info = new DownloadInfo().setApkUrl("http://jokesimg.cretinzp.com/apk/app-release_224_jiagu_sign.apk")
                .setFileSize(31338250)
                .setProdVersionCode(19)
                .setProdVersionName("2.3.1")
                .setForceUpdateFlag(0)
                .setUpdateLog("新版本特性：\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙");
        AppUpdateUtils.getInstance().checkUpdate(info);
    }

    public void update10(View view) {
        DownloadInfo info = new DownloadInfo().setApkUrl("https://apkdownload.followme.cn/Followme-official-release.apk")
//        DownloadInfo info = new DownloadInfo().setApkUrl("http://jokesimg.cretinzp.com/apk/app-release_224_jiagu_sign.apk")
                .setFileSize(31338250)
                .setProdVersionCode(19)
                .setProdVersionName("2.3.1")
                .setForceUpdateFlag(0)
                .setUpdateLog("新版本特性：\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙");
        AppUpdateUtils.getInstance().checkUpdate(info);
    }

    public void update11(View view) {
        DownloadInfo info = new DownloadInfo().setApkUrl("https://apkdownload.followme.cn/Followme-official-release.apk")
//        DownloadInfo info = new DownloadInfo().setApkUrl("http://jokesimg.cretinzp.com/apk/app-release_224_jiagu_sign.apk")
                .setFileSize(31338250)
                .setProdVersionCode(19)
                .setProdVersionName("2.3.1")
                .setForceUpdateFlag(0)
                .setUpdateLog("新版本特性：\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙");
        AppUpdateUtils.getInstance().checkUpdate(info);
    }

    public void update12(View view) {
        DownloadInfo info = new DownloadInfo().setApkUrl("https://apkdownload.followme.cn/Followme-official-release.apk")
//        DownloadInfo info = new DownloadInfo().setApkUrl("http://jokesimg.cretinzp.com/apk/app-release_224_jiagu_sign.apk")
                .setFileSize(31338250)
                .setProdVersionCode(19)
                .setProdVersionName("2.3.1")
                .setForceUpdateFlag(0)
                .setUpdateLog("新版本特性：\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙");
        AppUpdateUtils.getInstance().checkUpdate(info);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //一定要调用
        CretinAutoUpdateUtils.getInstance(this).destroy();
    }
}