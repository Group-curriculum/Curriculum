package com.studyhub.tanzania.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.studyhub.tanzania.data.local.dao.SubjectDao
import com.studyhub.tanzania.data.models.Subject
import com.studyhub.tanzania.data.models.EducationLevel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing subject data
 */
@Singleton
class SubjectRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val subjectDao: SubjectDao
) {
    
    /**
     * Get all subjects from local database
     */
    fun getAllSubjects(): Flow<List<Subject>> = subjectDao.getAllSubjects()
    
    /**
     * Get subjects by education level
     */
    fun getSubjectsByLevel(level: EducationLevel): Flow<List<Subject>> = 
        subjectDao.getSubjectsByLevel(level)
    
    /**
     * Get subject by ID
     */
    suspend fun getSubjectById(subjectId: String): Subject? = 
        subjectDao.getSubjectById(subjectId)
    
    /**
     * Get core subjects for a level
     */
    fun getCoreSubjects(level: EducationLevel): Flow<List<Subject>> = 
        subjectDao.getCoreSubjects(level)
    
    /**
     * Search subjects by name
     */
    fun searchSubjects(query: String): Flow<List<Subject>> = 
        subjectDao.searchSubjects(query)
    
    /**
     * Sync subjects from Firestore
     */
    suspend fun syncSubjectsFromFirestore(): Result<Unit> {
        return try {
            val subjects = firestore.collection("subjects")
                .get()
                .await()
                .toObjects(Subject::class.java)
            
            subjectDao.insertSubjects(subjects)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Get subjects for offline use
     */
    suspend fun getOfflineSubjects(): List<Subject> = 
        subjectDao.getOfflineSubjects()
    
    /**
     * Update subject availability
     */
    suspend fun updateSubjectAvailability(subjectId: String, isAvailable: Boolean): Result<Unit> {
        return try {
            subjectDao.updateSubjectAvailability(subjectId, isAvailable)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Get subject progress for user
     */
    suspend fun getSubjectProgress(userId: String, subjectId: String): Flow<SubjectProgress> {
        // This would typically come from a progress repository
        // For now, return empty progress
        return kotlinx.coroutines.flow.flowOf(SubjectProgress())
    }
}

/**
 * Data class for subject progress tracking
 */
data class SubjectProgress(
    val subjectId: String = "",
    val notesRead: Int = 0,
    val quizzesAttempted: Int = 0,
    val quizzesPassed: Int = 0,
    val topicsCompleted: Int = 0,
    val totalTopics: Int = 0,
    val averageScore: Float = 0f,
    val lastStudyDate: Long = 0
)