package com.studyhub.tz.utils

/**
 * App-wide constants
 */
object Constants {
    
    // SharedPreferences
    const val PREFS_NAME = "StudyHubPrefs"
    const val PREF_LANGUAGE = "pref_language"
    const val PREF_EDUCATION_LEVEL = "pref_education_level"
    const val PREF_NOTIFICATIONS_ENABLED = "pref_notifications_enabled"
    
    // Languages
    const val LANG_ENGLISH = "en"
    const val LANG_SWAHILI = "sw"
    
    // Intent extras
    const val EXTRA_SUBJECT_ID = "extra_subject_id"
    const val EXTRA_NOTE_ID = "extra_note_id"
    const val EXTRA_QUIZ_ID = "extra_quiz_id"
    const val EXTRA_PAPER_ID = "extra_paper_id"
    
    // Notification channels
    const val CHANNEL_REMINDERS = "study_reminders"
    const val CHANNEL_ACHIEVEMENTS = "achievements"
    
    // Cache settings
    const val CACHE_EXPIRY_TIME = 24 * 60 * 60 * 1000L // 24 hours in milliseconds
    
    // Quiz settings
    const val QUIZ_DEFAULT_TIME = 30 // minutes
    const val QUIZ_PASSING_SCORE = 50 // percentage
    
    // Study session
    const val MIN_STUDY_SESSION_DURATION = 60 * 1000L // 1 minute in milliseconds
    
    // Pagination
    const val PAGE_SIZE = 20
    const val INITIAL_LOAD_SIZE = 40
}
