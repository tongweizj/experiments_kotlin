plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("com.google.devtools.ksp")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}
android {
    namespace = "com.packt.chapterseven"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.packt.chapterseven"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android.txt"),
                    "proguard-rules.pro"
                )
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }

    packaging  {
        resources {
            pickFirsts.add("META-INF/AL2.0")
            pickFirsts.add("META-INF/LGPL2.1")
        }
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}
secrets {
    // To add your Maps API key to this project:
    // 1. If the secrets.properties file does not exist, create it in the same folder as the local.properties file.
    // 2. Add this line, where YOUR_API_KEY is your API key:
    //        MAPS_API_KEY=YOUR_API_KEY
    propertiesFileName = "secrets.properties"

    // A properties file containing default secret values. This file can be
    // checked in version control.
    defaultPropertiesFileName = "local.defaults.properties"
}
dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.networking)
    implementation(libs.compose.navigation)
    implementation(libs.compose.window.size)
    implementation(libs.androidx.window)
    // Gson 转换器，用于将 JSON 转换为 Kotlin 对象
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //ROOM
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    //workmanager
    implementation(libs.work.runtime)
    implementation(libs.workmanager.koin)
    //Google Maps
    implementation ("com.google.android.gms:play-services-maps:18.0.2")
    implementation ("com.google.maps.android:maps-compose:2.0.0")
    implementation("com.google.android.libraries.places:places:4.1.0")

    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.junitExt)
    androidTestImplementation(libs.test.espresso)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.test.compose.junit4)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.manifest)
}
