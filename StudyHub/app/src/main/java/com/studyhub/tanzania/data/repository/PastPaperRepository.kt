package com.studyhub.tanzania.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.studyhub.tanzania.data.local.dao.PastPaperDao
import com.studyhub.tanzania.data.models.PastPaper
import com.studyhub.tanzania.data.models.PastPaperAttempt
import com.studyhub.tanzania.data.models.EducationLevel
import com.studyhub.tanzania.data.models.ExamMonth
import com.studyhub.tanzania.data.models.PaperType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing past paper data
 */
@Singleton
class PastPaperRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val pastPaperDao: PastPaperDao
) {
    
    /**
     * Get all past papers from local database
     */
    fun getAllPastPapers(): Flow<List<PastPaper>> = pastPaperDao.getAllPastPapers()
    
    /**
     * Get past papers by subject
     */
    fun getPastPapersBySubject(subjectId: String): Flow<List<PastPaper>> = 
        pastPaperDao.getPastPapersBySubject(subjectId)
    
    /**
     * Get past papers by education level
     */
    fun getPastPapersByLevel(level: EducationLevel): Flow<List<PastPaper>> = 
        pastPaperDao.getPastPapersByLevel(level)
    
    /**
     * Get past paper by ID
     */
    suspend fun getPastPaperById(pastPaperId: String): PastPaper? = 
        pastPaperDao.getPastPaperById(pastPaperId)
    
    /**
     * Get past papers by year
     */
    fun getPastPapersByYear(year: Int): Flow<List<PastPaper>> = 
        pastPaperDao.getPastPapersByYear(year)
    
    /**
     * Get past papers by month
     */
    fun getPastPapersByMonth(month: ExamMonth): Flow<List<PastPaper>> = 
        pastPaperDao.getPastPapersByMonth(month)
    
    /**
     * Get past papers by type
     */
    fun getPastPapersByType(paperType: PaperType): Flow<List<PastPaper>> = 
        pastPaperDao.getPastPapersByType(paperType)
    
    /**
     * Search past papers by query
     */
    fun searchPastPapers(query: String): Flow<List<PastPaper>> = 
        pastPaperDao.searchPastPapers(query)
    
    /**
     * Get offline past papers
     */
    suspend fun getOfflinePastPapers(): List<PastPaper> = 
        pastPaperDao.getOfflinePastPapers()
    
    /**
     * Get available years
     */
    fun getAvailableYears(): Flow<List<Int>> = 
        pastPaperDao.getAvailableYears()
    
    /**
     * Sync past papers from Firestore
     */
    suspend fun syncPastPapersFromFirestore(subjectId: String? = null): Result<Unit> {
        return try {
            val query = if (subjectId != null) {
                firestore.collection("pastpapers")
                    .whereEqualTo("subjectId", subjectId)
            } else {
                firestore.collection("pastpapers")
            }
            
            val pastPapers = query.get().await().toObjects(PastPaper::class.java)
            pastPaperDao.insertPastPapers(pastPapers)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Download past paper for offline use
     */
    suspend fun downloadPastPaperForOffline(pastPaperId: String): Result<Unit> {
        return try {
            val pastPaper = pastPaperDao.getPastPaperById(pastPaperId)
            if (pastPaper != null) {
                val updatedPastPaper = pastPaper.copy(
                    isOfflineAvailable = true,
                    isDownloaded = true
                )
                pastPaperDao.insertPastPaper(updatedPastPaper)
                pastPaperDao.incrementDownloadCount(pastPaperId)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Past paper not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Start a past paper attempt
     */
    suspend fun startPastPaperAttempt(pastPaperId: String, userId: String): Result<PastPaperAttempt> {
        return try {
            val pastPaper = pastPaperDao.getPastPaperById(pastPaperId)
            if (pastPaper != null) {
                val attempt = PastPaperAttempt(
                    userId = userId,
                    pastPaperId = pastPaperId,
                    totalMarks = pastPaper.totalMarks
                )
                pastPaperDao.insertPastPaperAttempt(attempt)
                Result.success(attempt)
            } else {
                Result.failure(Exception("Past paper not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Submit past paper attempt
     */
    suspend fun submitPastPaperAttempt(attempt: PastPaperAttempt): Result<PastPaperAttempt> {
        return try {
            pastPaperDao.updatePastPaperAttempt(attempt)
            
            // Update average score
            val averageScore = pastPaperDao.getAverageScore(attempt.userId, attempt.pastPaperId) ?: 0f
            pastPaperDao.updateAverageRating(attempt.pastPaperId, averageScore)
            
            Result.success(attempt)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Get past paper attempts by user
     */
    fun getPastPaperAttemptsByUser(userId: String): Flow<List<PastPaperAttempt>> = 
        pastPaperDao.getPastPaperAttemptsByUser(userId)
    
    /**
     * Get past paper attempts by paper
     */
    fun getPastPaperAttemptsByPaper(pastPaperId: String): Flow<List<PastPaperAttempt>> = 
        pastPaperDao.getPastPaperAttemptsByPaper(pastPaperId)
    
    /**
     * Update past paper rating
     */
    suspend fun updatePastPaperRating(pastPaperId: String, rating: Float): Result<Unit> {
        return try {
            pastPaperDao.updateAverageRating(pastPaperId, rating)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}