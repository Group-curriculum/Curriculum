package com.studyhub.tz.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.studyhub.tz.data.local.StudyHubDatabase
import com.studyhub.tz.data.model.Note
import com.studyhub.tz.data.repository.NoteRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for notes
 */
class NoteViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: NoteRepository
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    
    init {
        val database = StudyHubDatabase.getDatabase(application)
        repository = NoteRepository(
            FirebaseFirestore.getInstance(),
            database.noteDao()
        )
    }
    
    fun getNotesBySubject(subjectId: String): LiveData<List<Note>> {
        syncNotesForSubject(subjectId)
        return repository.getNotesBySubject(subjectId)
    }
    
    fun getNoteById(noteId: String): LiveData<Note?> {
        return repository.getNoteById(noteId)
    }
    
    fun getBookmarkedNotes(): LiveData<List<Note>> {
        return repository.getBookmarkedNotes()
    }
    
    fun getPopularNotes(limit: Int = 10): LiveData<List<Note>> {
        return repository.getPopularNotes(limit)
    }
    
    fun syncNotesForSubject(subjectId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            val result = repository.syncNotesForSubject(subjectId)
            result.onSuccess {
                _isLoading.value = false
            }.onFailure { exception ->
                _isLoading.value = false
                _error.value = exception.message
            }
        }
    }
    
    fun toggleBookmark(noteId: String) {
        viewModelScope.launch {
            repository.toggleBookmark(noteId)
        }
    }
    
    fun incrementReadCount(noteId: String) {
        viewModelScope.launch {
            repository.incrementReadCount(noteId)
        }
    }
}
