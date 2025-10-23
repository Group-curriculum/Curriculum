package com.studyhub.tz.data.repository

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.studyhub.tz.data.local.dao.SubjectDao
import com.studyhub.tz.data.model.EducationLevel
import com.studyhub.tz.data.model.Subject
import kotlinx.coroutines.tasks.await

/**
 * Repository for subject data with Firebase sync and offline caching
 */
class SubjectRepository(
    private val firestore: FirebaseFirestore,
    private val subjectDao: SubjectDao
) {
    companion object {
        private const val SUBJECTS_COLLECTION = "subjects"
    }

    /**
     * Get all subjects from local database
     */
    fun getAllSubjects(): LiveData<List<Subject>> {
        return subjectDao.getAllSubjects()
    }

    /**
     * Get subjects by education level
     */
    fun getSubjectsByLevel(level: EducationLevel): LiveData<List<Subject>> {
        return subjectDao.getSubjectsByLevel(level)
    }

    /**
     * Get subject by ID
     */
    fun getSubjectById(subjectId: String): LiveData<Subject?> {
        return subjectDao.getSubjectById(subjectId)
    }

    /**
     * Get popular subjects
     */
    fun getPopularSubjects(limit: Int = 6): LiveData<List<Subject>> {
        return subjectDao.getPopularSubjects(limit)
    }

    /**
     * Sync subjects from Firebase to local database
     */
    suspend fun syncSubjects(): Result<Unit> {
        return try {
            val snapshot = firestore.collection(SUBJECTS_COLLECTION)
                .get()
                .await()
            
            val subjects = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Subject::class.java)?.copy(id = doc.id)
            }
            
            subjectDao.insertSubjects(subjects)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Fetch subjects by level from Firebase
     */
    suspend fun fetchSubjectsByLevel(level: EducationLevel): Result<List<Subject>> {
        return try {
            val snapshot = firestore.collection(SUBJECTS_COLLECTION)
                .whereEqualTo("level", level.name)
                .get()
                .await()
            
            val subjects = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Subject::class.java)?.copy(id = doc.id)
            }
            
            subjectDao.insertSubjects(subjects)
            Result.success(subjects)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
