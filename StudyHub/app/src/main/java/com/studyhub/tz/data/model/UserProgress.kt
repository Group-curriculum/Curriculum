package com.studyhub.tz.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * UserProgress model for tracking student performance and study analytics
 */
@Parcelize
@Entity(tableName = "user_progress")
data class UserProgress(
    @PrimaryKey
    val id: String = "",
    val userId: String = "",
    val subjectId: String = "",
    val notesRead: Int = 0,
    val quizzesTaken: Int = 0,
    val quizzesPassed: Int = 0,
    val averageScore: Float = 0f,
    val totalStudyTime: Long = 0L, // in milliseconds
    val lastStudyDate: Long = System.currentTimeMillis(),
    val studyStreak: Int = 0,
    val strongTopics: List<String> = emptyList(),
    val weakTopics: List<String> = emptyList(),
    val achievements: List<Achievement> = emptyList(),
    val level: Int = 1,
    val experiencePoints: Int = 0,
    val updatedAt: Long = System.currentTimeMillis()
) : Parcelable

@Parcelize
data class Achievement(
    val id: String = "",
    val title: String = "",
    val titleSwahili: String = "",
    val description: String = "",
    val iconUrl: String = "",
    val unlockedAt: Long = 0L,
    val isUnlocked: Boolean = false
) : Parcelable

/**
 * QuizAttempt model for tracking individual quiz attempts
 */
@Parcelize
@Entity(tableName = "quiz_attempts")
data class QuizAttempt(
    @PrimaryKey
    val id: String = "",
    val userId: String = "",
    val quizId: String = "",
    val subjectId: String = "",
    val score: Float = 0f,
    val correctAnswers: Int = 0,
    val totalQuestions: Int = 0,
    val timeTaken: Long = 0L, // in milliseconds
    val answers: Map<String, String> = emptyMap(), // questionId to answer
    val isPassed: Boolean = false,
    val completedAt: Long = System.currentTimeMillis()
) : Parcelable

/**
 * StudySession model for tracking study time
 */
@Parcelize
@Entity(tableName = "study_sessions")
data class StudySession(
    @PrimaryKey
    val id: String = "",
    val userId: String = "",
    val subjectId: String = "",
    val activityType: StudyActivityType = StudyActivityType.NOTE_READING,
    val contentId: String = "",
    val duration: Long = 0L, // in milliseconds
    val startTime: Long = System.currentTimeMillis(),
    val endTime: Long = System.currentTimeMillis()
) : Parcelable

enum class StudyActivityType {
    NOTE_READING,
    QUIZ_TAKING,
    PAST_PAPER_PRACTICE,
    VIDEO_WATCHING
}
