import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.3.11"

    kotlin("jvm") version kotlinVersion
    //*** Makes classes annotated with Spring annotations open ***/
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    /*** ¿¿¿ Spring dependencies ??? ***/
    id("org.springframework.boot") version "2.1.1.RELEASE"
    /*** Control the project's dependencies versions ***/
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
}

group = "com.davfer"
version = "1.0-SNAPSHOT"
description = """ Organizer - Spring project with Kotlin """

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    /*** Kotlin reflection library (mandatory as of Spring Framework 5) ***/
    implementation(kotlin("reflect"))
    /*** Adds support for serialization/deserialization of Kotlin classes and data classes ***/
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit") //Exclude JUnit 4
    }
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    runtimeOnly("mysql:mysql-connector-java:8.0.13")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjsr305=strict")//Allow Kotlin null-safety with Spring
    }
}

tasks.withType<Wrapper> {
    gradleVersion = "5.0"
}

tasks.withType<Test> {
    useJUnitPlatform() //Enable JUnit 5 support
}
