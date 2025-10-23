package com.studyhub.tz.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.studyhub.tz.R
import com.studyhub.tz.data.model.Difficulty
import com.studyhub.tz.data.model.Note
import com.studyhub.tz.databinding.ItemNoteCardBinding

/**
 * Adapter for displaying notes in RecyclerView
 */
class NoteAdapter(
    private val onNoteClick: (Note) -> Unit
) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteViewHolder(binding, onNoteClick)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NoteViewHolder(
        private val binding: ItemNoteCardBinding,
        private val onNoteClick: (Note) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.apply {
                tvNoteTitle.text = note.title
                tvNoteSummary.text = note.summary
                tvReadTime.text = "${note.estimatedReadTime} min read"
                
                // Set difficulty badge
                val (difficultyText, difficultyColor) = when (note.difficulty) {
                    Difficulty.EASY -> Pair("Easy", R.color.difficulty_easy)
                    Difficulty.MEDIUM -> Pair("Medium", R.color.difficulty_medium)
                    Difficulty.HARD -> Pair("Hard", R.color.difficulty_hard)
                }
                
                tvDifficulty.text = difficultyText
                tvDifficulty.setBackgroundColor(
                    ContextCompat.getColor(root.context, difficultyColor)
                )
                
                root.setOnClickListener {
                    onNoteClick(note)
                }
            }
        }
    }

    class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
}
