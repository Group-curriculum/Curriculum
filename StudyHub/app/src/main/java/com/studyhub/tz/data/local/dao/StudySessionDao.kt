package com.studyhub.tz.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.studyhub.tz.data.model.StudySession

@Dao
interface StudySessionDao {
    @Query("SELECT * FROM study_sessions WHERE userId = :userId ORDER BY startTime DESC")
    fun getAllSessionsByUser(userId: String): LiveData<List<StudySession>>

    @Query("SELECT * FROM study_sessions WHERE userId = :userId AND subjectId = :subjectId ORDER BY startTime DESC")
    fun getSessionsBySubject(userId: String, subjectId: String): LiveData<List<StudySession>>

    @Query("SELECT SUM(duration) FROM study_sessions WHERE userId = :userId")
    fun getTotalStudyTime(userId: String): LiveData<Long?>

    @Query("SELECT SUM(duration) FROM study_sessions WHERE userId = :userId AND subjectId = :subjectId")
    fun getStudyTimeBySubject(userId: String, subjectId: String): LiveData<Long?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: StudySession)

    @Update
    suspend fun updateSession(session: StudySession)

    @Delete
    suspend fun deleteSession(session: StudySession)

    @Query("DELETE FROM study_sessions WHERE userId = :userId")
    suspend fun deleteAllSessionsByUser(userId: String)
}
