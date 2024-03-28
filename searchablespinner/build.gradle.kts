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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
android {
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

afterEvaluate {
    publishing {
        publications {
            register("release", MavenPublication::class) {
                groupId = "com.bk"
                artifactId = "searchablespinner"
                version = "1.0.0"

                from(components["release"])
                artifact("$buildDir/outputs/aar/searchablespinner-release.aar")

                pom {
                    withXml {
                        val dependenciesNode = asNode().appendNode("dependencies")

                        project.configurations["implementation"].allDependencies.forEach { dep ->
                            if (dep.group != null || dep.name != null || dep.version != null || dep.name == "unspecified") return@forEach

                            val dependencyNode = dependenciesNode.appendNode("dependency")
                            dependencyNode.appendNode("groupId", dep.group)
                            dependencyNode.appendNode("artifactId", dep.name)
                            dependencyNode.appendNode("version", dep.version)
                        }
                    }
                }
            }
        }
    }
}

