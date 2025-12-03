// File: build.gradle.kts (Project)
plugins {
    // Aggiorniamo il plugin Android alla 8.5.0 per supportare SDK 35
    id("com.android.application") version "8.5.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
}