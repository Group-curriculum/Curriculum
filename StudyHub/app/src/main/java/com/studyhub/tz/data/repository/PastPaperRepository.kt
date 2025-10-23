package com.studyhub.tz.data.repository

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.studyhub.tz.data.local.dao.PastPaperDao
import com.studyhub.tz.data.model.PastPaper
import kotlinx.coroutines.tasks.await

/**
 * Repository for past papers with Firebase sync and offline caching
 */
class PastPaperRepository(
    private val firestore: FirebaseFirestore,
    private val pastPaperDao: PastPaperDao
) {
    companion object {
        private const val PAST_PAPERS_COLLECTION = "past_papers"
    }

    /**
     * Get past papers by subject
     */
    fun getPastPapersBySubject(subjectId: String): LiveData<List<PastPaper>> {
        return pastPaperDao.getPastPapersBySubject(subjectId)
    }

    /**
     * Get past paper by ID
     */
    fun getPastPaperById(paperId: String): LiveData<PastPaper?> {
        return pastPaperDao.getPastPaperById(paperId)
    }

    /**
     * Get past papers by year
     */
    fun getPastPapersByYear(year: Int): LiveData<List<PastPaper>> {
        return pastPaperDao.getPastPapersByYear(year)
    }

    /**
     * Get bookmarked past papers
     */
    fun getBookmarkedPastPapers(): LiveData<List<PastPaper>> {
        return pastPaperDao.getBookmarkedPastPapers()
    }

    /**
     * Get available years
     */
    fun getAvailableYears(): LiveData<List<Int>> {
        return pastPaperDao.getAvailableYears()
    }

    /**
     * Sync past papers for a subject from Firebase
     */
    suspend fun syncPastPapersForSubject(subjectId: String): Result<Unit> {
        return try {
            val snapshot = firestore.collection(PAST_PAPERS_COLLECTION)
                .whereEqualTo("subjectId", subjectId)
                .get()
                .await()
            
            val pastPapers = snapshot.documents.mapNotNull { doc ->
                doc.toObject(PastPaper::class.java)?.copy(id = doc.id)
            }
            
            pastPaperDao.insertPastPapers(pastPapers)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Toggle bookmark status
     */
    suspend fun toggleBookmark(paperId: String): Result<Unit> {
        return try {
            val paper = pastPaperDao.getPastPaperByIdSync(paperId) 
                ?: throw Exception("Past paper not found")
            val updatedPaper = paper.copy(isBookmarked = !paper.isBookmarked)
            pastPaperDao.updatePastPaper(updatedPaper)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Increment download count
     */
    suspend fun incrementDownloadCount(paperId: String): Result<Unit> {
        return try {
            val paper = pastPaperDao.getPastPaperByIdSync(paperId) 
                ?: throw Exception("Past paper not found")
            val updatedPaper = paper.copy(downloadCount = paper.downloadCount + 1)
            pastPaperDao.updatePastPaper(updatedPaper)
            
            // Also update in Firebase
            firestore.collection(PAST_PAPERS_COLLECTION)
                .document(paperId)
                .update("downloadCount", updatedPaper.downloadCount)
                .await()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
