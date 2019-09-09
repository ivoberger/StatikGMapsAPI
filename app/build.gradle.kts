plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")

}

android {
    compileSdkVersion (28)
    defaultConfig {
        applicationId = "com.ivoberger.statikgmapsapi"
        minSdkVersion (21)
        targetSdkVersion (28)
        versionCode =1
        versionName ="1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(Libs.kotlin_stdlib_jdk8)
    implementation(Libs.appcompat)
    implementation(Libs.core_ktx)
}
