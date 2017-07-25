#AutoUpdateProject
-------------------

特点概述

一、可从后台主动控制本地app强制更新，主要适用场合是某个版本有bug，会严重影响用户的使用，此时用这种模式，只要用户打开app，提醒强制更新，否则不能进入app；

二、根据后台返回受影响的版本号，可控制多个版本同时被强制更新；

三、后台返回最新安装包大小，本地判断安装包是否下载，防止多次下载；

四、内部处理，忽略此版本更新提示

五、library采用无第三方工具类，下载使用HttpURLConnextion，本地存储使用SharedPrefference，以免使用此library带来第三方插件冲突

六、library适配Android 7.0

七、library默认请求方式为POST请求，对于GET请求无法正常请求，提供配置请求方式

八、library添加了对log日志的开关，在调试阶段可打开调试log输出，提交生产环境的时候可以主动的关闭

九、library支持判断网络环境，移动数据下智能提示，防止流量外漏。

十、考虑到减少与后台人员的配合的高度耦合，增加了自定义model的入口，具体使用请关注使用方式。

十一、提供两种模式的版本更新，一种是对话框显示下载进度，一种是通知栏显示后台默默下载形式

-------------------

##最新版本V1.1.3
**1、** 现在最新版是V1.1.3，如果你之前没有使用过，请先看最下面的集成步骤，再依次从下往上查看集成步骤

**2、** 添加依赖的时候注意版本

```gradle
dependencies {
	        compile 'com.github.MZCretin:AutoUpdateProject:v1.1.3'
	}
```
**3、** 更新说明：修复了创建文件失败的bug

##最新版本V1.1.2

**1、** 现在最新版是V1.1.2，如果你之前没有使用过，请先看最下面的集成步骤，再依次从下往上查看集成步骤

**2、** 添加依赖的时候注意版本

```gradle
dependencies {
	        compile 'com.github.MZCretin:AutoUpdateProject:v1.1.2'
	}
```

**3、** 自定义字段的说明

```
CretinAutoUpdateUtils.Builder builder = new CretinAutoUpdateUtils.Builder()
                //设置更新api 
                .setBaseUrl("http://101.201.31.212:8016/version/checkVersion")
                //设置是否显示忽略此版本 
                .setIgnoreThisVersion(false)
                //设置下载显示形式 对话框或者通知栏显示 二选一 
                .setShowType(CretinAutoUpdateUtils.Builder.TYPE_DIALOG)
                //设置下载时展示的图标 
                .setIconRes(R.mipmap.ic_launcher)
                //设置是否打印log日志
                .showLog(true)
                //设置请求方式
                .setRequestMethod(CretinAutoUpdateUtils.Builder.METHOD_GET)
                //设置下载时展示的应用名称
                .setAppName("测试应用") 
                //设置自定义的Model类
                .setTransition(new UpdateModel())
                .build();
CretinAutoUpdateUtils.init(builder);
```

**4、** 相关说明

首先，在初始化sdk的时候采用自定义参数的方式，使用 new CretinAutoUpdateUtils.Builder().
setTransition(new UpdateModel())方式传入你自定义的model类，请注意，自定义的UpdateModel
类必须实现sdk的LibraryUpdateEntity接口，并实现该接口所必须实现的方法，在这些方法中返回给
sdk必须的字段，这样sdk才能成功的获取相对应的信息。下面是一个具体的实现：
```
import com.cretin.www.cretinautoupdatelibrary.model.LibraryUpdateEntity;

/**
 * Created by cretin on 2017/4/21.
 */

public class UpdateModel implements LibraryUpdateEntity{

    /**
     * id : test
     * page : 1
     * rows : 10
     * isForceUpdate : 0
     * preBaselineCode : 0
     * versionName : V1.0.1
     * versionCode : 3
     * downurl : http://120.24.5.102/Webconfig/frj01_211_jiagu_sign.apk
     * updateLog : 1、修复bug
     * size : 10291218
     * hasAffectCodes : 1|2
     * createTime : 1489651956000
     * iosVersion : 1
     */

    private String id;
    private int page;
    private int rows;
    private int isForceUpdate;
    private int preBaselineCode;
    private String versionName;
    private int versionCode;
    private String downurl;
    private String updateLog;
    private String size;
    private String hasAffectCodes;
    private long createTime;
    private int iosVersion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getIsForceUpdate() {
        return isForceUpdate;
    }

    public void setIsForceUpdate(int isForceUpdate) {
        this.isForceUpdate = isForceUpdate;
    }

    public int getPreBaselineCode() {
        return preBaselineCode;
    }

    public void setPreBaselineCode(int preBaselineCode) {
        this.preBaselineCode = preBaselineCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getDownurl() {
        return downurl;
    }

    public void setDownurl(String downurl) {
        this.downurl = downurl;
    }

    public String getUpdateLog() {
        return updateLog;
    }

    public void setUpdateLog(String updateLog) {
        this.updateLog = updateLog;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getHasAffectCodes() {
        return hasAffectCodes;
    }

    public void setHasAffectCodes(String hasAffectCodes) {
        this.hasAffectCodes = hasAffectCodes;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getIosVersion() {
        return iosVersion;
    }

    public void setIosVersion(int iosVersion) {
        this.iosVersion = iosVersion;
    }

    @Override
    public int getVersionCodes() {
        return getVersionCode();
    }

    @Override
    public int getIsForceUpdates() {
        return getIsForceUpdate();
    }

    @Override
    public int getPreBaselineCodes() {
        return getPreBaselineCode();
    }

    @Override
    public String getVersionNames() {
        return getVersionName();
    }

    @Override
    public String getDownurls() {
        return getDownurl();
    }

    @Override
    public String getUpdateLogs() {
        return getUpdateLog();
    }

    @Override
    public String getApkSizes() {
        return getSize();
    }

    @Override
    public String getHasAffectCodess() {
        return getHasAffectCodes();
    }
}
```


-------------------

##最新版本V1.1.1

版本更新说明：

**1、** 现在最新版是V1.1.1，如果你之前没有使用过，请先看最下面的集成步骤，再依次从下往上查看集成步骤

**2、** 添加依赖的时候注意版本

```gradle
dependencies {
	        compile 'com.github.MZCretin:AutoUpdateProject:v1.1.1'
	}
```

**3、** 自定义字段的说明

```
CretinAutoUpdateUtils.Builder builder = new CretinAutoUpdateUtils.Builder()
                //设置更新api 
                .setBaseUrl("http://101.201.31.212:8016/version/checkVersion")
                //设置是否显示忽略此版本 
                .setIgnoreThisVersion(false)
                //设置下载显示形式 对话框或者通知栏显示 二选一 
                .setShowType(CretinAutoUpdateUtils.Builder.TYPE_DIALOG)
                //设置下载时展示的图标 
                .setIconRes(R.mipmap.ic_launcher)
                //设置是否打印log日志
                .showLog(true)
                //设置请求方式
                .setRequestMethod(CretinAutoUpdateUtils.Builder.METHOD_GET)
                //设置下载时展示的应用名称
                .setAppName("测试应用") 
                .build();
CretinAutoUpdateUtils.init(builder);
```

**4、** 相关说明

由于之前的版本默认请求方式为POST请求，对于GET请求无法正常请求，所以在本次更新的时候添加了自定义请求的配置；

还添加了对log日志的开关，在调试阶段可打开调试log输出，提交生产环境的时候可以主动的关闭。

优化了产生错误之后的处理。

欢迎在issue里面提出问题，共同优化

-------------------

##V1.0.2版本


在小伙伴的使用过程中，发现这个插件不能支持Android 7.0系统，于是我意识到，这是Android 7.0在Android apk做了处理，不能用以前的方式来进行Apk的安装了，所以急急忙忙的进行了升级和更新。

更新说明：

**1、** 现在最新版是v1.0.2，如果你之前没有使用过，请先看最下面的集成步骤，再依次从下往上查看集成步骤

**2、** 添加依赖的时候注意版本
```gradle
dependencies {
	        compile 'com.github.MZCretin:AutoUpdateProject:v1.0.2'
	}
```

**3、** 在app module 的 res下建立一个xml文件夹，新建一个install_file.xml，在该文件内填写以下内容：
``` xml
    <?xml version="1.0" encoding="utf-8"?>
    <paths>
        <external-path path="Android/data/$(applicationId)/"  name="files_root" />
        <external-path path="." name="cretin_install" />
    </paths>
```

**4、** 在AndroidManifest.xml文件的application标签内填下以下内容
``` xml
    <provider
                android:name="android.support.v4.content.FileProvider"
                android:authorities="${applicationId}.fileprovider"
                android:grantUriPermissions="true"
                android:exported="false"
                >
                <meta-data
                    android:name="android.support.FILE_PROVIDER_PATHS"
                    android:resource="@xml/install_file" />
            </provider>
```

-------------------



##V1.0版本

##使用方式：

-------------------

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


##使用说明

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

####有什么意见或者建议欢迎与我交流，觉得不错欢迎Star

使用过程中如果有什么问题或者建议 欢迎在issue中提出来或者直接联系我 792075058 嘿嘿

PS:如果显示异常，欢迎移步 http://blog.csdn.net/u010998327/article/details/62036622
