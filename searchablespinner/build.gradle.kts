//import java.io.FileInputStream
//import java.util.Properties
//
//val publishVersion = "1.0.1"
//
//val group = "com.bk"
//val artifact = "searchablespinner"
//val localProperties = Properties()
//localProperties.load(FileInputStream(rootProject.file("local.properties")))
plugins {
    id("com.android.library")
    id("maven-publish")
}

android {
    namespace = "com.bk.searchablespinner"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        version = "1.0.5" // Use the version directly here
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
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.android.tools.build:gradle:3.6.0")// Use a version compatible with Java 8
    implementation("com.google.android.material:material:1.4.0") // Update Material version for compatibility
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

// Configure publication to JitPack
//publishing {
//    publications {
//        // Define a publication named "release" for JitPack
//        register<MavenPublication>("release") {
//            //from(components["release"])
//
//            // Configure the coordinates of the published artifact
//            groupId = "com.github.bktelematics" // Replace with your GitHub username
//            artifactId = "searchablespinner" // Replace with your artifact ID
//            version = "1.0.2"
//        }
//    }
//    repositories {
//        maven { url = uri("https://jitpack.io") }
//    }
//}
afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                groupId = "com.github.bktelematics" // Replace with your GitHub username
                artifactId = "searchablespinner" // Replace with your artifact ID
                version = "1.1.0"

                artifact("$buildDir/outputs/aar/searchablespinner-release.aar")
            }
        }

        repositories {
            maven { url = uri("https://jitpack.io") }
        }

        // Declare dependency on bundleReleaseAar
        tasks.named("publishReleasePublicationToMavenLocal") {
            dependsOn("bundleReleaseAar")
        }
    }
}

//plugins {
//    id("com.android.library")
//    id ("maven-publish")
//
//}
//
//android {
//    namespace = "com.bk.searchablespinner"
//    compileSdk = 34
//
//    defaultConfig {
//        minSdk = 24
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        version = "1.0.5" // Use the version directly here
//
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//}
//
//dependencies {
//    implementation("androidx.appcompat:appcompat:1.6.1")
//    implementation("com.google.android.material:material:1.11.0")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//}
//plugins {
//    id ("com.android.library")
//    id ("maven-publish")
//}
//
//android {
//    namespace = "com.bk.searchablespinner"
//    compileSdk = 34
//
//    defaultConfig {
//        minSdk = 24
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        version = publishVersion
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//
//    publishing {
//        singleVariant("release") {
//            withSourcesJar()
//            withJavadocJar()
//        }
//    }
//}
//
//dependencies {
//    implementation ("androidx.appcompat:appcompat:1.6.1")
//    implementation ("com.google.android.material:material:1.11.0")
//    testImplementation ("junit:junit:4.13.2")
//    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
//
//}
//
//// Configure the publishing task to publish the artifact
//publishing {
//    publications {
//        // Configure a Maven publication for your library
//        register<MavenPublication>("release") {
//            // Define the coordinates for your library
//            groupId = group
//            artifactId = artifact
//            version = publishVersion
//            afterEvaluate{
//                from(components["release"])
//            }
//        }
//    }
//
//    // Configure the repository to publish to (e.g., GitHub Packages)
//    repositories {
//        maven {
//            name = "GitHubPackages"
//            url  = uri("https://maven.pkg.github.com/bktelematics/SearchableSpinner")
//            credentials {
//                username = localProperties.getProperty("gpk.username")?: ""
//                password = localProperties.getProperty("gpk.token")?: ""
//            }
//        }
//    }
//}
//

