package com.studyhub.tanzania.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

/**
 * Progress model for tracking student performance and study progress
 */
@Parcelize
data class Progress(
    val userId: String = "",
    val subjectId: String = "",
    val level: EducationLevel = EducationLevel.O_LEVEL,
    val totalStudyTime: Long = 0, // in minutes
    val notesRead: Int = 0,
    val quizzesAttempted: Int = 0,
    val quizzesPassed: Int = 0,
    val pastPapersAttempted: Int = 0,
    val pastPapersPassed: Int = 0,
    val averageQuizScore: Float = 0f,
    val averagePastPaperScore: Float = 0f,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val lastStudyDate: Date? = null,
    val topicsCompleted: List<String> = emptyList(),
    val weakTopics: List<String> = emptyList(),
    val strongTopics: List<String> = emptyList(),
    val achievements: List<Achievement> = emptyList(),
    val weeklyProgress: List<WeeklyProgress> = emptyList(),
    val monthlyProgress: List<MonthlyProgress> = emptyList()
) : Parcelable

@Parcelize
data class WeeklyProgress(
    val weekStart: Date = Date(),
    val weekEnd: Date = Date(),
    val studyTime: Long = 0, // in minutes
    val notesRead: Int = 0,
    val quizzesAttempted: Int = 0,
    val quizzesPassed: Int = 0,
    val averageScore: Float = 0f,
    val streak: Int = 0
) : Parcelable

@Parcelize
data class MonthlyProgress(
    val month: Int = 0,
    val year: Int = 0,
    val studyTime: Long = 0, // in minutes
    val notesRead: Int = 0,
    val quizzesAttempted: Int = 0,
    val quizzesPassed: Int = 0,
    val averageScore: Float = 0f,
    val streak: Int = 0,
    val achievements: List<String> = emptyList()
) : Parcelable

@Parcelize
data class Achievement(
    val id: String = "",
    val title: String = "",
    val titleSwahili: String = "",
    val description: String = "",
    val descriptionSwahili: String = "",
    val iconRes: String = "",
    val type: AchievementType = AchievementType.STUDY_STREAK,
    val requirement: Int = 0,
    val currentProgress: Int = 0,
    val isUnlocked: Boolean = false,
    val unlockedDate: Date? = null,
    val points: Int = 0
) : Parcelable

@Parcelize
data class StudySession(
    val id: String = "",
    val userId: String = "",
    val subjectId: String = "",
    val topicId: String = "",
    val startTime: Date = Date(),
    val endTime: Date? = null,
    val duration: Long = 0, // in minutes
    val activityType: StudyActivityType = StudyActivityType.READING_NOTES,
    val contentId: String = "",
    val score: Float = 0f,
    val isCompleted: Boolean = false
) : Parcelable

enum class AchievementType {
    STUDY_STREAK,
    NOTES_READ,
    QUIZZES_PASSED,
    PAST_PAPERS_ATTEMPTED,
    PERFECT_SCORE,
    TIME_STUDIED,
    TOPICS_COMPLETED
}

enum class StudyActivityType {
    READING_NOTES,
    TAKING_QUIZ,
    ATTEMPTING_PAST_PAPER,
    WATCHING_VIDEO,
    PRACTICING_FORMULAS
}