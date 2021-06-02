import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "me.lucyy"
version = "1.0-SNAPSHOT"
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    mavenLocal() // TODO remove
}

dependencies {
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
    implementation("me.lucyy:squirtgun-api:2.0.0-pre3-SNAPSHOT") // TODO migrate to proper version
    implementation("me.lucyy:squirtgun-commands:2.0.0-pre3-SNAPSHOT")
    implementation("me.lucyy:squirtgun-bukkit:2.0.0-pre3-SNAPSHOT")
}

tasks {
    withType<Jar> {
        archiveClassifier.set("nodeps")
    }

    withType<ProcessResources> {
        filter<org.apache.tools.ant.filters.ReplaceTokens>("tokens" to mapOf("version" to project.version.toString()))
    }

    val shadowTask = named<ShadowJar>("shadowJar") {
        archiveClassifier.set("")

        relocate("me.lucyy.squirtgun", "me.lucyy.comfyelections.deps.squirtgun")
    }

    named("build") {
        dependsOn(shadowTask)
    }
}