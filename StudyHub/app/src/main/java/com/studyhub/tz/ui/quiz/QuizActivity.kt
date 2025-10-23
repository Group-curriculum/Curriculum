package com.studyhub.tz.ui.quiz

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.studyhub.tz.R
import com.studyhub.tz.data.model.Question
import com.studyhub.tz.data.model.Quiz
import com.studyhub.tz.data.model.QuizAttempt
import com.studyhub.tz.databinding.ActivityQuizBinding
import com.studyhub.tz.ui.viewmodel.QuizViewModel
import java.util.*

/**
 * Activity for taking quizzes
 * Displays questions one at a time with navigation and timer
 */
class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private val viewModel: QuizViewModel by viewModels()
    
    private var quiz: Quiz? = null
    private var currentQuestionIndex = 0
    private val userAnswers = mutableMapOf<String, String>()
    private var timer: CountDownTimer? = null
    private var startTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        val quizId = intent.getStringExtra("QUIZ_ID") ?: ""
        startTime = System.currentTimeMillis()
        
        loadQuiz(quizId)
        setupNavigation()
    }
    
    private fun loadQuiz(quizId: String) {
        viewModel.getQuizById(quizId).observe(this) { loadedQuiz ->
            loadedQuiz?.let {
                quiz = it
                supportActionBar?.title = it.title
                startTimer(it.duration * 60 * 1000L) // Convert minutes to milliseconds
                displayQuestion(0)
            }
        }
    }
    
    private fun startTimer(durationMillis: Long) {
        timer = object : CountDownTimer(durationMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = (millisUntilFinished / 1000) % 60
                binding.tvTimer.text = String.format("%02d:%02d", minutes, seconds)
            }
            
            override fun onFinish() {
                binding.tvTimer.text = "00:00"
                submitQuiz()
            }
        }.start()
    }
    
    private fun displayQuestion(index: Int) {
        val currentQuiz = quiz ?: return
        if (index < 0 || index >= currentQuiz.questions.size) return
        
        currentQuestionIndex = index
        val question = currentQuiz.questions[index]
        
        // Update progress
        binding.tvQuestionProgress.text = getString(
            R.string.question_number,
            index + 1,
            currentQuiz.questions.size
        )
        
        // Display question
        binding.tvQuestion.text = question.questionText
        
        // Setup options
        binding.rgOptions.removeAllViews()
        question.options.forEachIndexed { optionIndex, option ->
            val radioButton = RadioButton(this).apply {
                id = View.generateViewId()
                text = option
                textSize = 16f
                setPadding(16, 16, 16, 16)
                
                // Check if this option was previously selected
                if (userAnswers[question.id] == option) {
                    isChecked = true
                }
            }
            binding.rgOptions.addView(radioButton)
        }
        
        // Save answer when option selected
        binding.rgOptions.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            selectedRadioButton?.let {
                userAnswers[question.id] = it.text.toString()
            }
        }
        
        // Update button states
        binding.btnPrevious.isEnabled = index > 0
        binding.btnNext.text = if (index == currentQuiz.questions.size - 1) {
            getString(R.string.submit_quiz)
        } else {
            getString(R.string.next_question)
        }
    }
    
    private fun setupNavigation() {
        binding.btnPrevious.setOnClickListener {
            if (currentQuestionIndex > 0) {
                displayQuestion(currentQuestionIndex - 1)
            }
        }
        
        binding.btnNext.setOnClickListener {
            val currentQuiz = quiz ?: return@setOnClickListener
            
            if (currentQuestionIndex < currentQuiz.questions.size - 1) {
                displayQuestion(currentQuestionIndex + 1)
            } else {
                // Last question - show submit confirmation
                showSubmitConfirmation()
            }
        }
    }
    
    private fun showSubmitConfirmation() {
        val currentQuiz = quiz ?: return
        val answeredCount = userAnswers.size
        val totalQuestions = currentQuiz.questions.size
        
        AlertDialog.Builder(this)
            .setTitle("Submit Quiz?")
            .setMessage("You have answered $answeredCount out of $totalQuestions questions.\n\nAre you sure you want to submit?")
            .setPositiveButton("Submit") { _, _ ->
                submitQuiz()
            }
            .setNegativeButton("Review") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    
    private fun submitQuiz() {
        timer?.cancel()
        
        val currentQuiz = quiz ?: return
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        
        // Calculate score
        var correctAnswers = 0
        currentQuiz.questions.forEach { question ->
            val userAnswer = userAnswers[question.id]
            if (userAnswer == question.correctAnswer) {
                correctAnswers++
            }
        }
        
        val score = (correctAnswers.toFloat() / currentQuiz.questions.size) * 100
        val timeTaken = System.currentTimeMillis() - startTime
        val isPassed = score >= currentQuiz.passingScore
        
        // Create quiz attempt
        val attempt = QuizAttempt(
            id = UUID.randomUUID().toString(),
            userId = userId,
            quizId = currentQuiz.id,
            subjectId = currentQuiz.subjectId,
            score = score,
            correctAnswers = correctAnswers,
            totalQuestions = currentQuiz.questions.size,
            timeTaken = timeTaken,
            answers = userAnswers,
            isPassed = isPassed,
            completedAt = System.currentTimeMillis()
        )
        
        // Submit to repository
        viewModel.submitQuizAttempt(attempt)
        
        // Navigate to results
        val intent = Intent(this, QuizResultActivity::class.java).apply {
            putExtra("ATTEMPT_ID", attempt.id)
            putExtra("QUIZ_ID", currentQuiz.id)
            putExtra("SCORE", score)
            putExtra("CORRECT_ANSWERS", correctAnswers)
            putExtra("TOTAL_QUESTIONS", currentQuiz.questions.size)
            putExtra("IS_PASSED", isPassed)
        }
        startActivity(intent)
        finish()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        // Show confirmation before exiting
        AlertDialog.Builder(this)
            .setTitle("Exit Quiz?")
            .setMessage("Your progress will be lost. Are you sure you want to exit?")
            .setPositiveButton("Exit") { _, _ ->
                timer?.cancel()
                finish()
            }
            .setNegativeButton("Continue") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
        return true
    }
    
    override fun onBackPressed() {
        onSupportNavigateUp()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}
