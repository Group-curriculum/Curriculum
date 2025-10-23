package com.studyhub.tz.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.studyhub.tz.data.model.QuizAttempt

@Dao
interface QuizAttemptDao {
    @Query("SELECT * FROM quiz_attempts WHERE userId = :userId ORDER BY completedAt DESC")
    fun getAllAttemptsByUser(userId: String): LiveData<List<QuizAttempt>>

    @Query("SELECT * FROM quiz_attempts WHERE userId = :userId AND quizId = :quizId ORDER BY completedAt DESC")
    fun getAttemptsByQuiz(userId: String, quizId: String): LiveData<List<QuizAttempt>>

    @Query("SELECT * FROM quiz_attempts WHERE id = :attemptId")
    suspend fun getAttemptById(attemptId: String): QuizAttempt?

    @Query("SELECT AVG(score) FROM quiz_attempts WHERE userId = :userId AND subjectId = :subjectId")
    fun getAverageScoreBySubject(userId: String, subjectId: String): LiveData<Float?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttempt(attempt: QuizAttempt)

    @Update
    suspend fun updateAttempt(attempt: QuizAttempt)

    @Delete
    suspend fun deleteAttempt(attempt: QuizAttempt)

    @Query("DELETE FROM quiz_attempts WHERE userId = :userId")
    suspend fun deleteAllAttemptsByUser(userId: String)
}
