package com.studyhub.tanzania.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

/**
 * Note model representing study notes for subjects
 */
@Parcelize
data class Note(
    val id: String = "",
    val title: String = "",
    val titleSwahili: String = "",
    val content: String = "",
    val contentSwahili: String = "",
    val subjectId: String = "",
    val topicId: String = "",
    val level: EducationLevel = EducationLevel.O_LEVEL,
    val author: String = "",
    val authorSwahili: String = "",
    val createdDate: Date = Date(),
    val lastModified: Date = Date(),
    val tags: List<String> = emptyList(),
    val difficulty: DifficultyLevel = DifficultyLevel.BEGINNER,
    val estimatedReadTime: Int = 0, // in minutes
    val isPremium: Boolean = false,
    val isOfflineAvailable: Boolean = true,
    val attachments: List<Attachment> = emptyList(),
    val formulas: List<Formula> = emptyList(),
    val diagrams: List<Diagram> = emptyList(),
    val keyPoints: List<String> = emptyList(),
    val keyPointsSwahili: List<String> = emptyList(),
    val isBookmarked: Boolean = false,
    val viewCount: Int = 0,
    val rating: Float = 0f
) : Parcelable

@Parcelize
data class Attachment(
    val id: String = "",
    val name: String = "",
    val type: AttachmentType = AttachmentType.IMAGE,
    val url: String = "",
    val size: Long = 0,
    val isDownloaded: Boolean = false
) : Parcelable

@Parcelize
data class Formula(
    val id: String = "",
    val expression: String = "",
    val description: String = "",
    val descriptionSwahili: String = "",
    val variables: List<Variable> = emptyList(),
    val examples: List<String> = emptyList()
) : Parcelable

@Parcelize
data class Variable(
    val symbol: String = "",
    val name: String = "",
    val nameSwahili: String = "",
    val unit: String = "",
    val description: String = ""
) : Parcelable

@Parcelize
data class Diagram(
    val id: String = "",
    val title: String = "",
    val titleSwahili: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val descriptionSwahili: String = "",
    val isInteractive: Boolean = false
) : Parcelable

enum class AttachmentType {
    IMAGE,
    PDF,
    AUDIO,
    VIDEO,
    DOCUMENT
}

enum class DifficultyLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED
}