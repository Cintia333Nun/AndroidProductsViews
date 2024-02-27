plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.testapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.testapplication"
        minSdk = 28
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

val versions = mapOf(
    //"roomVersion" to "2.6.1",
    "dagger" to "2.50",
    "hilt" to "1.2.0",
    "nav" to "2.7.7",
)
extra["versions"] = versions

dependencies {
    //val roomVersion: String by versions
    val dagger: String by versions
    val hilt: String by versions
    val nav: String by versions

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Retrofit
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation ("com.google.code.gson:gson:2.10")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // PICASSO
    implementation ("com.squareup.picasso:picasso:2.71828")

    // ROOM
    /*implementation ("androidx.room:room-runtime:$roomVersion")
    implementation ("androidx.room:room-ktx:$roomVersion")
    implementation ("androidx.room:room-paging:$roomVersion")
    ksp ("androidx.room:room-compiler:$roomVersion")*/

    // NAVIGATION
    implementation("androidx.navigation:navigation-fragment-ktx:$nav")
    implementation("androidx.navigation:navigation-ui-ktx:$nav")

    // PARCELIZE
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.10")

    // DAGGER HILT
    implementation ("com.google.dagger:hilt-android:$dagger")
    ksp ("com.google.dagger:hilt-compiler:$dagger")
    implementation ("androidx.hilt:hilt-work:$hilt")
    ksp ("androidx.hilt:hilt-compiler:$hilt")

    //CUSTOM ALERTER
    implementation ("com.github.tapadoo:alerter:7.2.4")

    //SWIPE REFRESH
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
}