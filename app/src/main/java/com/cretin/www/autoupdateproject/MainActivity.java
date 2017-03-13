package com.cretin.www.autoupdateproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cretin.www.cretinautoupdatelibrary.utils.CretinAutoUpdateUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
