package com.cretin.www.cretinautoupdatelibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cretin.www.cretinautoupdatelibrary.LogUtils;
import com.cretin.www.cretinautoupdatelibrary.R;
import com.cretin.www.cretinautoupdatelibrary.interfaces.AppDownloadListener;
import com.cretin.www.cretinautoupdatelibrary.model.DownloadInfo;
import com.cretin.www.cretinautoupdatelibrary.utils.ResUtils;
import com.cretin.www.cretinautoupdatelibrary.view.ProgressView;

/**
 * 绿色样式并且在当前展示进度的样式
 */
public class UpdateType1Activity extends RootActivity {

    //view
    private ImageView ivClose;
    private TextView tvTitle;
    private ProgressView progressView;
    private TextView tvMsg;
    private TextView tvBtn1;
    private TextView tvBtn2;
    private View viewLine;
    private LinearLayout llProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_download_dialog);

        findView();

        setDataAndListener();
    }

    private void setDataAndListener() {
        tvMsg.setText(downloadInfo.getUpdateLog());
        tvMsg.setMovementMethod(ScrollingMovementMethod.getInstance());

        if (downloadInfo.isForceUpdate()) {
            tvBtn1.setVisibility(View.GONE);
            viewLine.setVisibility(View.GONE);
            ivClose.setVisibility(View.GONE);
            tvBtn2.setBackground(ResUtils.getDrawable(R.drawable.dialog_item_bg_selector_white_left_right_bottom));
        } else {
            tvBtn1.setVisibility(View.VISIBLE);
        }

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //左边的按钮
                finish();
            }
        });

        tvBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //右边的按钮
                download();
            }
        });
    }

    @Override
    public AppDownloadListener obtainDownloadListener() {
        return new AppDownloadListener() {
            @Override
            public void downloading(int progress) {
                progressView.setProgress(progress);
                tvBtn2.setText(ResUtils.getString(R.string.downloading)+progress+"%");
            }

            @Override
            public void downloadFail(String msg) {
                llProgress.setVisibility(View.GONE);
                tvMsg.setVisibility(View.VISIBLE);
                tvBtn2.setText(ResUtils.getString(R.string.btn_update_now));
                Toast.makeText(UpdateType1Activity.this, ResUtils.getString(R.string.apk_file_download_fail), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void downloadComplete(String path) {
                llProgress.setVisibility(View.GONE);
                tvMsg.setVisibility(View.VISIBLE);
                tvBtn2.setText(ResUtils.getString(R.string.btn_update_now));
            }

            @Override
            public void downloadStart() {
                llProgress.setVisibility(View.VISIBLE);
                tvMsg.setVisibility(View.GONE);
                tvBtn2.setText(ResUtils.getString(R.string.downloading));
            }

            @Override
            public void reDownload() {
                LogUtils.log("下载失败后点击重试");
            }
        };
    }


    private void findView() {
        ivClose = (ImageView) findViewById(R.id.iv_close);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        progressView = (ProgressView) findViewById(R.id.progressView);
        tvMsg = (TextView) findViewById(R.id.tv_msg);
        tvBtn1 = (TextView) findViewById(R.id.tv_btn1);
        tvBtn2 = (TextView) findViewById(R.id.tv_btn2);
        viewLine = findViewById(R.id.view_line);
        llProgress = findViewById(R.id.ll_progress);
    }

    /**
     * 启动Activity
     *
     * @param context
     * @param info
     */
    public static void launch(Context context, DownloadInfo info) {
        Intent intent = new Intent(context, UpdateType1Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("info", info);
        context.startActivity(intent);
    }

}
