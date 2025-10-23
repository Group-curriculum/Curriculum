package com.studyhub.tanzania.data.local.dao

import androidx.room.*
import com.studyhub.tanzania.data.models.Achievement
import com.studyhub.tanzania.data.models.AchievementType
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Achievement entity
 */
@Dao
interface AchievementDao {
    
    @Query("SELECT * FROM achievement ORDER BY type ASC, requirement ASC")
    fun getAllAchievements(): Flow<List<Achievement>>
    
    @Query("SELECT * FROM achievement WHERE type = :type ORDER BY requirement ASC")
    fun getAchievementsByType(type: AchievementType): Flow<List<Achievement>>
    
    @Query("SELECT * FROM achievement WHERE isUnlocked = 1 ORDER BY unlockedDate DESC")
    fun getUnlockedAchievements(): Flow<List<Achievement>>
    
    @Query("SELECT * FROM achievement WHERE isUnlocked = 0 ORDER BY type ASC, requirement ASC")
    fun getLockedAchievements(): Flow<List<Achievement>>
    
    @Query("SELECT * FROM achievement WHERE id = :achievementId")
    suspend fun getAchievementById(achievementId: String): Achievement?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievement(achievement: Achievement)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievements(achievements: List<Achievement>)
    
    @Update
    suspend fun updateAchievement(achievement: Achievement)
    
    @Delete
    suspend fun deleteAchievement(achievement: Achievement)
    
    @Query("UPDATE achievement SET isUnlocked = :isUnlocked, unlockedDate = :unlockedDate WHERE id = :achievementId")
    suspend fun updateAchievementUnlockStatus(achievementId: String, isUnlocked: Boolean, unlockedDate: Long?)
    
    @Query("UPDATE achievement SET currentProgress = :currentProgress WHERE id = :achievementId")
    suspend fun updateAchievementProgress(achievementId: String, currentProgress: Int)
    
    @Query("SELECT COUNT(*) FROM achievement WHERE isUnlocked = 1")
    suspend fun getUnlockedAchievementCount(): Int
    
    @Query("SELECT COUNT(*) FROM achievement")
    suspend fun getTotalAchievementCount(): Int
}