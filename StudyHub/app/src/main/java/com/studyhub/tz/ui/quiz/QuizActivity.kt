package com.studyhub.tz.ui.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.studyhub.tz.databinding.ActivityQuizBinding

/**
 * Activity for taking quizzes
 * Displays questions one at a time with navigation
 */
class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        // TODO: Implement quiz logic
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
