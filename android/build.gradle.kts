import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    id("org.jetbrains.dokka")
    id("digital.wup.android-maven-publish")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }

}

tasks.named<DokkaTask>("dokka") {
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
}

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
}

tasks.register<Jar>("javadocJar") {
    val dokka = tasks.named<DokkaTask>("dokka")
    archiveClassifier.set("javadoc")
    from(dokka.get().outputDirectory)
    dependsOn(dokka)
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

dependencies {
    implementation(Libs.kotlin_stdlib_jdk8)
    testImplementation(Libs.junit)
}
