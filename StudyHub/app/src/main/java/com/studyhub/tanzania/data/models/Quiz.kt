package com.studyhub.tanzania.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

/**
 * Quiz model representing quizzes and mock tests
 */
@Parcelize
data class Quiz(
    val id: String = "",
    val title: String = "",
    val titleSwahili: String = "",
    val description: String = "",
    val descriptionSwahili: String = "",
    val subjectId: String = "",
    val topicId: String = "",
    val level: EducationLevel = EducationLevel.O_LEVEL,
    val type: QuizType = QuizType.MULTIPLE_CHOICE,
    val difficulty: DifficultyLevel = DifficultyLevel.BEGINNER,
    val timeLimit: Int = 0, // in minutes, 0 means no time limit
    val totalQuestions: Int = 0,
    val totalMarks: Int = 0,
    val passingMarks: Int = 0,
    val questions: List<Question> = emptyList(),
    val isPremium: Boolean = false,
    val isOfflineAvailable: Boolean = true,
    val createdDate: Date = Date(),
    val lastModified: Date = Date(),
    val isBookmarked: Boolean = false,
    val attemptCount: Int = 0,
    val averageScore: Float = 0f,
    val tags: List<String> = emptyList()
) : Parcelable

@Parcelize
data class Question(
    val id: String = "",
    val questionText: String = "",
    val questionTextSwahili: String = "",
    val type: QuestionType = QuestionType.MULTIPLE_CHOICE,
    val options: List<Option> = emptyList(),
    val correctAnswer: String = "",
    val explanation: String = "",
    val explanationSwahili: String = "",
    val marks: Int = 1,
    val difficulty: DifficultyLevel = DifficultyLevel.BEGINNER,
    val imageUrl: String = "",
    val isFormula: Boolean = false,
    val formula: String = "",
    val hints: List<String> = emptyList(),
    val hintsSwahili: List<String> = emptyList()
) : Parcelable

@Parcelize
data class Option(
    val id: String = "",
    val text: String = "",
    val textSwahili: String = "",
    val isCorrect: Boolean = false,
    val explanation: String = "",
    val explanationSwahili: String = ""
) : Parcelable

@Parcelize
data class QuizAttempt(
    val id: String = "",
    val userId: String = "",
    val quizId: String = "",
    val startTime: Date = Date(),
    val endTime: Date? = null,
    val answers: List<Answer> = emptyList(),
    val score: Int = 0,
    val totalMarks: Int = 0,
    val percentage: Float = 0f,
    val timeSpent: Long = 0, // in seconds
    val isCompleted: Boolean = false,
    val isPassed: Boolean = false
) : Parcelable

@Parcelize
data class Answer(
    val questionId: String = "",
    val selectedAnswer: String = "",
    val isCorrect: Boolean = false,
    val timeSpent: Long = 0, // in seconds
    val hintsUsed: Int = 0
) : Parcelable

enum class QuizType {
    MULTIPLE_CHOICE,
    TRUE_FALSE,
    FILL_IN_BLANK,
    MATCHING,
    SHORT_ANSWER,
    MOCK_EXAM,
    PRACTICE_QUIZ
}

enum class QuestionType {
    MULTIPLE_CHOICE,
    TRUE_FALSE,
    FILL_IN_BLANK,
    MATCHING,
    SHORT_ANSWER,
    NUMERICAL
}