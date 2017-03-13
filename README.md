# AutoUpdateProject


1.0版本
版本更新library，提供两种模式的版本更新，一种是对话框显示下载进度，一种是通知栏显示后台默默下载形式

自定义：
可从后台主动控制本地app强制更新，主要适用场合是某个版本有bug，会严重影响用户的使用，此时用这种模式，只要用户打开app，提醒强制更新，否则不能进入app；根据后台返回受影响的版本号，可控制多个版本同时被强制更新；后台返回最新安装包大小，本地判断安装包是否下载，防止多次下载；

使用方式：
Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
allprojects {
repositories {
...
maven { url 'https://jitpack.io' }
}
}

Step 2. Add the dependency
dependencies {
compile 'com.github.MZCretin:AutoUpdateProject:v1.0'
}

Step 3.Init it in BaseApplication or MainActivity before using it.And then register BaseApplication in AndroidManifest(Don't forget it).There are two ways you can chose.
//第一种形式 自定义参数
CretinAutoUpdateUtils.Builder builder = new CretinAutoUpdateUtils.Builder()
//设置更新api
.setBaseUrl("http://120.24.5.102/weixin/app/getversion")
//设置是否显示忽略此版本
.setIgnoreThisVersion(true)
//设置下载显示形式 对话框或者通知栏显示 二选一
.setShowType(CretinAutoUpdateUtils.Builder.TYPE_DIALOG)
//设置下载时展示的图标
.setIconRes(R.mipmap.ic_launcher)
//设置下载时展示的应用吗
.setAppName("测试应用")
.build();
CretinAutoUpdateUtils.init(builder);
//第二种模式
//CretinAutoUpdateUtils.init("http://120.24.5.102/weixin/app/getversion");

Step 4.Add below codes to your app module's AndroidManifest file where under tags.

    //these codes...
    <service android:name="com.cretin.www.cretinautoupdatelibrary.utils.DownloadService"/>

Step 5.Start using it wherever you want as below.
CretinAutoUpdateUtils.getInstance(MainActivity.this).check();
