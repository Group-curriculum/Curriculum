package com.studyhub.tz.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Note model representing study notes for a subject
 */
@Parcelize
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey
    val id: String = "",
    val subjectId: String = "",
    val title: String = "",
    val titleSwahili: String = "",
    val content: String = "",
    val contentSwahili: String = "",
    val summary: String = "",
    val summarySwahili: String = "",
    val topics: List<String> = emptyList(),
    val keyPoints: List<String> = emptyList(),
    val keyPointsSwahili: List<String> = emptyList(),
    val formulas: List<Formula> = emptyList(),
    val diagrams: List<Diagram> = emptyList(),
    val isPremium: Boolean = false,
    val isBookmarked: Boolean = false,
    val readCount: Int = 0,
    val estimatedReadTime: Int = 5, // in minutes
    val difficulty: Difficulty = Difficulty.MEDIUM,
    val order: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) : Parcelable

@Parcelize
data class Formula(
    val title: String = "",
    val latex: String = "",
    val description: String = ""
) : Parcelable

@Parcelize
data class Diagram(
    val title: String = "",
    val imageUrl: String = "",
    val description: String = ""
) : Parcelable

enum class Difficulty {
    EASY,
    MEDIUM,
    HARD
}
