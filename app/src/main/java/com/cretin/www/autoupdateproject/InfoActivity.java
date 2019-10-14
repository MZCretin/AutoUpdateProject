
package com.cretin.www.autoupdateproject;

import android.support.v4.text.util.LinkifyCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {
    private TextView tv_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        tv_info = findViewById(R.id.tv_info);

        String info = "首先，感谢您体验或使用我的版本更新库！\n" +
                "另外，这是一个大的版本修改，之前的老版本的方式依然可以使用，只是我不再推荐，也不做文档说明了！新版本做了很多优化！\n\n" +
                "功能介绍：\n\n" +
                "一、最大亮点，提供12种更新的样式，总有一个是你喜欢的！\n\n" +
                "二、支持三种设置更新信息的方式，您可以直接传model，传json数据，或者直接配置请求链接，sdk会自主请求并发起app的更新！\n\n" +
                "三、文件下载支持断点续传，节省流量！\n\n" +
                "四、相同版本的apk只会下载一次，减少重复下载\n\n" +
                "五、已适配Android 6.0，7.0，8.0，9.0\n\n" +
                "六、提供强制更新，不更新则无法使用APP\n\n" +
                "七、通知栏图片自定义\n\n" +
                "其他说明：\n\n" +
                "Demo下载下来的App是我业余写的一个段子类的App（段子乐），定期更新大量的搞笑段子和搞笑趣图，如果您感兴趣，可以安装之后在空闲的时候娱乐娱乐，如果您也是段友，还可发布您的作品！\n\n" +
                "Github地址：\n\n" +
                "https://github.com/MZCretin/AutoUpdateProject\n\n" +
                "记得点赞哦！！！";
        final SpannableString value = SpannableString.valueOf(info);
        LinkifyCompat.addLinks(value, Linkify.ALL);
        tv_info.setMovementMethod(LinkMovementMethod.getInstance());
        tv_info.setText(value);
    }
}
