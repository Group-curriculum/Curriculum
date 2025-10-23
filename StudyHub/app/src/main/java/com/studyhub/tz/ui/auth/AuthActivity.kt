package com.studyhub.tz.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.studyhub.tz.R
import com.studyhub.tz.databinding.ActivityAuthBinding
import com.studyhub.tz.ui.MainActivity
import com.studyhub.tz.ui.viewmodel.AuthState
import com.studyhub.tz.ui.viewmodel.AuthViewModel

/**
 * Authentication activity for sign in and sign up
 */
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val viewModel: AuthViewModel by viewModels()

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            account?.let {
                viewModel.signInWithGoogle(it)
            }
        } catch (e: ApiException) {
            Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        observeAuthState()
    }
    
    private fun setupUI() {
        // Sign In button
        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            
            if (validateInput(email, password)) {
                viewModel.signInWithEmail(email, password)
            }
        }
        
        // Google Sign In button
        binding.btnGoogleSignIn.setOnClickListener {
            val signInIntent = viewModel.getGoogleSignInClient().signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
        
        // Forgot Password
        binding.tvForgotPassword.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            if (email.isNotEmpty()) {
                viewModel.resetPassword(email)
            } else {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun observeAuthState() {
        viewModel.authState.observe(this) { state ->
            when (state) {
                is AuthState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSignIn.isEnabled = false
                    binding.btnGoogleSignIn.isEnabled = false
                }
                
                is AuthState.Authenticated -> {
                    binding.progressBar.visibility = View.GONE
                    navigateToMain()
                }
                
                is AuthState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignIn.isEnabled = true
                    binding.btnGoogleSignIn.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
                
                is AuthState.PasswordResetSent -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Password reset email sent. Check your inbox.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                
                else -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignIn.isEnabled = true
                    binding.btnGoogleSignIn.isEnabled = true
                }
            }
        }
    }
    
    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.etEmail.error = "Email is required"
            return false
        }
        
        if (password.isEmpty()) {
            binding.etPassword.error = "Password is required"
            return false
        }
        
        if (password.length < 6) {
            binding.etPassword.error = "Password must be at least 6 characters"
            return false
        }
        
        return true
    }
    
    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
