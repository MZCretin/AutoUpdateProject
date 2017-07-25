package com.cretin.www.autoupdateproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.cretin.www.cretinautoupdatelibrary.interfaces.ForceExitCallBack;
import com.cretin.www.cretinautoupdatelibrary.utils.CretinAutoUpdateUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.dialog);
//        View view = View.inflate(this, R.layout.download_dialog_super, null);
//        builder.setView(view);
//        builder.show();
    }

    //更新
    public void update(View view) {
        CretinAutoUpdateUtils.getInstance(MainActivity.this).check(new ForceExitCallBack() {
            @Override
            public void exit() {
                MainActivity.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CretinAutoUpdateUtils.getInstance(this).destroy();
    }

    private float getScaleSize(Context context) {
        TextView tv = new TextView(context);
        tv.setTextSize(1);
        return tv.getTextSize();
    }
}
