import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.3.11"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.allopen") version kotlinVersion // Open classes for custom annotations
    kotlin("plugin.spring") version kotlinVersion // Open classes for Spring annotations
    kotlin("plugin.jpa") version kotlinVersion // No-args constructor for JPA classes

    id("org.springframework.boot") version "2.1.1.RELEASE" //Spring Boot support
    // Control the project's dependencies versions like Maven's BOM
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
}

group = "com.davfer.organizer"
version = "1.0-SNAPSHOT"
description = """ Organizer - Spring project with Kotlin """

repositories {
    mavenCentral()
    maven("https://repo.spring.io/milestone") //for spring cloud dependencies
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect")) // Kotlin reflection library (mandatory as of Spring Framework 5)
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.session:spring-session-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit") //Exclude JUnit 4
    }

    implementation(platform("org.springframework.cloud:spring-cloud-dependencies:Greenwich.RC2")) //BOM
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    // Support for serialization/deserialization of Kotlin classes and data classes
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    runtimeOnly("mysql:mysql-connector-java:8.0.13")

    implementation("io.projectreactor:reactor-bus:2.0.8.RELEASE")
    implementation("io.projectreactor:reactor-core:2.0.8.RELEASE")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
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
