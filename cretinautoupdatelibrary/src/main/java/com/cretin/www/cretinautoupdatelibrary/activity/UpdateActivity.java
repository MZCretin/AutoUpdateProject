package com.cretin.www.cretinautoupdatelibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cretin.www.cretinautoupdatelibrary.R;
import com.cretin.www.cretinautoupdatelibrary.model.DownloadInfo;
import com.cretin.www.cretinautoupdatelibrary.utils.ResUtils;
import com.cretin.www.cretinautoupdatelibrary.view.ProgressView;

public class UpdateActivity extends AppCompatActivity {

    private DownloadInfo downloadInfo;

    //view
    private ImageView ivClose;
    private TextView tvTitle;
    private ProgressView progressView;
    private TextView tvMsg;
    private TextView tvBtn1;
    private TextView tvBtn2;
    private View viewLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_download_dialog);

        downloadInfo = getIntent().getParcelableExtra("info");

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
            }
        });

        tvBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //右边的按钮
            }
        });
    }

    private void findView() {
        ivClose = (ImageView) findViewById(R.id.iv_close);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        progressView = (ProgressView) findViewById(R.id.progressView);
        tvMsg = (TextView) findViewById(R.id.tv_msg);
        tvBtn1 = (TextView) findViewById(R.id.tv_btn1);
        tvBtn2 = (TextView) findViewById(R.id.tv_btn2);
        viewLine = findViewById(R.id.view_line);
    }

    /**
     * 启动Activity
     *
     * @param context
     * @param info
     */
    public static void launch(Context context, DownloadInfo info) {
        Intent intent = new Intent(context, UpdateActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("info", info);
        context.startActivity(intent);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.dialog_enter, R.anim.dialog_out);
    }

    @Override
    public void onBackPressed() {
        if (!downloadInfo.isForceUpdate())
            super.onBackPressed();
    }
}
