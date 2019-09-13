import kotlin.String

/**
 * Generated by https://github.com/jmfayard/buildSrcVersions
 *
 * Update this file with
 *   `$ ./gradlew buildSrcVersions`
 */
object Libs {
  /**
   * https://developer.android.com/studio
   */
  const val aapt2: String = "com.android.tools.build:aapt2:" + Versions.aapt2

  /**
   * https://developer.android.com/studio
   */
  const val com_android_tools_build_gradle: String = "com.android.tools.build:gradle:" +
      Versions.com_android_tools_build_gradle

  /**
   * https://developer.android.com/studio
   */
  const val lint_gradle: String = "com.android.tools.lint:lint-gradle:" + Versions.lint_gradle

  const val de_fayard_buildsrcversions_gradle_plugin: String =
      "de.fayard.buildSrcVersions:de.fayard.buildSrcVersions.gradle.plugin:" +
      Versions.de_fayard_buildsrcversions_gradle_plugin

  /**
   * https://github.com/wupdigital/android-maven-publish
   */
  const val android_maven_publish: String = "digital.wup:android-maven-publish:" +
      Versions.android_maven_publish

  /**
   * http://www.github.com/kotlintest/kotlintest
   */
  const val kotlintest_runner_junit5: String = "io.kotlintest:kotlintest-runner-junit5:" +
      Versions.kotlintest_runner_junit5

  const val dokka_android_gradle_plugin: String =
      "org.jetbrains.dokka:dokka-android-gradle-plugin:" + Versions.org_jetbrains_dokka

  const val dokka_gradle_plugin: String = "org.jetbrains.dokka:dokka-gradle-plugin:" +
      Versions.org_jetbrains_dokka

  /**
   * https://kotlinlang.org/
   */
  const val kotlin_android_extensions_runtime: String =
      "org.jetbrains.kotlin:kotlin-android-extensions-runtime:" + Versions.org_jetbrains_kotlin

  /**
   * https://kotlinlang.org/
   */
  const val kotlin_android_extensions: String = "org.jetbrains.kotlin:kotlin-android-extensions:" +
      Versions.org_jetbrains_kotlin

  /**
   * https://kotlinlang.org/
   */
  const val kotlin_gradle_plugin: String = "org.jetbrains.kotlin:kotlin-gradle-plugin:" +
      Versions.org_jetbrains_kotlin

  /**
   * https://kotlinlang.org/
   */
  const val kotlin_scripting_compiler_embeddable: String =
      "org.jetbrains.kotlin:kotlin-scripting-compiler-embeddable:" + Versions.org_jetbrains_kotlin

  /**
   * https://kotlinlang.org/
   */
  const val kotlin_stdlib_jdk8: String = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:" +
      Versions.org_jetbrains_kotlin
}
