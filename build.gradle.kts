import com.android.build.gradle.LibraryExtension
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
        classpath(Libs.android_maven_publish)
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

    apply(plugin = "org.gradle.jacoco")
}

subprojects {
    group = "com.ivoberger.statikgmapsapi"
    version = "0.4.0"

    tasks.getting(Test::class) {
        useJUnitPlatform()
    }


    afterEvaluate {
        dependencies {
            "implementation"(Libs.kotlin_stdlib_jdk8)
            "testImplementation"(Libs.kotlintest_runner_junit5)
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
    register<JacocoReport>("codeCoverageReport") {
        executionData(fileTree(project.rootDir.absolutePath).include(("**/build/jacoco/*.exec")))

        subprojects.forEach {
            it.extensions.findByType<LibraryExtension>()?.let { android ->
                additionalSourceDirs(android.sourceSets.findByName("main")!!.java.sourceFiles)
                return@forEach
            }
            sourceSets(
                it.extensions.getByName<SourceSetContainer>("sourceSets").named<SourceSet>("main").get()
            )
        }


        reports {
            xml.isEnabled = true
            xml.destination = file("$buildDir/reports/jacoco/report.xml")
            html.isEnabled = false
        }

        dependsOn(subprojects.map { it.tasks.named("test") })
    }
}

