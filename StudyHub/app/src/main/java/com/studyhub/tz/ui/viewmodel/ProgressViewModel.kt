package com.studyhub.tz.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.studyhub.tz.data.local.StudyHubDatabase
import com.studyhub.tz.data.model.StudySession
import com.studyhub.tz.data.model.UserProgress
import com.studyhub.tz.data.repository.ProgressRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for user progress tracking
 */
class ProgressViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: ProgressRepository
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    
    init {
        val database = StudyHubDatabase.getDatabase(application)
        repository = ProgressRepository(
            FirebaseFirestore.getInstance(),
            database.userProgressDao(),
            database.studySessionDao()
        )
    }
    
    fun getAllProgressByUser(userId: String): LiveData<List<UserProgress>> {
        syncProgress(userId)
        return repository.getAllProgressByUser(userId)
    }
    
    fun getProgressBySubject(userId: String, subjectId: String): LiveData<UserProgress?> {
        return repository.getProgressBySubject(userId, subjectId)
    }
    
    fun getTopSubjects(userId: String, limit: Int = 5): LiveData<List<UserProgress>> {
        return repository.getTopSubjects(userId, limit)
    }
    
    fun updateProgress(progress: UserProgress) {
        viewModelScope.launch {
            repository.updateProgress(progress)
        }
    }
    
    fun syncProgress(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            val result = repository.syncProgress(userId)
            result.onSuccess {
                _isLoading.value = false
            }.onFailure { exception ->
                _isLoading.value = false
                _error.value = exception.message
            }
        }
    }
    
    fun getAllSessionsByUser(userId: String): LiveData<List<StudySession>> {
        return repository.getAllSessionsByUser(userId)
    }
    
    fun getTotalStudyTime(userId: String): LiveData<Long?> {
        return repository.getTotalStudyTime(userId)
    }
    
    fun getStudyTimeBySubject(userId: String, subjectId: String): LiveData<Long?> {
        return repository.getStudyTimeBySubject(userId, subjectId)
    }
    
    fun recordStudySession(session: StudySession) {
        viewModelScope.launch {
            repository.recordStudySession(session)
        }
    }
}
