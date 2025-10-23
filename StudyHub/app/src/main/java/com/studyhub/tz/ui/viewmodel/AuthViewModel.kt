package com.studyhub.tz.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.studyhub.tz.data.local.StudyHubDatabase
import com.studyhub.tz.data.model.User
import com.studyhub.tz.data.repository.AuthRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for authentication operations
 */
class AuthViewModel(application: Application) : AndroidViewModel(application) {
    
    private val authRepository: AuthRepository
    
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState
    
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser
    
    init {
        val database = StudyHubDatabase.getDatabase(application)
        authRepository = AuthRepository(
            application.applicationContext,
            FirebaseAuth.getInstance(),
            FirebaseFirestore.getInstance(),
            database.userDao()
        )
        
        // Check if user is already logged in
        if (authRepository.isUserLoggedIn()) {
            _authState.value = AuthState.Authenticated
        } else {
            _authState.value = AuthState.Unauthenticated
        }
    }
    
    fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.signInWithEmail(email, password)
            
            result.onSuccess { user ->
                _currentUser.value = user
                _authState.value = AuthState.Authenticated
            }.onFailure { exception ->
                _authState.value = AuthState.Error(exception.message ?: "Sign in failed")
            }
        }
    }
    
    fun signUpWithEmail(email: String, password: String, displayName: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.signUpWithEmail(email, password, displayName)
            
            result.onSuccess { user ->
                _currentUser.value = user
                _authState.value = AuthState.Authenticated
            }.onFailure { exception ->
                _authState.value = AuthState.Error(exception.message ?: "Sign up failed")
            }
        }
    }
    
    fun getGoogleSignInClient(): GoogleSignInClient {
        return authRepository.getGoogleSignInClient()
    }
    
    fun signInWithGoogle(account: GoogleSignInAccount) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.signInWithGoogle(account)
            
            result.onSuccess { user ->
                _currentUser.value = user
                _authState.value = AuthState.Authenticated
            }.onFailure { exception ->
                _authState.value = AuthState.Error(exception.message ?: "Google sign in failed")
            }
        }
    }
    
    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
            _currentUser.value = null
            _authState.value = AuthState.Unauthenticated
        }
    }
    
    fun resetPassword(email: String) {
        viewModelScope.launch {
            val result = authRepository.resetPassword(email)
            result.onSuccess {
                _authState.value = AuthState.PasswordResetSent
            }.onFailure { exception ->
                _authState.value = AuthState.Error(exception.message ?: "Password reset failed")
            }
        }
    }
}

sealed class AuthState {
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    object Authenticated : AuthState()
    object PasswordResetSent : AuthState()
    data class Error(val message: String) : AuthState()
}
