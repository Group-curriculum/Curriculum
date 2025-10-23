package com.studyhub.tz.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Quiz model representing quizzes and mock tests
 */
@Parcelize
@Entity(tableName = "quizzes")
data class Quiz(
    @PrimaryKey
    val id: String = "",
    val subjectId: String = "",
    val title: String = "",
    val titleSwahili: String = "",
    val description: String = "",
    val descriptionSwahili: String = "",
    val questions: List<Question> = emptyList(),
    val totalQuestions: Int = 0,
    val duration: Int = 30, // in minutes
    val passingScore: Int = 50, // percentage
    val difficulty: Difficulty = Difficulty.MEDIUM,
    val quizType: QuizType = QuizType.PRACTICE,
    val topics: List<String> = emptyList(),
    val isPremium: Boolean = false,
    val attemptCount: Int = 0,
    val averageScore: Float = 0f,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) : Parcelable

@Parcelize
data class Question(
    val id: String = "",
    val questionText: String = "",
    val questionTextSwahili: String = "",
    val type: QuestionType = QuestionType.MULTIPLE_CHOICE,
    val options: List<String> = emptyList(),
    val optionsSwahili: List<String> = emptyList(),
    val correctAnswer: String = "",
    val explanation: String = "",
    val explanationSwahili: String = "",
    val imageUrl: String = "",
    val points: Int = 1,
    val difficulty: Difficulty = Difficulty.MEDIUM
) : Parcelable

enum class QuizType {
    PRACTICE,      // Practice quiz
    MOCK_EXAM,     // Mock examination
    TOPIC_TEST,    // Test on specific topic
    QUICK_QUIZ     // Quick 5-10 questions quiz
}

enum class QuestionType {
    MULTIPLE_CHOICE,  // Multiple choice with 4 options
    TRUE_FALSE,       // True or False
    FILL_BLANK,       // Fill in the blank
    SHORT_ANSWER      // Short answer (optional)
}
