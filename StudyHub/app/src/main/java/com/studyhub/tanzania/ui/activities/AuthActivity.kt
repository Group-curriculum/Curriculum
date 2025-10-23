package com.studyhub.tanzania.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.studyhub.tanzania.ui.screens.auth.AuthScreen
import com.studyhub.tanzania.ui.theme.StudyHubTheme
import com.studyhub.tanzania.ui.viewmodels.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Authentication Activity for Study Hub app
 */
class AuthActivity : ComponentActivity() {
    
    private val viewModel: AuthViewModel by viewModel()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            StudyHubTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AuthScreen(
                        viewModel = viewModel,
                        onAuthSuccess = {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    )
                }
            }
        }
    }
}