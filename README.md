# AutoUpdateProject

### 特点概述

+ 最大亮点，提供**12**种更新的样式，总有一个是你喜欢的类型！
+ 支持三种设置更新信息的方式，您可以直接传model，传json数据，或者直接配置请求链接，sdk会自主请求并发起app的更新，满足多方需求！
+ 文件下载支持断点续传，下载错误拥有重试机制；相同版本的apk只会下载一次，防止重复下载！
+ 使用接口方式获取数据时支持设置请求头，支持POST和GET请求方式，更灵活。
+ 已适配Android 6.0，Android 7.0，Android 8.0，Android 9.0。
+ 提供强制更新，不更新则无法使用APP，同时可以根据后台返回受影响的版本号，可控制多个版本同时被强制更新。
+ 通知栏图片自定义

### 使用方式

**Step 1.** Add the JitPack repository to your build file Add it in your root build.gradle at the end of repositories: 
```gradle
allprojects { repositories { ... maven { url 'https://jitpack.io' } } }
```

**Step 2.** Add the dependency
```gradle
dependencies { compile 'com.github.MZCretin:AutoUpdateProject:v1.0' }
```

**Step 3.** Init it in BaseApplication or MainActivity before using it.And then register BaseApplication in AndroidManifest(Don't forget it).There are two ways you can chose.

```
//第一种形式 自定义参数 
CretinAutoUpdateUtils.Builder builder = 
		new CretinAutoUpdateUtils.Builder() 
		//设置更新api 
		.setBaseUrl("http://120.24.5.102/weixin/app/getversion") 
		//设置是否显示忽略此版本 
		.setIgnoreThisVersion(true) 
		//设置下载显示形式 对话框或者通知栏显示 二选一 
		.setShowType(CretinAutoUpdateUtils.Builder.TYPE_DIALOG) 
		//设置下载时展示的图标 
		.setIconRes(R.mipmap.ic_launcher) 
		//设置下载时展示的应用名称
		.setAppName("测试应用") 
		.build(); 
CretinAutoUpdateUtils.init(builder); 

//第二种模式 
//CretinAutoUpdateUtils.init("http://120.24.5.102/weixin/app/getversion");
```


**Step 4.** Add below codes to your app module's AndroidManifest file where under tags.
```xml
<application
        android:name=".BaseApp"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>

                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>

        <service android:name="com.cretin.www.cretinautoupdatelibrary.utils.DownloadService"/>
    </application>
```

**Step 5.** Start using it wherever you want as below.

```
CretinAutoUpdateUtils.getInstance(MainActivity.this).check();
```


## 使用说明

-------------------
此library的使用需要与后台联动配合，下面是后台需要返回给我们使用的字段说明：

```
/**
 * Created by cretin on 2017/3/8.
 */

public class UpdateEntity {
    public int versionCode = 0;
    //是否强制更新 0 不强制更新 1 hasAffectCodes拥有字段强制更新 2 所有版本强制更新
    public int isForceUpdate = 0;
    //上一个版本版本号
    public int preBaselineCode = 0;
    //版本号 描述作用
    public String versionName = "";
    //新安装包的下载地址
    public String downurl = "";
    //更新日志
    public String updateLog = "";
    //安装包大小 单位字节
    public String size = "";
    //受影响的版本号 如果开启强制更新 那么这个字段包含的所有版本都会被强制更新 格式 2|3|4
    public String hasAffectCodes = "";

    public UpdateEntity(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        this.versionCode = jsonObject.getInt("versionCode");
        this.versionName = jsonObject.getString("versionName");
        this.isForceUpdate = jsonObject.getInt("isForceUpdate");
        this.downurl = jsonObject.getString("downurl");
        this.preBaselineCode = jsonObject.getInt("preBaselineCode");
        this.updateLog = jsonObject.getString("updateLog");
        this.size = jsonObject.getString("size");
        this.hasAffectCodes = jsonObject.getString("hasAffectCodes");
    }
}java

```
-------------------
所以需要后台返回给我们这些字段，这些字段都是必须的，相关说明请看注释，下面是一个参考
```json
{
    "versionCode": "18", 
    "isForceUpdate": "1", 
    "preBaselineCode": "0", 
    "versionName": "2.1.1", 
    "downurl": "http://120.24.5.102/Webconfig/frj01_211_jiagu_sign.apk", 
    "hasAffectCodes": "11|12|13|14|15|16|17", 
    "updateLog": "1、修复bug 2、完善部分功能点 3、系统升级，强制更新",
    "size": 10291218
}
```

#### 有什么意见或者建议欢迎与我交流，觉得不错欢迎Star

使用过程中如果有什么问题或者建议 欢迎在issue中提出来或者直接联系我 792075058 嘿嘿

PS:如果显示异常，欢迎移步 http://blog.csdn.net/u010998327/article/details/62036622
