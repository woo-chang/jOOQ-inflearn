plugins {
    kotlin("jvm")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // 버전을 명시하지 않으면 에러 발생
    implementation("org.jooq:jooq-codegen:3.19.8")
    runtimeOnly("com.mysql:mysql-connector-j:8.3.0")
}

kotlin {
    jvmToolchain(17)
}
