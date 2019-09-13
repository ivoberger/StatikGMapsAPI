plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    `maven-publish`
}

tasks {
    dokka {
        outputFormat = "html"
        outputDirectory = "$buildDir/javadoc"
    }
    register<Jar>("javadocJar") {
        archiveClassifier.set("javadoc")
        from(dokka.get().outputDirectory)
        dependsOn(dokka)
    }
    jacocoTestReport {
        reports {
            xml.isEnabled = true
            html.isEnabled = false
        }
        dependsOn(test)
    }
    test {
        useJUnitPlatform()
    }
}

publishing {
    publications {
        create<MavenPublication>(name) {
            from(components["java"])

            artifact(tasks["kotlinSourcesJar"])
            artifact(tasks["javadocJar"])
        }
    }
}
