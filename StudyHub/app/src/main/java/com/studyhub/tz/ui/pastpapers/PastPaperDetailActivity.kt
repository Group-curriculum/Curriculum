package com.studyhub.tz.ui.pastpapers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studyhub.tz.data.model.PaperQuestion
import com.studyhub.tz.data.model.PastPaper
import com.studyhub.tz.databinding.ActivityPastPaperDetailBinding
import com.studyhub.tz.databinding.ItemPaperQuestionBinding
import com.studyhub.tz.ui.viewmodel.PastPaperViewModel

/**
 * Activity to display past paper details and questions with solutions
 */
class PastPaperDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPastPaperDetailBinding
    private val viewModel: PastPaperViewModel by viewModels()
    private var currentPaper: PastPaper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPastPaperDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        val paperId = intent.getStringExtra("PAPER_ID") ?: ""
        setupRecyclerView()
        loadPaper(paperId)
        setupButtons()
    }
    
    private fun setupRecyclerView() {
        binding.rvQuestions.layoutManager = LinearLayoutManager(this)
    }
    
    private fun loadPaper(paperId: String) {
        viewModel.getPastPaperById(paperId).observe(this) { paper ->
            paper?.let {
                currentPaper = it
                displayPaper(it)
            }
        }
    }
    
    private fun displayPaper(paper: PastPaper) {
        binding.apply {
            tvPaperTitle.text = paper.title
            supportActionBar?.title = paper.title
            tvYear.text = paper.year.toString()
            tvDuration.text = "${paper.duration / 60} Hours"
            tvTotalMarks.text = paper.totalMarks.toString()
            
            // Setup questions adapter
            val adapter = PaperQuestionAdapter(paper.questions)
            rvQuestions.adapter = adapter
            
            // Update bookmark button
            updateBookmarkButton(paper.isBookmarked)
        }
    }
    
    private fun setupButtons() {
        binding.btnDownload.setOnClickListener {
            currentPaper?.let { paper ->
                // Download paper
                viewModel.incrementDownloadCount(paper.id)
                Toast.makeText(
                    this,
                    "Downloading ${paper.title}...",
                    Toast.LENGTH_SHORT
                ).show()
                // TODO: Implement actual PDF download
            }
        }
        
        binding.btnBookmark.setOnClickListener {
            currentPaper?.let { paper ->
                viewModel.toggleBookmark(paper.id)
                updateBookmarkButton(!paper.isBookmarked)
            }
        }
    }
    
    private fun updateBookmarkButton(isBookmarked: Boolean) {
        binding.btnBookmark.text = if (isBookmarked) "Bookmarked" else "Bookmark"
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    
    /**
     * Adapter for displaying paper questions
     */
    class PaperQuestionAdapter(
        private val questions: List<PaperQuestion>
    ) : RecyclerView.Adapter<PaperQuestionAdapter.QuestionViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
            val binding = ItemPaperQuestionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return QuestionViewHolder(binding)
        }

        override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
            holder.bind(questions[position])
        }

        override fun getItemCount() = questions.size

        class QuestionViewHolder(
            private val binding: ItemPaperQuestionBinding
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(question: PaperQuestion) {
                binding.apply {
                    tvQuestionNumber.text = question.questionNumber
                    tvQuestionText.text = question.questionText
                    tvMarks.text = "[${question.marks} marks]"
                    
                    // Show/hide solution
                    var isSolutionVisible = false
                    btnViewSolution.setOnClickListener {
                        isSolutionVisible = !isSolutionVisible
                        if (isSolutionVisible) {
                            tvSolution.visibility = View.VISIBLE
                            tvSolution.text = "Solution: ${question.solution}\n\n${question.explanation}"
                            btnViewSolution.text = "Hide Solution"
                        } else {
                            tvSolution.visibility = View.GONE
                            btnViewSolution.text = "View Solution"
                        }
                    }
                    
                    // Hide button if no solution available
                    if (question.solution.isEmpty()) {
                        btnViewSolution.visibility = View.GONE
                    }
                }
            }
        }
    }
}
