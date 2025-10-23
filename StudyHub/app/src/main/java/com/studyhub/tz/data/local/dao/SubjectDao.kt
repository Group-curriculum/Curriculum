package com.studyhub.tz.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.studyhub.tz.data.model.EducationLevel
import com.studyhub.tz.data.model.Subject

@Dao
interface SubjectDao {
    @Query("SELECT * FROM subjects ORDER BY `order` ASC")
    fun getAllSubjects(): LiveData<List<Subject>>

    @Query("SELECT * FROM subjects WHERE level = :level ORDER BY `order` ASC")
    fun getSubjectsByLevel(level: EducationLevel): LiveData<List<Subject>>

    @Query("SELECT * FROM subjects WHERE id = :subjectId")
    fun getSubjectById(subjectId: String): LiveData<Subject?>

    @Query("SELECT * FROM subjects WHERE id = :subjectId")
    suspend fun getSubjectByIdSync(subjectId: String): Subject?

    @Query("SELECT * FROM subjects WHERE isPopular = 1 ORDER BY `order` ASC LIMIT :limit")
    fun getPopularSubjects(limit: Int = 6): LiveData<List<Subject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: Subject)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubjects(subjects: List<Subject>)

    @Update
    suspend fun updateSubject(subject: Subject)

    @Delete
    suspend fun deleteSubject(subject: Subject)

    @Query("DELETE FROM subjects")
    suspend fun deleteAllSubjects()
}
