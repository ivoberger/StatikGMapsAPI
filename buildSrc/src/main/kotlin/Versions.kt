import kotlin.String
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

/**
 * Generated by https://github.com/jmfayard/buildSrcVersions
 *
 * Find which updates are available by running
 *     `$ ./gradlew buildSrcVersions`
 * This will only update the comments.
 *
 * YOU are responsible for updating manually the dependency version.
 */
object Versions {
    const val com_google_android_gms: String = "17.1.0"

    const val org_jetbrains_kotlin: String = "1.3.61"

    const val org_jetbrains_dokka: String = "0.9.18"

    const val com_android_tools_build_gradle: String = "3.5.3"

    const val de_fayard_buildsrcversions_gradle_plugin: String = "0.7.0"

    const val kotlintest_runner_junit5: String = "3.4.2"

    const val android_maven_publish: String = "3.6.3"

    const val jacoco_android: String = "0.1.4"

    const val lint_gradle: String = "26.5.3"

    const val aapt2: String = "3.5.3-5435860"

    /**
     * Current version: "5.6.2"
     * See issue 19: How to update Gradle itself?
     * https://github.com/jmfayard/buildSrcVersions/issues/19
     */
    const val gradleLatestVersion: String = "5.6.2"
}

/**
 * See issue #47: how to update buildSrcVersions itself
 * https://github.com/jmfayard/buildSrcVersions/issues/47
 */
val PluginDependenciesSpec.buildSrcVersions: PluginDependencySpec
    inline get() =
            id("de.fayard.buildSrcVersions").version(Versions.de_fayard_buildsrcversions_gradle_plugin)
