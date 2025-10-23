package com.studyhub.tz.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Subject model representing NECTA O-Level and A-Level subjects
 */
@Parcelize
@Entity(tableName = "subjects")
data class Subject(
    @PrimaryKey
    val id: String = "",
    val name: String = "",
    val nameSwahili: String = "",
    val level: EducationLevel = EducationLevel.O_LEVEL,
    val description: String = "",
    val descriptionSwahili: String = "",
    val iconUrl: String = "",
    val color: String = "#2196F3",
    val notesCount: Int = 0,
    val quizzesCount: Int = 0,
    val pastPapersCount: Int = 0,
    val isPopular: Boolean = false,
    val order: Int = 0,
    val lastUpdated: Long = System.currentTimeMillis()
) : Parcelable

enum class EducationLevel {
    O_LEVEL,  // Ordinary Level (Form 1-4)
    A_LEVEL   // Advanced Level (Form 5-6)
}
