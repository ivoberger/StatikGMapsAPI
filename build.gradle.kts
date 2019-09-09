import de.fayard.BuildSrcVersionsTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath(Libs.com_android_tools_build_gradle)
        classpath(Libs.kotlin_gradle_plugin)
        classpath(Libs.dokka_gradle_plugin)
        classpath(Libs.dokka_android_gradle_plugin)
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

subprojects {
    group = "com.ivoberger.statikgmapsapi"
    version = "0.1.0"

    apply(plugin = "org.gradle.jacoco")

    tasks.withType<KotlinCompile> { kotlinOptions { jvmTarget = "1.8" } }
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

