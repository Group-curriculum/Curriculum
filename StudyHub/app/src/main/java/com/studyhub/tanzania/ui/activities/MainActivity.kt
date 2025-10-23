package com.studyhub.tanzania.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.studyhub.tanzania.ui.navigation.StudyHubNavigation
import com.studyhub.tanzania.ui.theme.StudyHubTheme
import com.studyhub.tanzania.ui.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Main Activity for Study Hub app
 */
class MainActivity : ComponentActivity() {
    
    private val viewModel: MainViewModel by viewModel()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            StudyHubTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StudyHubApp(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun StudyHubApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.checkAuthStatus()
    }
    
    StudyHubNavigation(
        navController = navController,
        isUserAuthenticated = uiState.isUserAuthenticated,
        currentUser = uiState.currentUser
    )
}