# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\AndroidStudioProjects11111111111\ANDROIDSTUIDOSDK1/tools/proguard/proguard-android.txt
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
-dontoptimize
-ignorewarnings
-keeppackagenames com.baidu.**
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,LocalVariable*Table,*Annotation*,Synthetic,EnclosingMethod
-dontwarn com.baidu.**

-dontwarn com.baidu.navisdk.**
-dontwarn com.baidu.navi.**

-keep class com.baidu.** { *; }
-keep interface com.baidu.** { *; }
-keep class vi.com.gdi.** { *; }
-keep interface vi.com.gdi.** { *; }
-keep class **.R$* { *; }


#百度内部依赖 --------------------------------------------------------------------
#navisdk tts
-keep class com.baidu.speechsynthesizer.**{*;}
-keep interface com.baidu.speechsynthesizer.**{*;}
-dontwarn com.baidu.speechsynthesizer.**
-keep class com.baidu.tts.**{*;}
-keep interface com.baidu.tts.**{*;}
-dontwarn com.baidu.tts.**

-keep class com.baidu.sapi2.** {*;}
-keepattributes JavascriptInterface
-keepattributes *Annotation*



#Android --------------------------------------------------------------------
-keep class * extends android.app.Activity
-keep class * extends android.app.Application
-keep class * extends android.app.Service
-keep class * extends android.content.BroadcastReceiver
-keep class * extends android.content.ContentProvider
-keep class * extends android.app.backup.BackupAgentHelper
-keep class * extends android.preference.Preference
-keep class * extends android.os.Bundle

-dontwarn com.google.android.support.v4.**
-keep class com.google.android.support.v4.** { *; }
-keep interface com.google.android.support.v4.app.** { *; }
-keep public class * extends com.google.android.support.v4.**
-keep public class * extends com.google.android.support.v4.app.Fragment

-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v4.app.Fragment
-keep class com.baidu.vi.** { *; }
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}


#第三方 --------------------------------------------------------------------
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *;}

-dontwarn com.google.gson.**
-keep class com.google.gson.** { *;}

-dontwarn org.litepal.**
-keep class org.litepal.** { *;}

-keep class cn.thinkit.libtmfe.test.JNI{
    public protected <fields>;
    public protected <methods>;
}
-keep class com.baidu.** {*;}
-keep public class android.net.http.SslError
-dontwarn android.net.http.SslError