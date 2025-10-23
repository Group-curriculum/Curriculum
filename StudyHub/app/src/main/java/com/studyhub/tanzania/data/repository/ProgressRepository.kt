package com.studyhub.tanzania.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.studyhub.tanzania.data.local.dao.ProgressDao
import com.studyhub.tanzania.data.models.Progress
import com.studyhub.tanzania.data.models.EducationLevel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing progress data
 */
@Singleton
class ProgressRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val progressDao: ProgressDao
) {
    
    /**
     * Get progress by user
     */
    fun getProgressByUser(userId: String): Flow<List<Progress>> = 
        progressDao.getProgressByUser(userId)
    
    /**
     * Get progress by user and subject
     */
    fun getProgressByUserAndSubject(userId: String, subjectId: String): Flow<Progress?> = 
        progressDao.getProgressByUserAndSubject(userId, subjectId)
    
    /**
     * Get progress by user and level
     */
    fun getProgressByUserAndLevel(userId: String, level: EducationLevel): Flow<List<Progress>> = 
        progressDao.getProgressByUserAndLevel(userId, level)
    
    /**
     * Update study time
     */
    suspend fun updateStudyTime(userId: String, subjectId: String, studyTime: Long): Result<Unit> {
        return try {
            progressDao.updateTotalStudyTime(userId, subjectId, studyTime)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Increment notes read
     */
    suspend fun incrementNotesRead(userId: String, subjectId: String): Result<Unit> {
        return try {
            progressDao.incrementNotesRead(userId, subjectId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Increment quizzes attempted
     */
    suspend fun incrementQuizzesAttempted(userId: String, subjectId: String): Result<Unit> {
        return try {
            progressDao.incrementQuizzesAttempted(userId, subjectId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Increment quizzes passed
     */
    suspend fun incrementQuizzesPassed(userId: String, subjectId: String): Result<Unit> {
        return try {
            progressDao.incrementQuizzesPassed(userId, subjectId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Increment past papers attempted
     */
    suspend fun incrementPastPapersAttempted(userId: String, subjectId: String): Result<Unit> {
        return try {
            progressDao.incrementPastPapersAttempted(userId, subjectId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Increment past papers passed
     */
    suspend fun incrementPastPapersPassed(userId: String, subjectId: String): Result<Unit> {
        return try {
            progressDao.incrementPastPapersPassed(userId, subjectId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Update average quiz score
     */
    suspend fun updateAverageQuizScore(userId: String, subjectId: String, averageScore: Float): Result<Unit> {
        return try {
            progressDao.updateAverageQuizScore(userId, subjectId, averageScore)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Update average past paper score
     */
    suspend fun updateAveragePastPaperScore(userId: String, subjectId: String, averageScore: Float): Result<Unit> {
        return try {
            progressDao.updateAveragePastPaperScore(userId, subjectId, averageScore)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Update study streak
     */
    suspend fun updateStudyStreak(userId: String, subjectId: String, streak: Int): Result<Unit> {
        return try {
            progressDao.updateCurrentStreak(userId, subjectId, streak)
            if (streak > 0) {
                progressDao.updateLongestStreak(userId, subjectId, streak)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Update last study date
     */
    suspend fun updateLastStudyDate(userId: String, subjectId: String): Result<Unit> {
        return try {
            val currentTime = System.currentTimeMillis()
            progressDao.updateLastStudyDate(userId, subjectId, currentTime)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Sync progress from Firestore
     */
    suspend fun syncProgressFromFirestore(userId: String): Result<Unit> {
        return try {
            val progress = firestore.collection("progress")
                .whereEqualTo("userId", userId)
                .get()
                .await()
                .toObjects(Progress::class.java)
            
            progress.forEach { progressDao.insertProgress(it) }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Save progress to Firestore
     */
    suspend fun saveProgressToFirestore(progress: Progress): Result<Unit> {
        return try {
            firestore.collection("progress")
                .document("${progress.userId}_${progress.subjectId}")
                .set(progress)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}