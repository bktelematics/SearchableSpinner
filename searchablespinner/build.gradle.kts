import java.util.Properties

val publishVersion = "1.0.1"

val group = "com.bk"
val artifact = "searchablespinner"
val localProperties = Properties()

plugins {
    id ("com.android.library")
    id ("maven-publish")
}

android {
    namespace = "com.bk.searchablespinner"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.11.0")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

}

// Configure the publishing task to publish the artifact
publishing {
    publications {
        // Configure a Maven publication for your library
        register<MavenPublication>("release") {
            // Define the coordinates for your library
            groupId = group
            artifactId = artifact
            version = publishVersion
            afterEvaluate{
                from(components["release"])
            }
        }
    }

    // Configure the repository to publish to (e.g., GitHub Packages)
    repositories {
        maven {
            name = "GitHubPackages"
            url  = uri("https://maven.pkg.github.com/bktelematics/SearchableSpinner")
            credentials {
                username = localProperties.getProperty("gpk.username")?: ""
                password =localProperties.getProperty("gpk.token")?: ""
            }
        }
    }
}