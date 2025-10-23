package com.studyhub.tanzania

import android.app.Application
import com.google.firebase.FirebaseApp
import com.studyhub.tanzania.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Application class for Study Hub
 */
class StudyHubApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        
        // Initialize Koin for dependency injection
        startKoin {
            androidContext(this@StudyHubApplication)
            modules(AppModule.modules)
        }
    }
}