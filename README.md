# AutoUpdateProject

[![](https://jitpack.io/v/MZCretin/AutoUpdateProject.svg)](https://jitpack.io/#MZCretin/AutoUpdateProject)

### 特点概述

+ **最大亮点，提供**12**种更新的样式，总有一个是你喜欢的类型！**
+ **支持三种设置更新信息的方式，您可以直接传model，传json数据，或者直接配置请求链接，sdk会自主请求并发起app的更新，满足多方需求！**
+ **文件下载支持断点续传，下载错误拥有重试机制；相同版本的apk只会下载一次，防止重复下载！**
+ **使用接口方式获取数据时支持设置请求头，支持POST和GET请求方式，更灵活。**
+ **调用者可以自定义UI实现更新功能。**
+ **已适配Android 6.0，Android 7.0，Android 8.0，Android 9.0。**
+ **提供强制更新，不更新则无法使用APP，同时可以根据后台返回受影响的版本号，可控制多个版本同时被强制更新。**
+ **通知栏图片自定义**

### 博客地址

[掘金-【需求解决系列之四】Android App在线自动更新Library（V2.0）](https://juejin.im/post/5da491535188255a31329231)

[简书-【需求解决系列之四】Android App在线自动更新Library（V2.0）](https://www.jianshu.com/p/9322785ffabf)

### Demo体验

[Demo下载](https://raw.githubusercontent.com/MZCretin/AutoUpdateProject/master/pic/demo.apk)

扫描二维码下载：
<img src="./pic/erweima.png"/>

### 效果预览

**说明： 以下12个更新的样式的类型值从左到右从上到下一次为** 

**TypeConfig.UI_THEME_A**、**TypeConfig.UI_THEME_B**、**TypeConfig.UI_THEME_C**、**TypeConfig.UI_THEME_D**、**TypeConfig.UI_THEME_E**、**TypeConfig.UI_THEME_F**、**TypeConfig.UI_THEME_G**、**TypeConfig.UI_THEME_H**、**TypeConfig.UI_THEME_I**、**TypeConfig.UI_THEME_J**、**TypeConfig.UI_THEME_K**、**TypeConfig.UI_THEME_L**

<div align=center ><img width="50%" height="100%" src="./pic/type01.png"  alt="UI_THEME_A"/><img width="50%" height="100%" src="./pic/type02.png" alt="UI_THEME_B"/><img width="50%" height="100%" src="./pic/type03.png" alt="UI_THEME_C"/><img width="50%" height="100%" src="./pic/type04.png" alt="UI_THEME_D"/><img width="50%" height="100%" src="./pic/type05.png" alt="UI_THEME_E"/><img width="50%" height="100%" src="./pic/type06.png" alt="UI_THEME_F"/><img width="50%" height="100%" src="./pic/type07.png" alt="UI_THEME_G"/><img width="50%" height="100%" src="./pic/type08.png" alt="UI_THEME_H"/><img width="50%" height="100%" src="./pic/type09.png" alt="UI_THEME_I"/><img width="50%" height="100%" src="./pic/type10.png" alt="UI_THEME_J"/><img width="50%" height="100%" src="./pic/type11.png" alt="UI_THEME_K"/><img width="50%" height="100%" src="./pic/type12.png" alt="UI_THEME_L"/></div>


### 使用方式

**Step 1.** Add the JitPack repository to your build file Add it in your root build.gradle at the end of repositories: 

```gradle
allprojects { repositories { ... maven { url 'https://jitpack.io' } } }
```

**Step 2.** Add the dependency
```gradle
dependencies { implementation 'com.github.MZCretin:AutoUpdateProject:latest_version' }
```

**Step 3.** Init it in BaseApplication or MainActivity before using it.And then register BaseApplication in AndroidManifest(Don't forget it).

```java
//更新库配置
UpdateConfig updateConfig = new UpdateConfig()
        .setDebug(true)//是否是Debug模式
        .setBaseUrl("http://www.cretinzp.com/system/versioninfo")//当dataSourceType为DATA_SOURCE_TYPE_URL时，配置此接口用于获取更新信息
        .setMethodType(TypeConfig.METHOD_GET)//当dataSourceType为DATA_SOURCE_TYPE_URL时，设置请求的方法
        .setDataSourceType(TypeConfig.DATA_SOURCE_TYPE_URL)//设置获取更新信息的方式
        .setShowNotification(true)//配置更新的过程中是否在通知栏显示进度
        .setNotificationIconRes(R.mipmap.download_icon)//配置通知栏显示的图标
        .setUiThemeType(TypeConfig.UI_THEME_AUTO)//配置UI的样式，一种有12种样式可供选择
        .setRequestHeaders(null)//当dataSourceType为DATA_SOURCE_TYPE_URL时，设置请求的请求头
        .setRequestParams(null)//当dataSourceType为DATA_SOURCE_TYPE_URL时，设置请求的请求参数
        .setCustomActivityClass(CustomActivity.class)//如果你选择的UI样式为TypeConfig.UI_THEME_CUSTOM，那么你需要自定义一个Activity继承自RootActivity，并参照demo实现功能，在此处填写自定义Activity的class
        .setModelClass(new UpdateModel());
AppUpdateUtils.init(this, updateConfig);
```

**Step 4.** Start using it wherever you want as below with 3 ways.

```java
//有三种方式实现app更新，您可选其中一种方式来进行，推荐使用第三种方式！

//第一种方式，使用JSON字符串，让sdk自主解析并实现功能
String jsonData = "{\"versionCode\": 25,\"isForceUpdate\": 1,\"preBaselineCode\": 24,\"versionName\": \"v2.3.1\",\"downurl\": \"http://jokesimg.cretinzp.com/apk/app-release_231_jiagu_sign.apk\",\"updateLog\": \"1、优化细节和体验，更加稳定\n2、引入大量优质用户\r\n3、修复已知bug\n4、风格修改\",\"size\": \"31338250\",\"hasAffectCodes\": \"1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24\"}";
AppUpdateUtils.getInstance().checkUpdate(jsonData);

//第二种方式，使用MODEL方式，组装好对应的MODEL，传入sdk中
DownloadInfo info = new DownloadInfo().setApkUrl("http://jokesimg.cretinzp.com/apk/app-release_231_jiagu_sign.apk")
        .setFileSize(31338250)
        .setProdVersionCode(25)
        .setProdVersionName("2.3.1")
        .setForceUpdateFlag(listModel.isForceUpdate() ? 1 : 0)
        .setUpdateLog("1、优化细节和体验，更加稳定\n2、引入大量优质用户\r\n3、修复已知bug\n4、风格修改");
AppUpdateUtils.getInstance().checkUpdate(info);

//第三种方式，在初始化的时候配置接口地址，sdk自主请求+解析实现功能（推荐）
AppUpdateUtils.getInstance().checkUpdate();

```

### 使用注意点

+ 最简单快捷的方式就是使用传入MODEL的形式，因为这种方式需要的配置最少，但是你需要自己处理请求，并保证最终调用checkUpdate(model)的时候是在主线程。
+ 在使用传入json这种方式的时候，你需要同样保证调用checkUpdate(json)的时候在主线程，并且你需要在初始化的时候配置json对应的model【setModelClass(new UpdateModel())】，并保证这个model实现了LibraryUpdateEntity接口。
+ 在使用配置接口地址的方式的时候，您需要设置一个请求链接地址！当然，如果是需要的话，您需要设置请求方式，设置请求头和请求参数；并且你需要在初始化的时候配置请求成功之后返回的数据所对应的model【setModelClass(new UpdateModel())】，并保证这个model实现了LibraryUpdateEntity接口。
+ 如果你需要自定义UI，请自定义一个普通的Activity就可以了，这个Activity需要继承RootActivity，这样你就拥有了自动更新的能力；另外建议给这个自定义的Activity添加一个主题@style/DialogActivityTheme，这样他就能以对话框的形式展示，据我们的UI说这样会好看点。

#### 有什么意见或者建议欢迎与我交流，觉得不错欢迎Star

使用过程中如果有什么问题或者建议，欢迎在issue中提出来或者直接联系我 mxnzp_life@163.com 嘿嘿！


