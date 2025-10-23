package com.studyhub.tanzania.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.studyhub.tanzania.data.local.dao.AchievementDao
import com.studyhub.tanzania.data.models.Achievement
import com.studyhub.tanzania.data.models.AchievementType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing achievement data
 */
@Singleton
class AchievementRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val achievementDao: AchievementDao
) {
    
    /**
     * Get all achievements
     */
    fun getAllAchievements(): Flow<List<Achievement>> = 
        achievementDao.getAllAchievements()
    
    /**
     * Get achievements by type
     */
    fun getAchievementsByType(type: AchievementType): Flow<List<Achievement>> = 
        achievementDao.getAchievementsByType(type)
    
    /**
     * Get unlocked achievements
     */
    fun getUnlockedAchievements(): Flow<List<Achievement>> = 
        achievementDao.getUnlockedAchievements()
    
    /**
     * Get locked achievements
     */
    fun getLockedAchievements(): Flow<List<Achievement>> = 
        achievementDao.getLockedAchievements()
    
    /**
     * Get achievement by ID
     */
    suspend fun getAchievementById(achievementId: String): Achievement? = 
        achievementDao.getAchievementById(achievementId)
    
    /**
     * Unlock an achievement
     */
    suspend fun unlockAchievement(achievementId: String): Result<Unit> {
        return try {
            val achievement = achievementDao.getAchievementById(achievementId)
            if (achievement != null && !achievement.isUnlocked) {
                val updatedAchievement = achievement.copy(
                    isUnlocked = true,
                    unlockedDate = Date()
                )
                achievementDao.updateAchievement(updatedAchievement)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Achievement not found or already unlocked"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Update achievement progress
     */
    suspend fun updateAchievementProgress(achievementId: String, progress: Int): Result<Unit> {
        return try {
            achievementDao.updateAchievementProgress(achievementId, progress)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Check and unlock achievements based on progress
     */
    suspend fun checkAndUnlockAchievements(
        userId: String,
        studyStreak: Int,
        notesRead: Int,
        quizzesPassed: Int,
        pastPapersAttempted: Int,
        totalStudyTime: Long
    ): Result<List<Achievement>> {
        return try {
            val unlockedAchievements = mutableListOf<Achievement>()
            
            // Get all achievements
            val allAchievements = achievementDao.getAllAchievements()
            
            // Check each achievement type
            val achievementsToCheck = listOf(
                AchievementType.STUDY_STREAK to studyStreak,
                AchievementType.NOTES_READ to notesRead,
                AchievementType.QUIZZES_PASSED to quizzesPassed,
                AchievementType.PAST_PAPERS_ATTEMPTED to pastPapersAttempted,
                AchievementType.TIME_STUDIED to (totalStudyTime / 60).toInt() // Convert to hours
            )
            
            achievementsToCheck.forEach { (type, currentValue) ->
                val achievements = achievementDao.getAchievementsByType(type)
                // Note: This is a simplified version. In a real implementation,
                // you would need to collect the flow and check each achievement
            }
            
            Result.success(unlockedAchievements)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Get unlocked achievement count
     */
    suspend fun getUnlockedAchievementCount(): Int = 
        achievementDao.getUnlockedAchievementCount()
    
    /**
     * Get total achievement count
     */
    suspend fun getTotalAchievementCount(): Int = 
        achievementDao.getTotalAchievementCount()
    
    /**
     * Sync achievements from Firestore
     */
    suspend fun syncAchievementsFromFirestore(): Result<Unit> {
        return try {
            val achievements = firestore.collection("achievements")
                .get()
                .await()
                .toObjects(Achievement::class.java)
            
            achievementDao.insertAchievements(achievements)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Save achievement to Firestore
     */
    suspend fun saveAchievementToFirestore(achievement: Achievement): Result<Unit> {
        return try {
            firestore.collection("achievements")
                .document(achievement.id)
                .set(achievement)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}