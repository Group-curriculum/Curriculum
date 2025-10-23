package com.studyhub.tz.ui.quiz

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.studyhub.tz.R
import com.studyhub.tz.databinding.ActivityQuizResultBinding

/**
 * Activity to display quiz results with feedback and options
 */
class QuizResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        displayResults()
        setupButtons()
    }
    
    private fun displayResults() {
        val score = intent.getFloatExtra("SCORE", 0f)
        val correctAnswers = intent.getIntExtra("CORRECT_ANSWERS", 0)
        val totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 0)
        val isPassed = intent.getBooleanExtra("IS_PASSED", false)
        
        // Display score
        binding.tvScore.text = "${score.toInt()}%"
        binding.tvCorrectAnswers.text = getString(R.string.correct_answers, correctAnswers, totalQuestions)
        
        // Display pass/fail status
        if (isPassed) {
            binding.tvResultStatus.text = getString(R.string.passed)
            binding.tvResultStatus.setTextColor(ContextCompat.getColor(this, R.color.success))
            binding.ivResultIcon.setColorFilter(ContextCompat.getColor(this, R.color.success))
        } else {
            binding.tvResultStatus.text = getString(R.string.failed)
            binding.tvResultStatus.setTextColor(ContextCompat.getColor(this, R.color.error))
            binding.ivResultIcon.setColorFilter(ContextCompat.getColor(this, R.color.error))
        }
        
        // Generate feedback
        val feedback = when {
            score >= 90 -> "Excellent work! You have mastered this topic. Keep up the great work!"
            score >= 75 -> "Great job! You have a strong understanding of this topic."
            score >= 60 -> "Good effort! Review the areas you got wrong and try again."
            score >= 50 -> "You passed! But there's room for improvement. Review the material and practice more."
            else -> "Don't worry! Review the material carefully and try again. You'll do better next time!"
        }
        
        binding.tvFeedback.text = feedback
    }
    
    private fun setupButtons() {
        val quizId = intent.getStringExtra("QUIZ_ID") ?: ""
        
        binding.btnViewSolutions.setOnClickListener {
            // TODO: Navigate to solutions view
            android.widget.Toast.makeText(
                this,
                "Solutions view coming soon",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
        
        binding.btnRetakeQuiz.setOnClickListener {
            // Retake the same quiz
            val intent = Intent(this, QuizActivity::class.java).apply {
                putExtra("QUIZ_ID", quizId)
            }
            startActivity(intent)
            finish()
        }
        
        binding.btnFinish.setOnClickListener {
            finish()
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
