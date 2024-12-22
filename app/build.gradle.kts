plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.dagger)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.safeargs.plugin)
}

android {
    namespace = "com.example.eterationcase"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.eterationcase"
        minSdk = 26
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //hilt
    implementation(libs.dagger.hilt.android)
    implementation(libs.dagger.hilt.navigation)
    ksp(libs.dagger.hilt.compiler)

    //room
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)

    //coroutines
    implementation(libs.coroutines)

    //serialization
    implementation(libs.kotlinx.serialization.json)

    //navigation
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    //fragment-ktx
    implementation(libs.fragment.ktx)

    //retrofit & okhttp
    implementation(libs.retrofit)
    implementation(libs.retrofit.serialization)
    implementation(libs.okhttp)

    //coil
    implementation(libs.coil)

    //transformations
    implementation(libs.lifecycle.livedata.ktx)
}