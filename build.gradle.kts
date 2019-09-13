import de.fayard.BuildSrcVersionsTask
import org.jetbrains.dokka.gradle.DokkaTask
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
        classpath(Libs.android_maven_publish)
        classpath(Libs.jacoco_android)
    }
}

plugins {
    buildSrcVersions
    jacoco
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

subprojects {
    group = "com.ivoberger.statikgmapsapi"
    version = "0.4.0"

    apply(plugin = "org.jetbrains.dokka")

    tasks.register<Jar>("javadocJar") {
        val dokka = tasks.named<DokkaTask>("dokka")
        archiveClassifier.set("javadoc")
        from(dokka.get().outputDirectory)
        dependsOn(dokka)
    }

    afterEvaluate {
        dependencies {
            "implementation"(Libs.kotlin_stdlib_jdk8)
            "testImplementation"(Libs.kotlintest_runner_junit5)
        }
        tasks.named<DokkaTask>("dokka") {
            outputFormat = "html"
            outputDirectory = "$buildDir/javadoc"
        }
    }


    tasks.withType<KotlinCompile> { kotlinOptions { jvmTarget = "1.8" } }
}

tasks {
    wrapper {
        gradleVersion = Versions.gradleLatestVersion
        distributionType = Wrapper.DistributionType.ALL
    }
    named<BuildSrcVersionsTask>("buildSrcVersions") {
        finalizedBy(wrapper)
    }
}

