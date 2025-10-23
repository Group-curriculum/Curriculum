package com.studyhub.tanzania.data.local.dao

import androidx.room.*
import com.studyhub.tanzania.data.models.Quiz
import com.studyhub.tanzania.data.models.QuizAttempt
import com.studyhub.tanzania.data.models.EducationLevel
import com.studyhub.tanzania.data.models.QuizType
import com.studyhub.tanzania.data.models.DifficultyLevel
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Quiz entity
 */
@Dao
interface QuizDao {
    
    @Query("SELECT * FROM quiz ORDER BY createdDate DESC")
    fun getAllQuizzes(): Flow<List<Quiz>>
    
    @Query("SELECT * FROM quiz WHERE subjectId = :subjectId ORDER BY createdDate DESC")
    fun getQuizzesBySubject(subjectId: String): Flow<List<Quiz>>
    
    @Query("SELECT * FROM quiz WHERE topicId = :topicId ORDER BY createdDate DESC")
    fun getQuizzesByTopic(topicId: String): Flow<List<Quiz>>
    
    @Query("SELECT * FROM quiz WHERE id = :quizId")
    suspend fun getQuizById(quizId: String): Quiz?
    
    @Query("SELECT * FROM quiz WHERE type = :type ORDER BY createdDate DESC")
    fun getQuizzesByType(type: QuizType): Flow<List<Quiz>>
    
    @Query("SELECT * FROM quiz WHERE difficulty = :difficulty ORDER BY createdDate DESC")
    fun getQuizzesByDifficulty(difficulty: DifficultyLevel): Flow<List<Quiz>>
    
    @Query("SELECT * FROM quiz WHERE level = :level ORDER BY createdDate DESC")
    fun getQuizzesByLevel(level: EducationLevel): Flow<List<Quiz>>
    
    @Query("SELECT * FROM quiz WHERE isBookmarked = 1 ORDER BY createdDate DESC")
    fun getBookmarkedQuizzes(): Flow<List<Quiz>>
    
    @Query("SELECT * FROM quiz WHERE isOfflineAvailable = 1 ORDER BY createdDate DESC")
    suspend fun getOfflineQuizzes(): List<Quiz>
    
    @Query("SELECT * FROM quiz WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY createdDate DESC")
    fun searchQuizzes(query: String): Flow<List<Quiz>>
    
    @Query("SELECT * FROM quiz WHERE isPremium = 0 ORDER BY createdDate DESC")
    fun getFreeQuizzes(): Flow<List<Quiz>>
    
    @Query("SELECT * FROM quiz WHERE isPremium = 1 ORDER BY createdDate DESC")
    fun getPremiumQuizzes(): Flow<List<Quiz>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuiz(quiz: Quiz)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizzes(quizzes: List<Quiz>)
    
    @Update
    suspend fun updateQuiz(quiz: Quiz)
    
    @Delete
    suspend fun deleteQuiz(quiz: Quiz)
    
    @Query("UPDATE quiz SET isBookmarked = :isBookmarked WHERE id = :quizId")
    suspend fun updateBookmarkStatus(quizId: String, isBookmarked: Boolean)
    
    @Query("UPDATE quiz SET attemptCount = attemptCount + 1 WHERE id = :quizId")
    suspend fun incrementAttemptCount(quizId: String)
    
    @Query("UPDATE quiz SET averageScore = :averageScore WHERE id = :quizId")
    suspend fun updateAverageScore(quizId: String, averageScore: Float)
    
    @Query("UPDATE quiz SET isOfflineAvailable = :isOfflineAvailable WHERE id = :quizId")
    suspend fun updateOfflineAvailability(quizId: String, isOfflineAvailable: Boolean)
    
    // Quiz Attempts
    @Query("SELECT * FROM quizattempt WHERE userId = :userId ORDER BY startTime DESC")
    fun getQuizAttemptsByUser(userId: String): Flow<List<QuizAttempt>>
    
    @Query("SELECT * FROM quizattempt WHERE quizId = :quizId ORDER BY startTime DESC")
    fun getQuizAttemptsByQuiz(quizId: String): Flow<List<QuizAttempt>>
    
    @Query("SELECT * FROM quizattempt WHERE id = :attemptId")
    suspend fun getQuizAttemptById(attemptId: String): QuizAttempt?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizAttempt(attempt: QuizAttempt)
    
    @Update
    suspend fun updateQuizAttempt(attempt: QuizAttempt)
    
    @Delete
    suspend fun deleteQuizAttempt(attempt: QuizAttempt)
    
    @Query("SELECT COUNT(*) FROM quizattempt WHERE userId = :userId AND quizId = :quizId")
    suspend fun getAttemptCount(userId: String, quizId: String): Int
    
    @Query("SELECT AVG(percentage) FROM quizattempt WHERE userId = :userId AND quizId = :quizId")
    suspend fun getAverageScore(userId: String, quizId: String): Float?
}