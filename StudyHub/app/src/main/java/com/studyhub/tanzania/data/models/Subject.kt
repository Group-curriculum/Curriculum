package com.studyhub.tanzania.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Subject model representing NECTA O-Level and A-Level subjects
 */
@Parcelize
data class Subject(
    val id: String = "",
    val name: String = "",
    val nameSwahili: String = "",
    val code: String = "",
    val level: EducationLevel = EducationLevel.O_LEVEL,
    val description: String = "",
    val descriptionSwahili: String = "",
    val iconRes: String = "",
    val color: String = "#2196F3",
    val topics: List<Topic> = emptyList(),
    val isCore: Boolean = true,
    val isAvailable: Boolean = true,
    val order: Int = 0
) : Parcelable

@Parcelize
data class Topic(
    val id: String = "",
    val name: String = "",
    val nameSwahili: String = "",
    val description: String = "",
    val descriptionSwahili: String = "",
    val order: Int = 0,
    val notes: List<String> = emptyList(), // Note IDs
    val quizzes: List<String> = emptyList(), // Quiz IDs
    val isCompleted: Boolean = false
) : Parcelable

// NECTA O-Level Subjects
object OLevelSubjects {
    const val MATHEMATICS = "mathematics"
    const val ENGLISH = "english"
    const val KISWAHILI = "kiswahili"
    const val PHYSICS = "physics"
    const val CHEMISTRY = "chemistry"
    const val BIOLOGY = "biology"
    const val HISTORY = "history"
    const val GEOGRAPHY = "geography"
    const val CIVICS = "civics"
    const val COMMERCE = "commerce"
    const val BOOK_KEEPING = "book_keeping"
    const val BASIC_MATHEMATICS = "basic_mathematics"
    const val ADDITIONAL_MATHEMATICS = "additional_mathematics"
}

// NECTA A-Level Subjects
object ALevelSubjects {
    const val MATHEMATICS = "mathematics_alevel"
    const val PHYSICS = "physics_alevel"
    const val CHEMISTRY = "chemistry_alevel"
    const val BIOLOGY = "biology_alevel"
    const val HISTORY = "history_alevel"
    const val GEOGRAPHY = "geography_alevel"
    const val ECONOMICS = "economics"
    const val COMMERCE = "commerce_alevel"
    const val ACCOUNTANCY = "accountancy"
    const val GENERAL_STUDIES = "general_studies"
    const val KISWAHILI = "kiswahili_alevel"
    const val ENGLISH = "english_alevel"
}