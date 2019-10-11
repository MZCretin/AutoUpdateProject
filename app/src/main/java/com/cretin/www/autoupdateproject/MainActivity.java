package com.cretin.www.autoupdateproject;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cretin.www.cretinautoupdatelibrary.interfaces.ForceExitCallBack;
import com.cretin.www.cretinautoupdatelibrary.model.DownloadInfo;
import com.cretin.www.cretinautoupdatelibrary.utils.AppUpdateUtils;
import com.cretin.www.cretinautoupdatelibrary.utils.AppUtils;
import com.cretin.www.cretinautoupdatelibrary.utils.CretinAutoUpdateUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //更新
    public void update(View view) {
        /**
         *   请大家在BaseApp里面切换不同的类型体验不同类型的更新效果
         */

        //需要处理强制更新的时候调用带参数的check方法
//        CretinAutoUpdateUtils.getInstance(MainActivity.this).check(new ForceExitCallBack() {
//            @Override
//            public void exit() {
//                在这里退出整个app
//                MainActivity.this.finish();
//            }
//        });



//        DownloadInfo info = new DownloadInfo().setApkUrl("https://apkdownload.followme.cn/Followme-official-release.apk");
        DownloadInfo info = new DownloadInfo().setApkUrl("http://jokesimg.cretinzp.com/apk/app-release_224_jiagu_sign.apk")
                .setFileSize(31338250)
                .setProdVersionCode(19)
                .setProdVersionName("2.3.1")
                .setForceUpdate(false)
                .setUpdateLog("新版本特性：\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙\n1.开户银行：江西银\n2.户姓名：小龙人\n3.子账户：621246000000000000\n4.户姓名：小龙");
        AppUpdateUtils.getInstance().checkUpdate(info);

        //这里就是不处理强制更新的情况
//        CretinAutoUpdateUtils.getInstance(MainActivity.this).check();

        ///-------------------------------------------------
        /**
         *  感谢网友的提示  他说：毕竟不是每个app都是web的 我是c++后台
         *  所以有必要提供一个给定数据的入口，然后根据提供的数据来进行版本的更新 所以就有了下面这个
         */
        //模拟请求到的数据
//        String json = "{'versionCode':10,'isForceUpdate':0,'preBaselineCode':9,'versionName':'v1.3.0'," +
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //一定要调用
        CretinAutoUpdateUtils.getInstance(this).destroy();
    }
}