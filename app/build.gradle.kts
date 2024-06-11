plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.dud_mobile"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dud_mobile"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.google.code.gson:gson:2.8.6")


    //расширение возможностей фрагмента:
    implementation("androidx.fragment:fragment:1.6.2")
    //Glide передача  картинки и работа с ней:
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    // круглое изображение
    implementation("de.hdodenhof:circleimageview:3.1.0")
    //Возможность преобразовать объект в необходимый формат:
    implementation("androidx.multidex:multidex:2.0.1")
    //Animation
    implementation("com.airbnb.android:lottie:3.6.1")

    // Lifecycle
//    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")

    implementation("com.squareup.picasso:picasso:2.71828")

    //Glide передача  картинки и работа с ней:
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    // круглое изображение
    implementation("de.hdodenhof:circleimageview:3.1.0")
    //Возможность преобразовать объект в необходимый формат:
    implementation("androidx.multidex:multidex:2.0.1")
    //Animation
    implementation("com.airbnb.android:lottie:3.6.1")
    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    //JsonConverter
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    // Okhttp
    implementation ("com.squareup.okhttp3:okhttp:4.9.1")

    implementation("com.squareup.picasso:picasso:2.71828")

    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

}