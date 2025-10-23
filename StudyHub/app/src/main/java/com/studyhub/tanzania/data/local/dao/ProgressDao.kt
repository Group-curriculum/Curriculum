package com.studyhub.tanzania.data.local.dao

import androidx.room.*
import com.studyhub.tanzania.data.models.Progress
import com.studyhub.tanzania.data.models.EducationLevel
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Progress entity
 */
@Dao
interface ProgressDao {
    
    @Query("SELECT * FROM progress WHERE userId = :userId")
    fun getProgressByUser(userId: String): Flow<List<Progress>>
    
    @Query("SELECT * FROM progress WHERE userId = :userId AND subjectId = :subjectId")
    fun getProgressByUserAndSubject(userId: String, subjectId: String): Flow<Progress?>
    
    @Query("SELECT * FROM progress WHERE userId = :userId AND level = :level")
    fun getProgressByUserAndLevel(userId: String, level: EducationLevel): Flow<List<Progress>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: Progress)
    
    @Update
    suspend fun updateProgress(progress: Progress)
    
    @Delete
    suspend fun deleteProgress(progress: Progress)
    
    @Query("UPDATE progress SET totalStudyTime = :totalStudyTime WHERE userId = :userId AND subjectId = :subjectId")
    suspend fun updateTotalStudyTime(userId: String, subjectId: String, totalStudyTime: Long)
    
    @Query("UPDATE progress SET notesRead = notesRead + 1 WHERE userId = :userId AND subjectId = :subjectId")
    suspend fun incrementNotesRead(userId: String, subjectId: String)
    
    @Query("UPDATE progress SET quizzesAttempted = quizzesAttempted + 1 WHERE userId = :userId AND subjectId = :subjectId")
    suspend fun incrementQuizzesAttempted(userId: String, subjectId: String)
    
    @Query("UPDATE progress SET quizzesPassed = quizzesPassed + 1 WHERE userId = :userId AND subjectId = :subjectId")
    suspend fun incrementQuizzesPassed(userId: String, subjectId: String)
    
    @Query("UPDATE progress SET pastPapersAttempted = pastPapersAttempted + 1 WHERE userId = :userId AND subjectId = :subjectId")
    suspend fun incrementPastPapersAttempted(userId: String, subjectId: String)
    
    @Query("UPDATE progress SET pastPapersPassed = pastPapersPassed + 1 WHERE userId = :userId AND subjectId = :subjectId")
    suspend fun incrementPastPapersPassed(userId: String, subjectId: String)
    
    @Query("UPDATE progress SET averageQuizScore = :averageScore WHERE userId = :userId AND subjectId = :subjectId")
    suspend fun updateAverageQuizScore(userId: String, subjectId: String, averageScore: Float)
    
    @Query("UPDATE progress SET averagePastPaperScore = :averageScore WHERE userId = :userId AND subjectId = :subjectId")
    suspend fun updateAveragePastPaperScore(userId: String, subjectId: String, averageScore: Float)
    
    @Query("UPDATE progress SET currentStreak = :streak WHERE userId = :userId AND subjectId = :subjectId")
    suspend fun updateCurrentStreak(userId: String, subjectId: String, streak: Int)
    
    @Query("UPDATE progress SET longestStreak = :streak WHERE userId = :userId AND subjectId = :subjectId")
    suspend fun updateLongestStreak(userId: String, subjectId: String, streak: Int)
    
    @Query("UPDATE progress SET lastStudyDate = :lastStudyDate WHERE userId = :userId AND subjectId = :subjectId")
    suspend fun updateLastStudyDate(userId: String, subjectId: String, lastStudyDate: Long)
}