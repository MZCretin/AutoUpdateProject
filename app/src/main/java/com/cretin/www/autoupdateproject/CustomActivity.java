package com.cretin.www.autoupdateproject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cretin.www.cretinautoupdatelibrary.activity.RootActivity;
import com.cretin.www.cretinautoupdatelibrary.activity.UpdateType2Activity;
import com.cretin.www.cretinautoupdatelibrary.interfaces.AppDownloadListener;
import com.cretin.www.cretinautoupdatelibrary.utils.ResUtils;

/**
 * 自定义UI类型
 */
public class CustomActivity extends RootActivity {
    private TextView tvInfo;
    private TextView tvUpdate;
    private TextView tvCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        findView();

        //当是强制更新的时候需要隐藏取消按钮 这个如果你在自定义UI的时候要注意
        if (downloadInfo.isForceUpdateFlag()) {
            tvCancel.setVisibility(View.GONE);
        } else {
            tvCancel.setVisibility(View.VISIBLE);
        }

        //设置更新信息
        tvInfo.setText("v" + downloadInfo.getProdVersionName() + "\n" +
                downloadInfo.getUpdateLog());
    }

    private void findView() {
        tvInfo = findViewById(R.id.tv_info);
        tvUpdate = findViewById(R.id.tv_update);
        tvCancel = findViewById(R.id.tv_cancel);

        //设置取消按钮的事件
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果你希望取消的同事也取消任务就调用cancelTask方法 否则直接finish就好
                cancelTask();
                finish();
            }
        });

        //开始获取信息并且下载
        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });
    }

    @Override
    public AppDownloadListener obtainDownloadListener() {
        return new AppDownloadListener() {
            @Override
            public void downloading(int progress) {
                //设置下载按钮的文案
                tvUpdate.setText(ResUtils.getString(com.cretin.www.cretinautoupdatelibrary.R.string.downloading) + progress + "%");
            }

            @Override
            public void downloadFail(String msg) {
                //下载失败的时候重新设置文案
                tvUpdate.setText(ResUtils.getString(com.cretin.www.cretinautoupdatelibrary.R.string.btn_update_now));
                Toast.makeText(CustomActivity.this, ResUtils.getString(com.cretin.www.cretinautoupdatelibrary.R.string.apk_file_download_fail), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void downloadComplete(String path) {
                //只需要你设置自定义页面上的文案即可 其他操作sdk会自己处理
                tvUpdate.setText(ResUtils.getString(com.cretin.www.cretinautoupdatelibrary.R.string.btn_update_now));
            }

            @Override
            public void downloadStart() {
                //下载开始了，设置按钮文案
                tvUpdate.setText(ResUtils.getString(com.cretin.www.cretinautoupdatelibrary.R.string.btn_update_now));
            }

            @Override
            public void reDownload() {

            }

            @Override
            public void pause() {

            }
        };
    }
}
