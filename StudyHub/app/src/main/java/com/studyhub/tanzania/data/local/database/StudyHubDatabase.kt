package com.studyhub.tanzania.data.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.studyhub.tanzania.data.local.dao.*
import com.studyhub.tanzania.data.models.*

/**
 * Room database for Study Hub app
 */
@Database(
    entities = [
        User::class,
        Subject::class,
        Note::class,
        Quiz::class,
        PastPaper::class,
        Progress::class,
        QuizAttempt::class,
        PastPaperAttempt::class,
        StudySession::class,
        Achievement::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class StudyHubDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
    abstract fun subjectDao(): SubjectDao
    abstract fun noteDao(): NoteDao
    abstract fun quizDao(): QuizDao
    abstract fun pastPaperDao(): PastPaperDao
    abstract fun progressDao(): ProgressDao
    abstract fun quizAttemptDao(): QuizAttemptDao
    abstract fun pastPaperAttemptDao(): PastPaperAttemptDao
    abstract fun studySessionDao(): StudySessionDao
    abstract fun achievementDao(): AchievementDao
    
    companion object {
        @Volatile
        private var INSTANCE: StudyHubDatabase? = null
        
        fun getDatabase(context: Context): StudyHubDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudyHubDatabase::class.java,
                    "studyhub_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}