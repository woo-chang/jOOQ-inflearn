plugins {
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
    id("nu.studer.jooq") version "9.0"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    // 스프링부트 3.3 버전부터 jOOQ 3.19 버전이 디폴트이므로 강의와 달리 그룹 재설정 제거
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    runtimeOnly("com.mysql:mysql-connector-j")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // nu.studer.jooq 플러그인 안에 필요한 의존성을 추가로 넣어주는 작업
    jooqGenerator(project(":jooq-custom"))
    jooqGenerator("org.jooq:jooq")
    jooqGenerator("org.jooq:jooq-meta")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val dbUser = System.getProperty("db-user") ?: "root"
val dbPasswd = System.getProperty("db-passwd") ?: "local"

jooq {
    configurations {
        create("sakilaDB") {
            jooqConfiguration.apply {
                // DB 접속정보
                jdbc.apply {
                    driver = "com.mysql.cj.jdbc.Driver"
                    url = "jdbc:mysql://localhost:3306"
                    user = dbUser
                    password = dbPasswd
                }

                // DSL 로드 방법 정의
                generator.apply {
                    // Java -> org.jooq.codegen.DefaultGenerator
                    name = "org.jooq.codegen.KotlinGenerator"
                    // MySQL DB 읽는다는 것을 명시
                    database.apply {
                        name = "org.jooq.meta.mysql.MySQLDatabase"
                        // 어떤 스키마를 읽을지 명시
                        inputSchema = "sakila"
                    }
                    // DSL 중 어떤 것을 생성할지에 대한 옵션
                    generate.apply {
                        isDaos = true
                        isRecords = true
                        isFluentSetters = true
                        isJavaTimeTypes = true
                        isDeprecated = false
                    }
                    // DSL 어디에 생성될지 명시
                    target.apply {
                        directory = "src/generated"
                    }
                    // 직접 생성한 CUSTOM generator
                    strategy.name = "jooq.custom.generator.JPrefixGeneratorStrategy"
                }
            }
        }
    }
}

sourceSets {
    main {
        kotlin {
            // include DSL source
            srcDirs("src/main/kotlin", "src/generated")
        }
    }
}
