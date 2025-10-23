package com.studyhub.tz.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.studyhub.tz.data.model.UserProgress

@Dao
interface UserProgressDao {
    @Query("SELECT * FROM user_progress WHERE userId = :userId")
    fun getAllProgressByUser(userId: String): LiveData<List<UserProgress>>

    @Query("SELECT * FROM user_progress WHERE userId = :userId AND subjectId = :subjectId")
    fun getProgressBySubject(userId: String, subjectId: String): LiveData<UserProgress?>

    @Query("SELECT * FROM user_progress WHERE userId = :userId AND subjectId = :subjectId")
    suspend fun getProgressBySubjectSync(userId: String, subjectId: String): UserProgress?

    @Query("SELECT * FROM user_progress WHERE userId = :userId ORDER BY averageScore DESC LIMIT :limit")
    fun getTopSubjects(userId: String, limit: Int = 5): LiveData<List<UserProgress>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: UserProgress)

    @Update
    suspend fun updateProgress(progress: UserProgress)

    @Delete
    suspend fun deleteProgress(progress: UserProgress)

    @Query("DELETE FROM user_progress WHERE userId = :userId")
    suspend fun deleteAllProgressByUser(userId: String)
}
