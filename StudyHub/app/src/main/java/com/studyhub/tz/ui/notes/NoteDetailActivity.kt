package com.studyhub.tz.ui.notes

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.studyhub.tz.R
import com.studyhub.tz.data.model.Difficulty
import com.studyhub.tz.data.model.Note
import com.studyhub.tz.databinding.ActivityNoteDetailBinding
import com.studyhub.tz.ui.viewmodel.NoteViewModel

/**
 * Activity to display detailed note content with bookmarking
 */
class NoteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteDetailBinding
    private val viewModel: NoteViewModel by viewModels()
    private var currentNote: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        val noteId = intent.getStringExtra("NOTE_ID") ?: ""
        loadNote(noteId)
        setupBookmark()
    }
    
    private fun loadNote(noteId: String) {
        viewModel.getNoteById(noteId).observe(this) { note ->
            note?.let {
                currentNote = it
                displayNote(it)
                updateBookmarkIcon(it.isBookmarked)
                
                // Increment read count
                viewModel.incrementReadCount(noteId)
            }
        }
    }
    
    private fun displayNote(note: Note) {
        binding.apply {
            // Set title
            tvNoteTitle.text = note.title
            supportActionBar?.title = note.title
            
            // Set info
            tvReadTime.text = "${note.estimatedReadTime} min read"
            
            // Set difficulty badge
            val (difficultyText, difficultyColor) = when (note.difficulty) {
                Difficulty.EASY -> Pair("Easy", R.color.difficulty_easy)
                Difficulty.MEDIUM -> Pair("Medium", R.color.difficulty_medium)
                Difficulty.HARD -> Pair("Hard", R.color.difficulty_hard)
            }
            tvDifficulty.text = difficultyText
            tvDifficulty.setBackgroundColor(
                ContextCompat.getColor(this@NoteDetailActivity, difficultyColor)
            )
            
            // Set summary
            tvSummary.text = note.summary
            
            // Set content
            tvContent.text = note.content
            
            // Set key points
            if (note.keyPoints.isNotEmpty()) {
                cardKeyPoints.visibility = View.VISIBLE
                tvKeyPoints.text = note.keyPoints.joinToString("\n") { "• $it" }
            } else {
                cardKeyPoints.visibility = View.GONE
            }
        }
    }
    
    private fun setupBookmark() {
        binding.fabBookmark.setOnClickListener {
            currentNote?.let { note ->
                viewModel.toggleBookmark(note.id)
                updateBookmarkIcon(!note.isBookmarked)
            }
        }
    }
    
    private fun updateBookmarkIcon(isBookmarked: Boolean) {
        val icon = if (isBookmarked) {
            android.R.drawable.btn_star_big_on
        } else {
            android.R.drawable.btn_star_big_off
        }
        binding.fabBookmark.setImageResource(icon)
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
