package com.studyhub.tanzania.data.local.dao

import androidx.room.*
import com.studyhub.tanzania.data.models.Subject
import com.studyhub.tanzania.data.models.EducationLevel
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Subject entity
 */
@Dao
interface SubjectDao {
    
    @Query("SELECT * FROM subject ORDER BY `order` ASC")
    fun getAllSubjects(): Flow<List<Subject>>
    
    @Query("SELECT * FROM subject WHERE level = :level ORDER BY `order` ASC")
    fun getSubjectsByLevel(level: EducationLevel): Flow<List<Subject>>
    
    @Query("SELECT * FROM subject WHERE id = :subjectId")
    suspend fun getSubjectById(subjectId: String): Subject?
    
    @Query("SELECT * FROM subject WHERE level = :level AND isCore = 1 ORDER BY `order` ASC")
    fun getCoreSubjects(level: EducationLevel): Flow<List<Subject>>
    
    @Query("SELECT * FROM subject WHERE name LIKE '%' || :query || '%' OR nameSwahili LIKE '%' || :query || '%' ORDER BY `order` ASC")
    fun searchSubjects(query: String): Flow<List<Subject>>
    
    @Query("SELECT * FROM subject WHERE isAvailable = 1 ORDER BY `order` ASC")
    fun getAvailableSubjects(): Flow<List<Subject>>
    
    @Query("SELECT * FROM subject WHERE isOfflineAvailable = 1")
    suspend fun getOfflineSubjects(): List<Subject>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: Subject)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubjects(subjects: List<Subject>)
    
    @Update
    suspend fun updateSubject(subject: Subject)
    
    @Delete
    suspend fun deleteSubject(subject: Subject)
    
    @Query("UPDATE subject SET isAvailable = :isAvailable WHERE id = :subjectId")
    suspend fun updateSubjectAvailability(subjectId: String, isAvailable: Boolean)
    
    @Query("UPDATE subject SET isOfflineAvailable = :isOfflineAvailable WHERE id = :subjectId")
    suspend fun updateSubjectOfflineAvailability(subjectId: String, isOfflineAvailable: Boolean)
    
    @Query("SELECT COUNT(*) FROM subject WHERE level = :level")
    suspend fun getSubjectCountByLevel(level: EducationLevel): Int
}