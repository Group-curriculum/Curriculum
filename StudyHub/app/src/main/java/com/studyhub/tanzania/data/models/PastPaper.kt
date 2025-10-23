package com.studyhub.tanzania.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

/**
 * PastPaper model representing NECTA past exam papers
 */
@Parcelize
data class PastPaper(
    val id: String = "",
    val title: String = "",
    val titleSwahili: String = "",
    val subjectId: String = "",
    val level: EducationLevel = EducationLevel.O_LEVEL,
    val year: Int = 0,
    val month: ExamMonth = ExamMonth.NOVEMBER,
    val paperType: PaperType = PaperType.MAIN,
    val duration: Int = 0, // in minutes
    val totalMarks: Int = 0,
    val instructions: String = "",
    val instructionsSwahili: String = "",
    val questions: List<PaperQuestion> = emptyList(),
    val markingScheme: List<MarkingScheme> = emptyList(),
    val pdfUrl: String = "",
    val pdfSize: Long = 0,
    val isDownloaded: Boolean = false,
    val isPremium: Boolean = false,
    val isOfflineAvailable: Boolean = true,
    val downloadCount: Int = 0,
    val averageRating: Float = 0f,
    val tags: List<String> = emptyList(),
    val uploadedDate: Date = Date()
) : Parcelable

@Parcelize
data class PaperQuestion(
    val id: String = "",
    val questionNumber: Int = 0,
    val questionText: String = "",
    val questionTextSwahili: String = "",
    val marks: Int = 0,
    val type: QuestionType = QuestionType.SHORT_ANSWER,
    val subQuestions: List<SubQuestion> = emptyList(),
    val imageUrl: String = "",
    val isFormula: Boolean = false,
    val formula: String = ""
) : Parcelable

@Parcelize
data class SubQuestion(
    val id: String = "",
    val part: String = "", // e.g., "a", "b", "c"
    val questionText: String = "",
    val questionTextSwahili: String = "",
    val marks: Int = 0,
    val type: QuestionType = QuestionType.SHORT_ANSWER,
    val imageUrl: String = ""
) : Parcelable

@Parcelize
data class MarkingScheme(
    val questionId: String = "",
    val points: List<MarkingPoint> = emptyList(),
    val totalMarks: Int = 0,
    val notes: String = "",
    val notesSwahili: String = ""
) : Parcelable

@Parcelize
data class MarkingPoint(
    val id: String = "",
    val description: String = "",
    val descriptionSwahili: String = "",
    val marks: Int = 0,
    val isRequired: Boolean = true
) : Parcelable

@Parcelize
data class PastPaperAttempt(
    val id: String = "",
    val userId: String = "",
    val pastPaperId: String = "",
    val startTime: Date = Date(),
    val endTime: Date? = null,
    val answers: List<PaperAnswer> = emptyList(),
    val score: Int = 0,
    val totalMarks: Int = 0,
    val percentage: Float = 0f,
    val timeSpent: Long = 0, // in seconds
    val isCompleted: Boolean = false,
    val isPassed: Boolean = false
) : Parcelable

@Parcelize
data class PaperAnswer(
    val questionId: String = "",
    val answer: String = "",
    val marks: Int = 0,
    val timeSpent: Long = 0, // in seconds
    val isCorrect: Boolean = false
) : Parcelable

enum class ExamMonth {
    MARCH,
    MAY,
    NOVEMBER
}

enum class PaperType {
    MAIN,
    SUPPLEMENTARY,
    SPECIAL
}