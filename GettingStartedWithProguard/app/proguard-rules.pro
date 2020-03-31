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

-dontwarn org.slf4j.**
-dontwarn sun.misc.**

-keep class org.jbox2d.** { *; }

-dontwarn javax.xml.stream.**
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

-keep class * implements org.simpleframework.xml.core.Parameter { public *; }

-keepclassmembers class * { @org.simpleframework.xml.* *; }

# That being said, obfuscation is sometimes used to hide or obscure proprietary logic or a secret algorithm.
# Sometimes developers apply manual obfuscation. Some examples are string splitting, dummy code, disguising
# the names of methods, or using reflection to muddy the app flow. You’ll add some reflection code that obfuscates
# your secret bubble-gradient formula. As of right now, it’s not impossible for an attacker to find the numbers
# used to make the gradient.
# When Android Studio compiles your app, it puts the code into that classes.dex DEX (Dalvik Executable) file.
# DEX files contain Bytecode – an intermediary set of instructions that a Java Virtual Machine (JVM) runs or ART
# (The Android Runtime) later converts to native code. With this being exposed, an attacker could potentially see
# values that your variables hold!
-keep class kotlin.reflect.jvm.internal.** { *; }
-keep class kotlin.Metadata { *; }