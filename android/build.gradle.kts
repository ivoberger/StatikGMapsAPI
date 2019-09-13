import com.dicedmelon.gradle.jacoco.android.JacocoAndroidUnitTestReportExtension

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    id("org.jetbrains.dokka")
    id("digital.wup.android-maven-publish")
    id("jacoco-android")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
    }
}

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
}

publishing {
    publications {
        create<MavenPublication>(name) {
            from(components["android"])

            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])
        }
    }
}
