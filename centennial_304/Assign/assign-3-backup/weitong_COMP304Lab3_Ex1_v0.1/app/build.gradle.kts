<<<<<<< HEAD
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.weitong_comp304lab3_ex1"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.weitong_comp304lab3_ex1"
=======
  plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
      kotlin("plugin.serialization") version "2.1.10"
}

android {
    namespace = "com.max.weitong_comp304lab3_ex1"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.max.weitong_comp304lab3_ex1"
>>>>>>> f5a767b68cdc95727122243f10e71747c2e81105
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
<<<<<<< HEAD
=======
    // Koin 核心库
    implementation("io.insert-koin:koin-core:3.5.0")
    // Koin Android 依赖注入（包含 ViewModel 支持）
    implementation("io.insert-koin:koin-android:3.5.0")
    // Jetpack Compose 集成（如果使用 Compose）
    implementation("io.insert-koin:koin-androidx-compose:3.5.0")
    // Retrofit 核心库
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Gson 转换器，用于将 JSON 转换为 Kotlin 对象
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp，用于网络请求
    implementation("com.squareup.okhttp3:okhttp:4.9.3")

    // OkHttp 日志拦截器，用于调试网络请求
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    implementation("io.coil-kt.coil3:coil-compose:3.1.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.1.0")
    implementation(libs.compose.navigation)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    implementation(libs.androidx.contentpager)
>>>>>>> f5a767b68cdc95727122243f10e71747c2e81105
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}