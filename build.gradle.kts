plugins {
    kotlin("jvm") version "2.0.20"
}

group = "com.project"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val http4kVersion: String by project
val junitBomVersion: String by project
val pesticideVersion: String by project
val striktVersion: String by project

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    implementation("org.http4k:http4k-core:${http4kVersion}")
    implementation("org.http4k:http4k-server-jetty:${http4kVersion}")

    testImplementation(platform("org.junit:junit-bom:${junitBomVersion}"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("org.http4k:http4k-client-jetty:${http4kVersion}")
    testImplementation("com.ubertob.pesticide:pesticide-core:${pesticideVersion}")
    testImplementation("io.strikt:strikt-core:${striktVersion}")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }

    //if true show println in test console
    testLogging.showStandardStreams = false

    // start tests every time, even when code not changed
    outputs.upToDateWhen { false }
}
kotlin {
    jvmToolchain(21)
}