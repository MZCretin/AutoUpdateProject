package com.cretin.www.autoupdateproject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cretin.www.cretinautoupdatelibrary.utils.RootActivity;
import com.cretin.www.cretinautoupdatelibrary.interfaces.AppDownloadListener;
import com.cretin.www.cretinautoupdatelibrary.interfaces.MD5CheckListener;
import com.cretin.www.cretinautoupdatelibrary.utils.ResUtils;

/**
 * 自定义UI类型
 * 你需要注意：
 * 1、Activity需要继承RootActivity才有效，并实现需要实现的方法
 * 2、你需要在你的AndroidManifest.xml中为这个自定义的Activity设置
 *
 * @style/DialogActivityTheme 这个对话框的主题；
 * 当然这只是建议，你只要撸出来的页面好看，咋地都行
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

    /**
     * 如果需要知道文件MD5校验结果就重写此方法
     *
     * @return
     */
    @Override
    public MD5CheckListener obtainMD5CheckListener() {
        return new MD5CheckListener() {
            @Override
            public void fileMd5CheckFail(String originMD5, String localMD5) {

            }

            @Override
            public void fileMd5CheckSuccess() {

            }
        };
    }
}
