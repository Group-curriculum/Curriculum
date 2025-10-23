package com.studyhub.tanzania.data.local.dao

import androidx.room.*
import com.studyhub.tanzania.data.models.User
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for User entity
 */
@Dao
interface UserDao {
    
    @Query("SELECT * FROM user WHERE id = :userId")
    suspend fun getUserById(userId: String): User?
    
    @Query("SELECT * FROM user LIMIT 1")
    fun getCurrentUser(): Flow<User?>
    
    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)
    
    @Update
    suspend fun updateUser(user: User)
    
    @Delete
    suspend fun deleteUser(user: User)
    
    @Query("DELETE FROM user")
    suspend fun clearCurrentUser()
    
    @Query("SELECT * FROM user WHERE isPremium = 1")
    fun getPremiumUsers(): Flow<List<User>>
    
    @Query("UPDATE user SET lastActiveDate = :lastActiveDate WHERE id = :userId")
    suspend fun updateLastActiveDate(userId: String, lastActiveDate: Long)
    
    @Query("UPDATE user SET studyStreak = :streak WHERE id = :userId")
    suspend fun updateStudyStreak(userId: String, streak: Int)
    
    @Query("UPDATE user SET totalStudyTime = :totalTime WHERE id = :userId")
    suspend fun updateTotalStudyTime(userId: String, totalTime: Long)
}