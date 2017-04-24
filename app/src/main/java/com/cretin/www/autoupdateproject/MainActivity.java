package com.cretin.www.autoupdateproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.cretin.www.cretinautoupdatelibrary.model.UpdateEntity;
import com.cretin.www.cretinautoupdatelibrary.utils.CretinAutoUpdateUtils;
import com.cretin.www.cretinautoupdatelibrary.utils.JSONHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String ss = "{'isForceUpdate':0,'preBaselineCode':0,'versionName':'V1.0.1','versionCode':3,'downurl':'http://120.24.5.102/Webconfig/frj01_211_jiagu_sign.apk','updateLog':'1、修复bug','size':'10291218','hasAffectCodes':'1|2'}";
        try {
            UpdateEntity jsonUser = JSONHelper.parseObject(ss, UpdateEntity.class);    //反序列化
            Log.e("", "");
        } catch ( Exception e ) {
            Log.e("", "");
        }


    }

    //更新
    public void update(View view) {
        CretinAutoUpdateUtils.getInstance(MainActivity.this).check();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CretinAutoUpdateUtils.getInstance(this).destroy();
    }
}
