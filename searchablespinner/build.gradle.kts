import java.io.FileInputStream
import java.util.Properties

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
val localProperties = Properties()
localProperties.load(FileInputStream(rootProject.file("local.properties")))
afterEvaluate {
    publishing {
        publications {
            register("release", MavenPublication::class) {
                groupId = "com.bk"
                artifactId = "searchablespinner"
                version = "1.0.0"
                artifact("$buildDir/outputs/aar/searchablespinner-release.aar")

                repositories{
                    maven{
                        name ="GitHubPackages"
                        url  = uri("https://maven.pkg.github.com/bktelematics/SearchableSpinner")
                        credentials {
                            username = "bktelematics"
                            password = localProperties.getProperty("GITHUB_TOKEN")
                        }
                    }
                }
            }
        }
    }
}