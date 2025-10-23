package com.studyhub.tz.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.studyhub.tz.ui.auth.AuthActivity

/**
 * Splash screen activity
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val splashDuration = 2000L // 2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        Handler(Looper.getMainLooper()).postDelayed({
            checkAuthentication()
        }, splashDuration)
    }
    
    private fun checkAuthentication() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        
        val intent = if (currentUser != null) {
            // User is signed in, go to main activity
            Intent(this, MainActivity::class.java)
        } else {
            // No user is signed in, go to auth activity
            Intent(this, AuthActivity::class.java)
        }
        
        startActivity(intent)
        finish()
    }
}
