package com.studyhub.tz.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.studyhub.tz.data.local.StudyHubDatabase
import com.studyhub.tz.data.model.Quiz
import com.studyhub.tz.data.model.QuizAttempt
import com.studyhub.tz.data.model.QuizType
import com.studyhub.tz.data.repository.QuizRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for quizzes
 */
class QuizViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: QuizRepository
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    
    private val _quizSubmitted = MutableLiveData<Boolean>()
    val quizSubmitted: LiveData<Boolean> = _quizSubmitted
    
    init {
        val database = StudyHubDatabase.getDatabase(application)
        repository = QuizRepository(
            FirebaseFirestore.getInstance(),
            database.quizDao(),
            database.quizAttemptDao()
        )
    }
    
    fun getQuizzesBySubject(subjectId: String): LiveData<List<Quiz>> {
        syncQuizzesForSubject(subjectId)
        return repository.getQuizzesBySubject(subjectId)
    }
    
    fun getQuizById(quizId: String): LiveData<Quiz?> {
        return repository.getQuizById(quizId)
    }
    
    fun getQuizzesByType(type: QuizType): LiveData<List<Quiz>> {
        return repository.getQuizzesByType(type)
    }
    
    fun getPopularQuizzes(limit: Int = 10): LiveData<List<Quiz>> {
        return repository.getPopularQuizzes(limit)
    }
    
    fun syncQuizzesForSubject(subjectId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            val result = repository.syncQuizzesForSubject(subjectId)
            result.onSuccess {
                _isLoading.value = false
            }.onFailure { exception ->
                _isLoading.value = false
                _error.value = exception.message
            }
        }
    }
    
    fun getAttemptsByUser(userId: String): LiveData<List<QuizAttempt>> {
        return repository.getAttemptsByUser(userId)
    }
    
    fun getAttemptsByQuiz(userId: String, quizId: String): LiveData<List<QuizAttempt>> {
        return repository.getAttemptsByQuiz(userId, quizId)
    }
    
    fun submitQuizAttempt(attempt: QuizAttempt) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            val result = repository.submitQuizAttempt(attempt)
            result.onSuccess {
                _isLoading.value = false
                _quizSubmitted.value = true
            }.onFailure { exception ->
                _isLoading.value = false
                _error.value = exception.message
            }
        }
    }
    
    fun getAverageScoreBySubject(userId: String, subjectId: String): LiveData<Float?> {
        return repository.getAverageScoreBySubject(userId, subjectId)
    }
}
