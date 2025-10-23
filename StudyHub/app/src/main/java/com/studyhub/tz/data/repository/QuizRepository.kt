package com.studyhub.tz.data.repository

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.studyhub.tz.data.local.dao.QuizAttemptDao
import com.studyhub.tz.data.local.dao.QuizDao
import com.studyhub.tz.data.model.Quiz
import com.studyhub.tz.data.model.QuizAttempt
import com.studyhub.tz.data.model.QuizType
import kotlinx.coroutines.tasks.await
import java.util.UUID

/**
 * Repository for quizzes with Firebase sync and offline caching
 */
class QuizRepository(
    private val firestore: FirebaseFirestore,
    private val quizDao: QuizDao,
    private val quizAttemptDao: QuizAttemptDao
) {
    companion object {
        private const val QUIZZES_COLLECTION = "quizzes"
        private const val QUIZ_ATTEMPTS_COLLECTION = "quiz_attempts"
    }

    /**
     * Get quizzes by subject
     */
    fun getQuizzesBySubject(subjectId: String): LiveData<List<Quiz>> {
        return quizDao.getQuizzesBySubject(subjectId)
    }

    /**
     * Get quiz by ID
     */
    fun getQuizById(quizId: String): LiveData<Quiz?> {
        return quizDao.getQuizById(quizId)
    }

    /**
     * Get quizzes by type
     */
    fun getQuizzesByType(type: QuizType): LiveData<List<Quiz>> {
        return quizDao.getQuizzesByType(type)
    }

    /**
     * Get popular quizzes
     */
    fun getPopularQuizzes(limit: Int = 10): LiveData<List<Quiz>> {
        return quizDao.getPopularQuizzes(limit)
    }

    /**
     * Sync quizzes for a subject from Firebase
     */
    suspend fun syncQuizzesForSubject(subjectId: String): Result<Unit> {
        return try {
            val snapshot = firestore.collection(QUIZZES_COLLECTION)
                .whereEqualTo("subjectId", subjectId)
                .get()
                .await()
            
            val quizzes = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Quiz::class.java)?.copy(id = doc.id)
            }
            
            quizDao.insertQuizzes(quizzes)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get quiz attempts by user
     */
    fun getAttemptsByUser(userId: String): LiveData<List<QuizAttempt>> {
        return quizAttemptDao.getAllAttemptsByUser(userId)
    }

    /**
     * Get attempts for a specific quiz
     */
    fun getAttemptsByQuiz(userId: String, quizId: String): LiveData<List<QuizAttempt>> {
        return quizAttemptDao.getAttemptsByQuiz(userId, quizId)
    }

    /**
     * Submit quiz attempt
     */
    suspend fun submitQuizAttempt(attempt: QuizAttempt): Result<Unit> {
        return try {
            // Save to local database
            quizAttemptDao.insertAttempt(attempt)
            
            // Save to Firebase
            firestore.collection(QUIZ_ATTEMPTS_COLLECTION)
                .document(attempt.id)
                .set(attempt)
                .await()
            
            // Update quiz statistics
            val quiz = quizDao.getQuizByIdSync(attempt.quizId)
            if (quiz != null) {
                val newAttemptCount = quiz.attemptCount + 1
                val newAverageScore = ((quiz.averageScore * quiz.attemptCount) + attempt.score) / newAttemptCount
                val updatedQuiz = quiz.copy(
                    attemptCount = newAttemptCount,
                    averageScore = newAverageScore
                )
                quizDao.updateQuiz(updatedQuiz)
            }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get average score by subject
     */
    fun getAverageScoreBySubject(userId: String, subjectId: String): LiveData<Float?> {
        return quizAttemptDao.getAverageScoreBySubject(userId, subjectId)
    }
}
