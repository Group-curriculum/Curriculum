package com.studyhub.tz

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.work.Configuration
import androidx.work.WorkManager
import com.google.firebase.FirebaseApp

/**
 * Main Application class for Study Hub
 */
class StudyHubApplication : Application() {

    companion object {
        const val CHANNEL_REMINDERS = "study_reminders"
        const val CHANNEL_ACHIEVEMENTS = "achievements"
    }

    override fun onCreate() {
        super.onCreate()
        
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        
        // Create notification channels
        createNotificationChannels()
        
        // Initialize WorkManager with custom configuration
        initializeWorkManager()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NotificationManager::class.java)
            
            // Study Reminders Channel
            val remindersChannel = NotificationChannel(
                CHANNEL_REMINDERS,
                "Study Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for study reminders and upcoming exams"
            }
            
            // Achievements Channel
            val achievementsChannel = NotificationChannel(
                CHANNEL_ACHIEVEMENTS,
                "Achievements",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Notifications for unlocked achievements and milestones"
            }
            
            notificationManager?.createNotificationChannel(remindersChannel)
            notificationManager?.createNotificationChannel(achievementsChannel)
        }
    }

    private fun initializeWorkManager() {
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
        
        WorkManager.initialize(this, config)
    }
}
