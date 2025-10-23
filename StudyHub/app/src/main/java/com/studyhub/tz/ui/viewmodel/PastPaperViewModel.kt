package com.studyhub.tz.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.studyhub.tz.data.local.StudyHubDatabase
import com.studyhub.tz.data.model.PastPaper
import com.studyhub.tz.data.repository.PastPaperRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for past papers
 */
class PastPaperViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: PastPaperRepository
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    
    init {
        val database = StudyHubDatabase.getDatabase(application)
        repository = PastPaperRepository(
            FirebaseFirestore.getInstance(),
            database.pastPaperDao()
        )
    }
    
    fun getPastPapersBySubject(subjectId: String): LiveData<List<PastPaper>> {
        syncPastPapersForSubject(subjectId)
        return repository.getPastPapersBySubject(subjectId)
    }
    
    fun getPastPaperById(paperId: String): LiveData<PastPaper?> {
        return repository.getPastPaperById(paperId)
    }
    
    fun getPastPapersByYear(year: Int): LiveData<List<PastPaper>> {
        return repository.getPastPapersByYear(year)
    }
    
    fun getBookmarkedPastPapers(): LiveData<List<PastPaper>> {
        return repository.getBookmarkedPastPapers()
    }
    
    fun getAvailableYears(): LiveData<List<Int>> {
        return repository.getAvailableYears()
    }
    
    fun syncPastPapersForSubject(subjectId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            val result = repository.syncPastPapersForSubject(subjectId)
            result.onSuccess {
                _isLoading.value = false
            }.onFailure { exception ->
                _isLoading.value = false
                _error.value = exception.message
            }
        }
    }
    
    fun toggleBookmark(paperId: String) {
        viewModelScope.launch {
            repository.toggleBookmark(paperId)
        }
    }
    
    fun incrementDownloadCount(paperId: String) {
        viewModelScope.launch {
            repository.incrementDownloadCount(paperId)
        }
    }
}
