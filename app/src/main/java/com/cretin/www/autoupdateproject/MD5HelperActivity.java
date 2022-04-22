package com.cretin.www.autoupdateproject;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cretin.www.autoupdateproject.utils.Md5Utils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

import static com.cretin.www.cretinautoupdatelibrary.utils.RootActivity.permission;
import static com.cretin.www.cretinautoupdatelibrary.utils.AppUtils.getAppLocalPath;

public class MD5HelperActivity extends AppCompatActivity {
    private TextView tvDownload;
    private EditText edContent;
    private TextView tvDetails;
    private boolean isDownloading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md5_helper);

        tvDownload = findViewById(R.id.tv_download);
        tvDetails = findViewById(R.id.tv_details);
        edContent = findViewById(R.id.ed_content);

        tvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDownloading) {
                    return;
                }
                if (TextUtils.isEmpty(edContent.getText().toString().trim())) {
                    Toast.makeText(MD5HelperActivity.this, "请输入文件下载地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M)) {
                    downloadFile(edContent.getText().toString().trim());
                } else {
                    //下载权限
                    int writePermission = ContextCompat.checkSelfPermission(MD5HelperActivity.this, permission);
                    if (writePermission == PackageManager.PERMISSION_GRANTED) {
                        //拥有权限则直接下载
                        downloadFile(edContent.getText().toString().trim());
                    } else {
                        // 申请权限
                        ActivityCompat.requestPermissions(MD5HelperActivity.this, new String[]{permission}, 1000);
                    }
                }
            }
        });
    }

    //下载文件
    private void downloadFile(String appUrl) {
        FileDownloader.setup(this);

        final String downloadUpdateApkFilePath = getAppLocalPath(this,"test");

        BaseDownloadTask downloadTask = FileDownloader.getImpl().create(appUrl)
                .setPath(downloadUpdateApkFilePath);
        downloadTask
                .addHeader("Accept-Encoding", "identity")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36")
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        isDownloading = true;
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        tvDownload.setText("已下载:" + (soFarBytes * 100l / totalBytes) + "%");
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        isDownloading = false;
                        tvDownload.setText("下载完成（点击可重新下载）");
                        //获取校验码
                        try {
                            String fileMD5String = Md5Utils.getFileMD5(new File(downloadUpdateApkFilePath));
                            tvDetails.setText(fileMD5String);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        isDownloading = false;
                        tvDownload.setText("下载出错（点击可重新下载）");
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        isDownloading = false;
                        tvDownload.setText("下载出错（点击可重新下载）");
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }
                })
                .setAutoRetryTimes(3)
                .start();
    }
}
