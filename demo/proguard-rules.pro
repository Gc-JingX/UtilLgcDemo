# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#指定代码的压缩级别
-optimizationpasses 5

# 是否使用大小写混合
-dontusemixedcaseclassnames

# 是否混淆第三方jar
-dontskipnonpubliclibraryclasses

# 混淆时是否做预校验
-dontpreverify

# 混淆时是否做预校验
-verbose

# 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# 保持android某些api不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.AppCompatActivity
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.support.v4.app.Fragment

-dontwarn org.xmlpull.v1.XmlPullParser
-dontwarn org.xmlpull.v1.XmlSerializer
-keep class org.xmlpull.v1.* {*;}
-keepattributes *Annotation*
#-keepattributes Signature


#  okio
-dontwarn okio.**
-dontwarn rx.**
-dontwarn com.squareup.okhttp.*
-dontwarn retrofit.appengine.UrlFetchClient
-keepattributes *Annotation*
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
   @retrofit.http.* <methods>;
}
-keepattributes Signaturerofit2.Platform$Java8

-keep class org.apache.**{*;}                                               #过滤commons-httpclient-3.1.jar

-keep class com.fasterxml.jackson.**{*;}                                    #过滤jackson-core-2.1.4.jar等

-dontwarn com.lidroid.xutils.**                                             #去掉警告
-keep class com.lidroid.xutils.**{*;}                                       #过滤xUtils-2.6.14.jar
-keep class * extends java.lang.annotation.Annotation{*;}                   #这是xUtils文档中提到的过滤掉注解


#  xutils
-dontwarn org.xutils.**
-keep class org.xutils.** { *;}

-keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.AppCompatActivity {
   public void *(android.view.View);
}
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

-keep class android.support.v4.**{*;}

-keepattributes *Annotation*

-dontwarn okio.**
-keep class okio.** { *;}

-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *;}

-dontwarn com.google.gson.**
-keep class com.google.gson.** {*;}

-dontwarn org.litepal.**
-keep class org.litepal.** {*;}



#  okhttp3
-dontwarn okhttp3.**
-keep class okhttp3.** { *;}
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
# OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
#-keepattributes Signature-keepattributes Exceptions
# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod

# glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
**[] $VALUES;
public *;
}

-keepclassmembers class ** {

@com.squareup.otto.Subscribe public *;

@com.squareup.otto.Produce public *;

}
#
-dontwarn android.support.**
-keep class android.support.** { *;}


# 注解
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.app.** { *; }
-keep public class * extends android.support.v7.**
-keep public class * extends android.app.Fragment


-keepnames class * implements java.io.Serializable
-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context);
}

-keepnames class * implements java.io.Serializable {
}
-keep public class * implements java.io.Serializable {*;}
-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}


-keep public class com.lgc.demo.R$*{
    public static final int *;
}
# 保持自定义类
-keep  class com.lgc.demo.util.feijinview.** { *; }
-keep  class com.lgc.demo.model.** { *; }

#极光推送
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-keep class com.google.protobuf.** {*;}
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

# 保持图表
-dontwarn com.jn.chart.**
-keep class com.jn.chart.** { *; }

-dontwarn com.github.chrisbanes.photoview.**
-keep class com.github.chrisbanes.photoview.** { *; }

-dontwarn com.sunfusheng.marqueeview.**
-keep class com.sunfusheng.marqueeview.** { *; }

-dontwarn com.yanzhenjie.recyclerview.swipe.**
-keep class com.yanzhenjie.recyclerview.swipe.** { *; }
#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}


-dontwarn com.dreamlive.upmarqueeview.**
-keep class com.dreamlive.upmarqueeview.** { *; }

-dontwarn com.lljjcoder.citypickerview.**
-keep class com.lljjcoder.citypickerview.** { *; }

-dontwarn com.lgc.garylianglib.**
-keep class com.lgc.garylianglib.** { *; }

-dontwarn com.github.chrisbanes.photoview.**
-keep class com.github.chrisbanes.photoview.** { *; }

-dontwarn com.jude.rollviewpager.**
-keep class com.jude.rollviewpager.** { *; }

#支付宝
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}
#微信
-keep class com.tencent.mm.opensdk.** {
   *;
}
-keep class com.tencent.wxop.** {
   *;
}
-keep class com.tencent.mm.sdk.** {
   *;
}