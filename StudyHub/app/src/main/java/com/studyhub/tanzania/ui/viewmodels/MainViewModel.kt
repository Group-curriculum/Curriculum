package com.studyhub.tanzania.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyhub.tanzania.data.models.User
import com.studyhub.tanzania.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * ViewModel for MainActivity
 */
class MainViewModel : ViewModel(), KoinComponent {
    
    private val userRepository: UserRepository by inject()
    
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
    
    init {
        checkAuthStatus()
    }
    
    fun checkAuthStatus() {
        viewModelScope.launch {
            if (userRepository.isUserSignedIn()) {
                userRepository.getCurrentUser().collect { user ->
                    _uiState.value = _uiState.value.copy(
                        isUserAuthenticated = true,
                        currentUser = user
                    )
                }
            } else {
                _uiState.value = _uiState.value.copy(
                    isUserAuthenticated = false,
                    currentUser = null
                )
            }
        }
    }
    
    fun signOut() {
        viewModelScope.launch {
            userRepository.signOut()
            _uiState.value = _uiState.value.copy(
                isUserAuthenticated = false,
                currentUser = null
            )
        }
    }
}

data class MainUiState(
    val isUserAuthenticated: Boolean = false,
    val currentUser: User? = null,
    val isLoading: Boolean = false
)