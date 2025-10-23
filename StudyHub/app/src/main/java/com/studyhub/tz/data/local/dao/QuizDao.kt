package com.studyhub.tz.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.studyhub.tz.data.model.Quiz
import com.studyhub.tz.data.model.QuizType

@Dao
interface QuizDao {
    @Query("SELECT * FROM quizzes WHERE subjectId = :subjectId ORDER BY updatedAt DESC")
    fun getQuizzesBySubject(subjectId: String): LiveData<List<Quiz>>

    @Query("SELECT * FROM quizzes WHERE id = :quizId")
    fun getQuizById(quizId: String): LiveData<Quiz?>

    @Query("SELECT * FROM quizzes WHERE id = :quizId")
    suspend fun getQuizByIdSync(quizId: String): Quiz?

    @Query("SELECT * FROM quizzes WHERE quizType = :type ORDER BY updatedAt DESC")
    fun getQuizzesByType(type: QuizType): LiveData<List<Quiz>>

    @Query("SELECT * FROM quizzes ORDER BY attemptCount DESC LIMIT :limit")
    fun getPopularQuizzes(limit: Int = 10): LiveData<List<Quiz>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuiz(quiz: Quiz)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizzes(quizzes: List<Quiz>)

    @Update
    suspend fun updateQuiz(quiz: Quiz)

    @Delete
    suspend fun deleteQuiz(quiz: Quiz)

    @Query("DELETE FROM quizzes WHERE subjectId = :subjectId")
    suspend fun deleteQuizzesBySubject(subjectId: String)

    @Query("DELETE FROM quizzes")
    suspend fun deleteAllQuizzes()
}
