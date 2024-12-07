plugins {
    kotlin("jvm") version "1.9.21"
}

group = "net.unverdruss"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useTestNG()
}
kotlin {
    jvmToolchain(21)
}