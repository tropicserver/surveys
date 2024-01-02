import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    `maven-publish`
    kotlin("jvm") version "1.9.10"
    kotlin("kapt") version "1.9.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}


group = "gg.tropic.surveys"
version = "1.0.0"

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.dmulloy2.net/repository/public/")
    }

    configureScalaRepository()
}



dependencies {
    compileOnly(kotlin("stdlib"))

    kapt("gg.scala.commons:bukkit:3.4.2")
    compileOnly("gg.scala.commons:bukkit:3.4.2")

    compileOnly("gg.scala.store:spigot:0.1.8")
    compileOnly("gg.scala.basics.plugin:scala-basics-plugin:1.0.7")

    compileOnly("gg.scala.spigot:server:1.1.3")

    compileOnly("gg.scala.lemon:bukkit:1.6.7")
    compileOnly("gg.scala.cloudsync:spigot:1.0.1")
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
    options.fork()
    options.encoding = "UTF-8"
}

kotlin {
    jvmToolchain(jdkVersion = 17)
}

tasks {
    withType<ShadowJar> {
        archiveClassifier.set("")
        exclude(
            "**/*.kotlin_metadata",
            "**/*.kotlin_builtins"
        )

        archiveFileName.set(
            "surveys-plugin.jar"
        )
    }

    withType<JavaCompile> {
        options.compilerArgs.add("-parameters")
        options.fork()
        options.encoding = "UTF-8"
    }

    withType<KotlinCompile> {
        kotlinOptions.javaParameters = true
        kotlinOptions.jvmTarget = "17"
    }

    getByName("build") {
        dependsOn(
            "shadowJar",
            "publishMavenJavaPublicationToScalaRepository"
        )
    }
}


publishing {
    repositories.configureScalaRepository(dev = false)

    publications {
        register(
            name = "mavenJava",
            type = MavenPublication::class,
            configurationAction = shadow::component
        )
    }
}


fun RepositoryHandler.configureScalaRepository(dev: Boolean = false)
{
    maven("${property("artifactory_contextUrl")}/gradle-${if (dev) "dev" else "release"}") {
        name = "scala"
        credentials {
            username = property("artifactory_user").toString()
            password = property("artifactory_password").toString()
        }
    }
}