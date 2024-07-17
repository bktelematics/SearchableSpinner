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
    implementation("com.google.android.material:material:1.4.0") // Update Material version for compatibility
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                groupId = "com.github.bktelematics" // Replace with your GitHub username
                artifactId = "searchablespinner" // Replace with your artifact ID
                version = "1.1.3"

                artifact("$buildDir/outputs/aar/searchablespinner-release.aar")
            }
        }

        repositories {
            maven { url = uri("https://jitpack.io") }
        }

        tasks.named("publishReleasePublicationToMavenLocal") {
            dependsOn("bundleReleaseAar")
        }
    }
}
