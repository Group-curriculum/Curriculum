package com.studyhub.tanzania.data.local.dao

import androidx.room.*
import com.studyhub.tanzania.data.models.StudySession
import com.studyhub.tanzania.data.models.StudyActivityType
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * Data Access Object for StudySession entity
 */
@Dao
interface StudySessionDao {
    
    @Query("SELECT * FROM studysession WHERE userId = :userId ORDER BY startTime DESC")
    fun getStudySessionsByUser(userId: String): Flow<List<StudySession>>
    
    @Query("SELECT * FROM studysession WHERE userId = :userId AND subjectId = :subjectId ORDER BY startTime DESC")
    fun getStudySessionsByUserAndSubject(userId: String, subjectId: String): Flow<List<StudySession>>
    
    @Query("SELECT * FROM studysession WHERE userId = :userId AND activityType = :activityType ORDER BY startTime DESC")
    fun getStudySessionsByUserAndActivity(userId: String, activityType: StudyActivityType): Flow<List<StudySession>>
    
    @Query("SELECT * FROM studysession WHERE id = :sessionId")
    suspend fun getStudySessionById(sessionId: String): StudySession?
    
    @Query("SELECT * FROM studysession WHERE userId = :userId AND startTime >= :startDate AND startTime <= :endDate ORDER BY startTime DESC")
    fun getStudySessionsByDateRange(userId: String, startDate: Long, endDate: Long): Flow<List<StudySession>>
    
    @Query("SELECT SUM(duration) FROM studysession WHERE userId = :userId AND subjectId = :subjectId")
    suspend fun getTotalStudyTimeByUserAndSubject(userId: String, subjectId: String): Long?
    
    @Query("SELECT SUM(duration) FROM studysession WHERE userId = :userId")
    suspend fun getTotalStudyTimeByUser(userId: String): Long?
    
    @Query("SELECT COUNT(*) FROM studysession WHERE userId = :userId AND startTime >= :startDate AND startTime <= :endDate")
    suspend fun getStudySessionCountByDateRange(userId: String, startDate: Long, endDate: Long): Int
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudySession(session: StudySession)
    
    @Update
    suspend fun updateStudySession(session: StudySession)
    
    @Delete
    suspend fun deleteStudySession(session: StudySession)
    
    @Query("UPDATE studysession SET endTime = :endTime, duration = :duration, isCompleted = :isCompleted WHERE id = :sessionId")
    suspend fun endStudySession(sessionId: String, endTime: Long, duration: Long, isCompleted: Boolean)
    
    @Query("UPDATE studysession SET score = :score WHERE id = :sessionId")
    suspend fun updateStudySessionScore(sessionId: String, score: Float)
}