plugins {
    java
    `java-library`
    id("io.github.goooler.shadow") version "8.1.8"
}

group = "userprofileview"
version = ""

repositories {
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    mavenCentral()
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")

    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
    implementation("org.glassfish.jaxb:jaxb-runtime:4.0.5")

    implementation("org.jooq:jooq:3.6.2")
    implementation("org.xerial:sqlite-jdbc:3.50.1.0")
    implementation("com.google.code.gson:gson:2.13.1")
    implementation("com.github.ben-manes.caffeine:caffeine:3.2.0")
}

val targetJavaVersion = 21
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible()) {
        options.release.set(targetJavaVersion)
    }
}

tasks.processResources {
    filteringCharset = "UTF-8"
}

tasks.jar {
    dependsOn(configurations.runtimeClasspath)
    archiveBaseName.set("UserProfileView")

    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from(configurations.runtimeClasspath.get().files.map { if (it.isDirectory()) it else zipTree(it) })
}

tasks.shadowJar {
    archiveBaseName.set("UserProfileView")
    archiveClassifier.set("")
}