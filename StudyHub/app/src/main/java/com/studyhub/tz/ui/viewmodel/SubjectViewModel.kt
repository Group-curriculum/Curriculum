package com.studyhub.tz.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.studyhub.tz.data.local.StudyHubDatabase
import com.studyhub.tz.data.model.EducationLevel
import com.studyhub.tz.data.model.Subject
import com.studyhub.tz.data.repository.SubjectRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for subjects
 */
class SubjectViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: SubjectRepository
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    
    init {
        val database = StudyHubDatabase.getDatabase(application)
        repository = SubjectRepository(
            FirebaseFirestore.getInstance(),
            database.subjectDao()
        )
        
        // Sync subjects on initialization
        syncSubjects()
    }
    
    fun getAllSubjects(): LiveData<List<Subject>> {
        return repository.getAllSubjects()
    }
    
    fun getSubjectsByLevel(level: EducationLevel): LiveData<List<Subject>> {
        return repository.getSubjectsByLevel(level)
    }
    
    fun getSubjectById(subjectId: String): LiveData<Subject?> {
        return repository.getSubjectById(subjectId)
    }
    
    fun getPopularSubjects(limit: Int = 6): LiveData<List<Subject>> {
        return repository.getPopularSubjects(limit)
    }
    
    fun syncSubjects() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            val result = repository.syncSubjects()
            result.onSuccess {
                _isLoading.value = false
            }.onFailure { exception ->
                _isLoading.value = false
                _error.value = exception.message
            }
        }
    }
    
    fun fetchSubjectsByLevel(level: EducationLevel) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            val result = repository.fetchSubjectsByLevel(level)
            result.onSuccess {
                _isLoading.value = false
            }.onFailure { exception ->
                _isLoading.value = false
                _error.value = exception.message
            }
        }
    }
}
