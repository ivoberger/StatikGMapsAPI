import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    kotlin("jvm")
    `maven-publish`
    id("org.jetbrains.dokka")
}

tasks.named<DokkaTask>("dokka") {
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
}

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
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
            from(components["java"])

            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])
        }
    }
}

dependencies {
    implementation(Libs.kotlin_stdlib_jdk8)
    testImplementation(Libs.junit)
}
