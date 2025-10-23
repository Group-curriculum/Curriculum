package com.studyhub.tanzania.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.studyhub.tanzania.data.local.dao.StudySessionDao
import com.studyhub.tanzania.data.models.StudySession
import com.studyhub.tanzania.data.models.StudyActivityType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing study session data
 */
@Singleton
class StudySessionRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val studySessionDao: StudySessionDao
) {
    
    /**
     * Get study sessions by user
     */
    fun getStudySessionsByUser(userId: String): Flow<List<StudySession>> = 
        studySessionDao.getStudySessionsByUser(userId)
    
    /**
     * Get study sessions by user and subject
     */
    fun getStudySessionsByUserAndSubject(userId: String, subjectId: String): Flow<List<StudySession>> = 
        studySessionDao.getStudySessionsByUserAndSubject(userId, subjectId)
    
    /**
     * Get study sessions by user and activity type
     */
    fun getStudySessionsByUserAndActivity(userId: String, activityType: StudyActivityType): Flow<List<StudySession>> = 
        studySessionDao.getStudySessionsByUserAndActivity(userId, activityType)
    
    /**
     * Get study sessions by date range
     */
    fun getStudySessionsByDateRange(userId: String, startDate: Date, endDate: Date): Flow<List<StudySession>> = 
        studySessionDao.getStudySessionsByDateRange(userId, startDate.time, endDate.time)
    
    /**
     * Start a new study session
     */
    suspend fun startStudySession(
        userId: String,
        subjectId: String,
        topicId: String,
        activityType: StudyActivityType,
        contentId: String
    ): Result<StudySession> {
        return try {
            val session = StudySession(
                userId = userId,
                subjectId = subjectId,
                topicId = topicId,
                activityType = activityType,
                contentId = contentId
            )
            studySessionDao.insertStudySession(session)
            Result.success(session)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * End a study session
     */
    suspend fun endStudySession(sessionId: String, score: Float? = null): Result<StudySession> {
        return try {
            val session = studySessionDao.getStudySessionById(sessionId)
            if (session != null) {
                val endTime = Date()
                val duration = (endTime.time - session.startTime.time) / (1000 * 60) // in minutes
                val isCompleted = duration > 0
                
                val updatedSession = session.copy(
                    endTime = endTime,
                    duration = duration,
                    isCompleted = isCompleted,
                    score = score ?: session.score
                )
                
                studySessionDao.updateStudySession(updatedSession)
                Result.success(updatedSession)
            } else {
                Result.failure(Exception("Study session not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Get total study time by user and subject
     */
    suspend fun getTotalStudyTimeByUserAndSubject(userId: String, subjectId: String): Long {
        return studySessionDao.getTotalStudyTimeByUserAndSubject(userId, subjectId) ?: 0L
    }
    
    /**
     * Get total study time by user
     */
    suspend fun getTotalStudyTimeByUser(userId: String): Long {
        return studySessionDao.getTotalStudyTimeByUser(userId) ?: 0L
    }
    
    /**
     * Get study session count by date range
     */
    suspend fun getStudySessionCountByDateRange(userId: String, startDate: Date, endDate: Date): Int {
        return studySessionDao.getStudySessionCountByDateRange(userId, startDate.time, endDate.time)
    }
    
    /**
     * Sync study sessions from Firestore
     */
    suspend fun syncStudySessionsFromFirestore(userId: String): Result<Unit> {
        return try {
            val sessions = firestore.collection("studysessions")
                .whereEqualTo("userId", userId)
                .get()
                .await()
                .toObjects(StudySession::class.java)
            
            sessions.forEach { studySessionDao.insertStudySession(it) }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Save study session to Firestore
     */
    suspend fun saveStudySessionToFirestore(session: StudySession): Result<Unit> {
        return try {
            firestore.collection("studysessions")
                .document(session.id)
                .set(session)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}