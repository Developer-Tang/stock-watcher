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
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
    testCompileOnly("org.projectlombok:lombok:1.18.38")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.38")

    // Hutool工具（排除重复或无用的传递依赖）
    implementation("cn.hutool:hutool-json:5.8.38") {
        exclude("org.apache.commons", "commons-lang3")
        exclude("org.slf4j", "slf4j-api")
        exclude("com.alibaba", "fastjson")
    }

    implementation("cn.hutool:hutool-http:5.8.38") {
        exclude("com.squareup.okhttp3", "okhttp")
        exclude("org.apache.httpcomponents.client5", "httpclient5")
    }

    // Quartz 定时任务框架（排除默认的连接池和日志依赖）
    implementation("org.quartz-scheduler:quartz:2.3.2") {
        exclude("com.mchange", "c3p0")
        exclude("com.zaxxer", "HikariCP-java7")
        exclude("com.mchange", "mchange-commons-java")
        exclude("org.slf4j", "slf4j-api")
        exclude("org.slf4j", "slf4j-log4j12")
        exclude("log4j", "log4j")
    }

    // HanLP 核心库（排除冗余的NLP模型和依赖）
    implementation("com.hankcs:hanlp:portable-1.8.6") {
        exclude("org.apache.lucene", "lucene-core")
        exclude("org.apache.lucene", "lucene-analyzers-common")
        exclude("junit", "junit")
        exclude("com.google.code.gson", "gson")
    }

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