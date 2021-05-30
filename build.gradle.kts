import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    java
}

group = "me.lucyy"
version = "1.0-SNAPSHOT"
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
    implementation("me.lucyy:squirtgun-api:2.0.0-pre2")
    implementation("me.lucyy:squirtgun-commands:2.0.0-pre2")
}