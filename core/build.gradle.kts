plugins {
    kotlin("jvm")
    jacoco
    `maven-publish`
}

tasks {
    jacocoTestReport {
        reports {
            xml.isEnabled = true
            html.isEnabled = false
        }
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
