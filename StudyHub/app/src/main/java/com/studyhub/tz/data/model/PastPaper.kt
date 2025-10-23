package com.studyhub.tz.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * PastPaper model representing NECTA past examination papers
 */
@Parcelize
@Entity(tableName = "past_papers")
data class PastPaper(
    @PrimaryKey
    val id: String = "",
    val subjectId: String = "",
    val title: String = "",
    val year: Int = 2024,
    val examType: ExamType = ExamType.NECTA,
    val level: EducationLevel = EducationLevel.O_LEVEL,
    val paperNumber: Int = 1,
    val fileUrl: String = "",
    val solutionsUrl: String = "",
    val hasSolutions: Boolean = false,
    val questions: List<PaperQuestion> = emptyList(),
    val topics: List<String> = emptyList(),
    val duration: Int = 180, // in minutes
    val totalMarks: Int = 100,
    val isPremium: Boolean = false,
    val downloadCount: Int = 0,
    val isBookmarked: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) : Parcelable

@Parcelize
data class PaperQuestion(
    val questionNumber: String = "",
    val questionText: String = "",
    val marks: Int = 0,
    val solution: String = "",
    val explanation: String = "",
    val imageUrl: String = ""
) : Parcelable

enum class ExamType {
    NECTA,          // National Examinations Council of Tanzania
    MOCK,           // Mock examination
    SCHOOL_EXAM     // School-level exam
}
