import org.gradle.api.plugins.ExtensionAware
import com.novoda.gradle.release.PublishExtension
import org.gradle.script.lang.kotlin.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.junit.platform.gradle.plugin.FiltersExtension
import org.junit.platform.gradle.plugin.EnginesExtension
import org.junit.platform.gradle.plugin.JUnitPlatformExtension

group = "com.github.azihsoyn.ktformat"
version = "0.1.0"

buildscript {
    var kotlin_version: String by extra
    kotlin_version = "1.2.21"

    repositories {
        mavenCentral()
        jcenter()
    }

    dependencies {
        classpath(kotlinModule("gradle-plugin", kotlin_version))
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.0")
        classpath("com.novoda:bintray-release:0.8.0")
    }

}

apply {
    plugin("kotlin")
    plugin("org.junit.platform.gradle.plugin")
    plugin("com.novoda.bintray-release")
}

configure<JUnitPlatformExtension> {
    filters {
        engines {
            include("spek")
        }
    }
}

configure<PublishExtension> {
    groupId = "com.github.azihsoyn.ktformat"
    artifactId = "ktformat"
    desc = "kotlin named string format"
    setLicences("MIT")
    publishVersion = "0.1.0"
    uploadName = "ktformat"
    website = "https://github.com/azihsoyn/ktformat"
}

val kotlin_version: String by extra

repositories {
    jcenter()
    mavenCentral()
    maven{setUrl("http://dl.bintray.com/jetbrains/spek")}
}

dependencies {
    compile(kotlinModule("stdlib-jdk8", kotlin_version))
    testImplementation("org.amshove.kluent:kluent:1.35")
    testCompile("org.jetbrains.spek:spek-api:1.1.5")
    testRuntime("org.jetbrains.spek:spek-junit-platform-engine:1.1.5")
    testRuntime("org.junit.platform:junit-platform-runner:1.0.0")
}

// extension for configuration
fun JUnitPlatformExtension.filters(setup: FiltersExtension.() -> Unit) {
    when (this) {
        is ExtensionAware -> extensions.getByType(FiltersExtension::class.java).setup()
        else -> throw Exception("${this::class} must be an instance of ExtensionAware")
    }
}
fun FiltersExtension.engines(setup: EnginesExtension.() -> Unit) {
    when (this) {
        is ExtensionAware -> extensions.getByType(EnginesExtension::class.java).setup()
        else -> throw Exception("${this::class} must be an instance of ExtensionAware")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

