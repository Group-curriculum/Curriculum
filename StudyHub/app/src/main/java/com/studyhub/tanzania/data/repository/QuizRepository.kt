package com.studyhub.tanzania.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.studyhub.tanzania.data.local.dao.QuizDao
import com.studyhub.tanzania.data.models.Quiz
import com.studyhub.tanzania.data.models.QuizAttempt
import com.studyhub.tanzania.data.models.EducationLevel
import com.studyhub.tanzania.data.models.QuizType
import com.studyhub.tanzania.data.models.DifficultyLevel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing quiz data
 */
@Singleton
class QuizRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val quizDao: QuizDao
) {
    
    /**
     * Get all quizzes from local database
     */
    fun getAllQuizzes(): Flow<List<Quiz>> = quizDao.getAllQuizzes()
    
    /**
     * Get quizzes by subject
     */
    fun getQuizzesBySubject(subjectId: String): Flow<List<Quiz>> = 
        quizDao.getQuizzesBySubject(subjectId)
    
    /**
     * Get quizzes by topic
     */
    fun getQuizzesByTopic(topicId: String): Flow<List<Quiz>> = 
        quizDao.getQuizzesByTopic(topicId)
    
    /**
     * Get quiz by ID
     */
    suspend fun getQuizById(quizId: String): Quiz? = 
        quizDao.getQuizById(quizId)
    
    /**
     * Get quizzes by type
     */
    fun getQuizzesByType(type: QuizType): Flow<List<Quiz>> = 
        quizDao.getQuizzesByType(type)
    
    /**
     * Get quizzes by difficulty
     */
    fun getQuizzesByDifficulty(difficulty: DifficultyLevel): Flow<List<Quiz>> = 
        quizDao.getQuizzesByDifficulty(difficulty)
    
    /**
     * Get quizzes by education level
     */
    fun getQuizzesByLevel(level: EducationLevel): Flow<List<Quiz>> = 
        quizDao.getQuizzesByLevel(level)
    
    /**
     * Search quizzes by query
     */
    fun searchQuizzes(query: String): Flow<List<Quiz>> = 
        quizDao.searchQuizzes(query)
    
    /**
     * Get bookmarked quizzes
     */
    fun getBookmarkedQuizzes(): Flow<List<Quiz>> = 
        quizDao.getBookmarkedQuizzes()
    
    /**
     * Get offline quizzes
     */
    suspend fun getOfflineQuizzes(): List<Quiz> = 
        quizDao.getOfflineQuizzes()
    
    /**
     * Sync quizzes from Firestore
     */
    suspend fun syncQuizzesFromFirestore(subjectId: String? = null): Result<Unit> {
        return try {
            val query = if (subjectId != null) {
                firestore.collection("quizzes")
                    .whereEqualTo("subjectId", subjectId)
            } else {
                firestore.collection("quizzes")
            }
            
            val quizzes = query.get().await().toObjects(Quiz::class.java)
            quizDao.insertQuizzes(quizzes)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Bookmark/unbookmark a quiz
     */
    suspend fun toggleBookmark(quizId: String): Result<Boolean> {
        return try {
            val quiz = quizDao.getQuizById(quizId)
            if (quiz != null) {
                val updatedQuiz = quiz.copy(isBookmarked = !quiz.isBookmarked)
                quizDao.insertQuiz(updatedQuiz)
                Result.success(updatedQuiz.isBookmarked)
            } else {
                Result.failure(Exception("Quiz not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Start a quiz attempt
     */
    suspend fun startQuizAttempt(quizId: String, userId: String): Result<QuizAttempt> {
        return try {
            val quiz = quizDao.getQuizById(quizId)
            if (quiz != null) {
                val attempt = QuizAttempt(
                    userId = userId,
                    quizId = quizId,
                    totalMarks = quiz.totalMarks
                )
                quizDao.insertQuizAttempt(attempt)
                Result.success(attempt)
            } else {
                Result.failure(Exception("Quiz not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Submit quiz attempt
     */
    suspend fun submitQuizAttempt(attempt: QuizAttempt): Result<QuizAttempt> {
        return try {
            quizDao.updateQuizAttempt(attempt)
            quizDao.incrementAttemptCount(attempt.quizId)
            
            // Update average score
            val averageScore = quizDao.getAverageScore(attempt.userId, attempt.quizId) ?: 0f
            quizDao.updateAverageScore(attempt.quizId, averageScore)
            
            Result.success(attempt)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Get quiz attempts by user
     */
    fun getQuizAttemptsByUser(userId: String): Flow<List<QuizAttempt>> = 
        quizDao.getQuizAttemptsByUser(userId)
    
    /**
     * Get quiz attempts by quiz
     */
    fun getQuizAttemptsByQuiz(quizId: String): Flow<List<QuizAttempt>> = 
        quizDao.getQuizAttemptsByQuiz(quizId)
    
    /**
     * Download quiz for offline use
     */
    suspend fun downloadQuizForOffline(quizId: String): Result<Unit> {
        return try {
            val quiz = quizDao.getQuizById(quizId)
            if (quiz != null) {
                val updatedQuiz = quiz.copy(isOfflineAvailable = true)
                quizDao.insertQuiz(updatedQuiz)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Quiz not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}