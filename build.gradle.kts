import de.fayard.BuildSrcVersionsTask

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath(Libs.com_android_tools_build_gradle)
        classpath(Libs.kotlin_gradle_plugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    buildSrcVersions
}

allprojects {
    repositories {
        google()
        jcenter()

    }
}

tasks {
    register<Delete>("clean") {
        delete(buildDir, "app/build")
    }
    wrapper {
        gradleVersion = Versions.gradleLatestVersion
        distributionType = Wrapper.DistributionType.ALL
    }
    named<BuildSrcVersionsTask>("buildSrcVersions") {
        finalizedBy(wrapper)
    }
}

