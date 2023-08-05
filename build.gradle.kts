import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose") version "1.5.0-beta01"
}

group = "cc.yaeko"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://maven.aliyun.com/repository/public/")
    maven("https://maven.aliyun.com/repository/central/")
    maven("https://maven.aliyun.com/repository/google/")
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.windows_x64)
    implementation("org.jetbrains.compose.material3:material3-desktop:1.5.0-beta01")

}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
            packageName = "KtaiTo"
            packageVersion = "1.0.0"
        }
    }
}
