package com.studyhub.tanzania.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

/**
 * User model representing a student in the Study Hub app
 */
@Parcelize
data class User(
    val id: String = "",
    val email: String = "",
    val displayName: String = "",
    val photoUrl: String = "",
    val level: EducationLevel = EducationLevel.O_LEVEL,
    val subjects: List<String> = emptyList(),
    val studyStreak: Int = 0,
    val totalStudyTime: Long = 0, // in minutes
    val joinDate: Date = Date(),
    val lastActiveDate: Date = Date(),
    val isPremium: Boolean = false,
    val preferences: UserPreferences = UserPreferences(),
    val achievements: List<String> = emptyList()
) : Parcelable

@Parcelize
data class UserPreferences(
    val language: String = "en", // "en" or "sw"
    val notificationsEnabled: Boolean = true,
    val studyRemindersEnabled: Boolean = true,
    val darkModeEnabled: Boolean = false,
    val fontSize: FontSize = FontSize.MEDIUM,
    val offlineModeEnabled: Boolean = true
) : Parcelable

enum class EducationLevel {
    O_LEVEL,
    A_LEVEL
}

enum class FontSize {
    SMALL,
    MEDIUM,
    LARGE
}