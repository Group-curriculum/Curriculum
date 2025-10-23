package com.studyhub.tz.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.studyhub.tz.data.local.dao.*
import com.studyhub.tz.data.model.*

/**
 * Room database for offline caching and local storage
 */
@Database(
    entities = [
        User::class,
        Subject::class,
        Note::class,
        Quiz::class,
        PastPaper::class,
        UserProgress::class,
        QuizAttempt::class,
        StudySession::class
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
    abstract fun userProgressDao(): UserProgressDao
    abstract fun quizAttemptDao(): QuizAttemptDao
    abstract fun studySessionDao(): StudySessionDao

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
