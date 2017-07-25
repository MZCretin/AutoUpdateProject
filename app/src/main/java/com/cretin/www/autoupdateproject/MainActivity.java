package com.cretin.www.autoupdateproject;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cretin.www.cretinautoupdatelibrary.interfaces.ForceExitCallBack;
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


        //需要处理强制更新的时候调用带餐的check方法
        CretinAutoUpdateUtils.getInstance(MainActivity.this).check(new ForceExitCallBack() {
            @Override
            public void exit() {
                //在这里退出整个app
                MainActivity.this.finish();
            }
        });
        //这里就是不处理强制更新的情况
        //CretinAutoUpdateUtils.getInstance(MainActivity.this).check();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CretinAutoUpdateUtils.getInstance(this).destroy();
    }
}