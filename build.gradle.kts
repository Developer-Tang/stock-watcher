plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.7.1"
}

group = "cn.tangshh"
version = "1.0"

repositories {
    maven {
        url = uri("https://maven.aliyun.com/repository/google")
    }
    maven {
        url = uri("https://maven.aliyun.com/repository/central")
    }
    maven {
        url = uri("https://maven.aliyun.com/repository/public")
    }
    maven {
        url = uri("https://maven.aliyun.com/repository/jcenter")
    }
    maven {
        url = uri("https://maven.aliyun.com/repository/gradle-plugin")
    }
    google()
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Configure IntelliJ Platform Gradle Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
    intellijPlatform {
        create("IC", "2025.1")
        testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)
    }
    // lombok
    implementation("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
    testImplementation("org.projectlombok:lombok:1.18.38")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.38")
    // Hutool工具
    implementation("cn.hutool:hutool-core:5.8.38")
    implementation("cn.hutool:hutool-json:5.8.38")
    implementation("cn.hutool:hutool-http:5.8.38")
    // quartz 定时任务框架
    implementation("org.quartz-scheduler:quartz:2.3.2")
    // HanLP 核心库（含拼音功能）
    implementation("com.hankcs:hanlp:portable-1.8.6")
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "251"
            untilBuild = "252.*"
        }

        changeNotes = """
            Initial version
        """.trimIndent()
    }
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }
}