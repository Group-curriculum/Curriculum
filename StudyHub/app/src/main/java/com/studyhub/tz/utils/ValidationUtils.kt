package com.studyhub.tz.utils

/**
 * Utility functions for input validation
 */
object ValidationUtils {

    /**
     * Validate email format
     */
    fun isValidEmail(email: String): Boolean {
        if (email.isBlank()) return false
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Validate password strength
     */
    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    /**
     * Get password strength level
     */
    fun getPasswordStrength(password: String): PasswordStrength {
        return when {
            password.length < 6 -> PasswordStrength.WEAK
            password.length < 8 -> PasswordStrength.MEDIUM
            password.any { it.isDigit() } && password.any { it.isUpperCase() } -> PasswordStrength.STRONG
            else -> PasswordStrength.MEDIUM
        }
    }

    /**
     * Validate phone number (Tanzanian format)
     */
    fun isValidTanzanianPhone(phone: String): Boolean {
        val cleanPhone = phone.replace(Regex("[^0-9+]"), "")
        return cleanPhone.matches(Regex("^(\\+255|0)[67]\\d{8}$"))
    }

    /**
     * Validate display name
     */
    fun isValidDisplayName(name: String): Boolean {
        return name.isNotBlank() && name.length >= 2
    }

    enum class PasswordStrength {
        WEAK, MEDIUM, STRONG
    }
}
