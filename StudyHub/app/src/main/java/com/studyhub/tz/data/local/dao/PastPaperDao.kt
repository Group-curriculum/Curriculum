package com.studyhub.tz.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.studyhub.tz.data.model.PastPaper

@Dao
interface PastPaperDao {
    @Query("SELECT * FROM past_papers WHERE subjectId = :subjectId ORDER BY year DESC")
    fun getPastPapersBySubject(subjectId: String): LiveData<List<PastPaper>>

    @Query("SELECT * FROM past_papers WHERE id = :paperId")
    fun getPastPaperById(paperId: String): LiveData<PastPaper?>

    @Query("SELECT * FROM past_papers WHERE id = :paperId")
    suspend fun getPastPaperByIdSync(paperId: String): PastPaper?

    @Query("SELECT * FROM past_papers WHERE year = :year ORDER BY subjectId ASC")
    fun getPastPapersByYear(year: Int): LiveData<List<PastPaper>>

    @Query("SELECT * FROM past_papers WHERE isBookmarked = 1 ORDER BY year DESC")
    fun getBookmarkedPastPapers(): LiveData<List<PastPaper>>

    @Query("SELECT DISTINCT year FROM past_papers ORDER BY year DESC")
    fun getAvailableYears(): LiveData<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPastPaper(pastPaper: PastPaper)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPastPapers(pastPapers: List<PastPaper>)

    @Update
    suspend fun updatePastPaper(pastPaper: PastPaper)

    @Delete
    suspend fun deletePastPaper(pastPaper: PastPaper)

    @Query("DELETE FROM past_papers WHERE subjectId = :subjectId")
    suspend fun deletePastPapersBySubject(subjectId: String)

    @Query("DELETE FROM past_papers")
    suspend fun deleteAllPastPapers()
}
