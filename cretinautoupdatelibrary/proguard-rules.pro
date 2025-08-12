# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/cretin/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Keep FileDownloader classes to prevent NoClassDefFoundError
-keep class com.liulishuo.filedownloader.** { *; }
-keepclassmembers class com.liulishuo.filedownloader.** { *; }

# Keep all public classes and methods in this library
-keep public class com.cretin.www.cretinautoupdatelibrary.** { *; }

# Keep all classes with annotations
-keep class ** {
    @com.cretin.www.cretinautoupdatelibrary.** *;
}

# Keep all callback interfaces and listeners
-keep interface com.cretin.www.cretinautoupdatelibrary.interfaces.** { *; }
