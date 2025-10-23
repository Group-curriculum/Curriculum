package com.studyhub.tz.data.repository

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.studyhub.tz.data.local.dao.StudySessionDao
import com.studyhub.tz.data.local.dao.UserProgressDao
import com.studyhub.tz.data.model.StudySession
import com.studyhub.tz.data.model.UserProgress
import kotlinx.coroutines.tasks.await

/**
 * Repository for user progress tracking
 */
class ProgressRepository(
    private val firestore: FirebaseFirestore,
    private val userProgressDao: UserProgressDao,
    private val studySessionDao: StudySessionDao
) {
    companion object {
        private const val USER_PROGRESS_COLLECTION = "user_progress"
        private const val STUDY_SESSIONS_COLLECTION = "study_sessions"
    }

    /**
     * Get all progress for a user
     */
    fun getAllProgressByUser(userId: String): LiveData<List<UserProgress>> {
        return userProgressDao.getAllProgressByUser(userId)
    }

    /**
     * Get progress for a specific subject
     */
    fun getProgressBySubject(userId: String, subjectId: String): LiveData<UserProgress?> {
        return userProgressDao.getProgressBySubject(userId, subjectId)
    }

    /**
     * Get top performing subjects
     */
    fun getTopSubjects(userId: String, limit: Int = 5): LiveData<List<UserProgress>> {
        return userProgressDao.getTopSubjects(userId, limit)
    }

    /**
     * Update user progress
     */
    suspend fun updateProgress(progress: UserProgress): Result<Unit> {
        return try {
            userProgressDao.insertProgress(progress)
            
            // Sync to Firebase
            firestore.collection(USER_PROGRESS_COLLECTION)
                .document(progress.id)
                .set(progress)
                .await()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Sync progress from Firebase
     */
    suspend fun syncProgress(userId: String): Result<Unit> {
        return try {
            val snapshot = firestore.collection(USER_PROGRESS_COLLECTION)
                .whereEqualTo("userId", userId)
                .get()
                .await()
            
            snapshot.documents.forEach { doc ->
                val progress = doc.toObject(UserProgress::class.java)
                progress?.let {
                    userProgressDao.insertProgress(it.copy(id = doc.id))
                }
            }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get all study sessions for a user
     */
    fun getAllSessionsByUser(userId: String): LiveData<List<StudySession>> {
        return studySessionDao.getAllSessionsByUser(userId)
    }

    /**
     * Get total study time
     */
    fun getTotalStudyTime(userId: String): LiveData<Long?> {
        return studySessionDao.getTotalStudyTime(userId)
    }

    /**
     * Get study time by subject
     */
    fun getStudyTimeBySubject(userId: String, subjectId: String): LiveData<Long?> {
        return studySessionDao.getStudyTimeBySubject(userId, subjectId)
    }

    /**
     * Record study session
     */
    suspend fun recordStudySession(session: StudySession): Result<Unit> {
        return try {
            studySessionDao.insertSession(session)
            
            // Sync to Firebase
            firestore.collection(STUDY_SESSIONS_COLLECTION)
                .document(session.id)
                .set(session)
                .await()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
