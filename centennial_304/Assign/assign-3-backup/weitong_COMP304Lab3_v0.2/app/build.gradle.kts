plugins {
    alias(libs.plugins.android.application)
    //alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.android)
    //id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("kotlin-kapt")
}

android {
    namespace = "com.max.weitong_comp304lab3"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.max.weitong_comp304lab3"
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
    // navigation
    implementation(libs.compose.navigation)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    implementation(libs.androidx.contentpager)
    // Room 依赖
    val roomVersion = "2.6.1" //Check for latest stable version
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
//    implementation(libs.androidx.room.runtime)
//    implementation(libs.androidx.room.ktx)
//    kapt(libs.androidx.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.4.1")
}