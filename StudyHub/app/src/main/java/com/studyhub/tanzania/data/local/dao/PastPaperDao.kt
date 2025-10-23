package com.studyhub.tanzania.data.local.dao

import androidx.room.*
import com.studyhub.tanzania.data.models.PastPaper
import com.studyhub.tanzania.data.models.PastPaperAttempt
import com.studyhub.tanzania.data.models.EducationLevel
import com.studyhub.tanzania.data.models.ExamMonth
import com.studyhub.tanzania.data.models.PaperType
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for PastPaper entity
 */
@Dao
interface PastPaperDao {
    
    @Query("SELECT * FROM pastpaper ORDER BY year DESC, month DESC")
    fun getAllPastPapers(): Flow<List<PastPaper>>
    
    @Query("SELECT * FROM pastpaper WHERE subjectId = :subjectId ORDER BY year DESC, month DESC")
    fun getPastPapersBySubject(subjectId: String): Flow<List<PastPaper>>
    
    @Query("SELECT * FROM pastpaper WHERE level = :level ORDER BY year DESC, month DESC")
    fun getPastPapersByLevel(level: EducationLevel): Flow<List<PastPaper>>
    
    @Query("SELECT * FROM pastpaper WHERE id = :pastPaperId")
    suspend fun getPastPaperById(pastPaperId: String): PastPaper?
    
    @Query("SELECT * FROM pastpaper WHERE year = :year ORDER BY month DESC")
    fun getPastPapersByYear(year: Int): Flow<List<PastPaper>>
    
    @Query("SELECT * FROM pastpaper WHERE month = :month ORDER BY year DESC")
    fun getPastPapersByMonth(month: ExamMonth): Flow<List<PastPaper>>
    
    @Query("SELECT * FROM pastpaper WHERE paperType = :paperType ORDER BY year DESC, month DESC")
    fun getPastPapersByType(paperType: PaperType): Flow<List<PastPaper>>
    
    @Query("SELECT * FROM pastpaper WHERE isOfflineAvailable = 1 ORDER BY year DESC, month DESC")
    suspend fun getOfflinePastPapers(): List<PastPaper>
    
    @Query("SELECT * FROM pastpaper WHERE title LIKE '%' || :query || '%' ORDER BY year DESC, month DESC")
    fun searchPastPapers(query: String): Flow<List<PastPaper>>
    
    @Query("SELECT * FROM pastpaper WHERE isPremium = 0 ORDER BY year DESC, month DESC")
    fun getFreePastPapers(): Flow<List<PastPaper>>
    
    @Query("SELECT * FROM pastpaper WHERE isPremium = 1 ORDER BY year DESC, month DESC")
    fun getPremiumPastPapers(): Flow<List<PastPaper>>
    
    @Query("SELECT DISTINCT year FROM pastpaper ORDER BY year DESC")
    fun getAvailableYears(): Flow<List<Int>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPastPaper(pastPaper: PastPaper)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPastPapers(pastPapers: List<PastPaper>)
    
    @Update
    suspend fun updatePastPaper(pastPaper: PastPaper)
    
    @Delete
    suspend fun deletePastPaper(pastPaper: PastPaper)
    
    @Query("UPDATE pastpaper SET downloadCount = downloadCount + 1 WHERE id = :pastPaperId")
    suspend fun incrementDownloadCount(pastPaperId: String)
    
    @Query("UPDATE pastpaper SET averageRating = :averageRating WHERE id = :pastPaperId")
    suspend fun updateAverageRating(pastPaperId: String, averageRating: Float)
    
    @Query("UPDATE pastpaper SET isDownloaded = :isDownloaded WHERE id = :pastPaperId")
    suspend fun updateDownloadStatus(pastPaperId: String, isDownloaded: Boolean)
    
    @Query("UPDATE pastpaper SET isOfflineAvailable = :isOfflineAvailable WHERE id = :pastPaperId")
    suspend fun updateOfflineAvailability(pastPaperId: String, isOfflineAvailable: Boolean)
    
    // Past Paper Attempts
    @Query("SELECT * FROM pastpaperattempt WHERE userId = :userId ORDER BY startTime DESC")
    fun getPastPaperAttemptsByUser(userId: String): Flow<List<PastPaperAttempt>>
    
    @Query("SELECT * FROM pastpaperattempt WHERE pastPaperId = :pastPaperId ORDER BY startTime DESC")
    fun getPastPaperAttemptsByPaper(pastPaperId: String): Flow<List<PastPaperAttempt>>
    
    @Query("SELECT * FROM pastpaperattempt WHERE id = :attemptId")
    suspend fun getPastPaperAttemptById(attemptId: String): PastPaperAttempt?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPastPaperAttempt(attempt: PastPaperAttempt)
    
    @Update
    suspend fun updatePastPaperAttempt(attempt: PastPaperAttempt)
    
    @Delete
    suspend fun deletePastPaperAttempt(attempt: PastPaperAttempt)
    
    @Query("SELECT COUNT(*) FROM pastpaperattempt WHERE userId = :userId AND pastPaperId = :pastPaperId")
    suspend fun getAttemptCount(userId: String, pastPaperId: String): Int
    
    @Query("SELECT AVG(percentage) FROM pastpaperattempt WHERE userId = :userId AND pastPaperId = :pastPaperId")
    suspend fun getAverageScore(userId: String, pastPaperId: String): Float?
}