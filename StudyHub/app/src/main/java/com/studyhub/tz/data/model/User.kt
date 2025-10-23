package com.studyhub.tz.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * User model representing the Study Hub user
 */
@Parcelize
@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val uid: String = "",
    val email: String = "",
    val displayName: String = "",
    val photoUrl: String = "",
    val phoneNumber: String = "",
    val educationLevel: EducationLevel = EducationLevel.O_LEVEL,
    val formClass: Int = 1, // Form 1, 2, 3, 4, 5, or 6
    val preferredLanguage: String = "en", // "en" or "sw" (Swahili)
    val isPremium: Boolean = false,
    val premiumExpiryDate: Long = 0L,
    val studyStreak: Int = 0,
    val totalStudyTime: Long = 0L,
    val bookmarkedNotes: List<String> = emptyList(),
    val bookmarkedPapers: List<String> = emptyList(),
    val notificationsEnabled: Boolean = true,
    val studyReminders: List<StudyReminder> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val lastLoginAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) : Parcelable

@Parcelize
data class StudyReminder(
    val id: String = "",
    val title: String = "",
    val time: String = "18:00", // HH:mm format
    val days: List<Int> = listOf(1, 2, 3, 4, 5), // 1=Monday, 7=Sunday
    val isEnabled: Boolean = true
) : Parcelable
