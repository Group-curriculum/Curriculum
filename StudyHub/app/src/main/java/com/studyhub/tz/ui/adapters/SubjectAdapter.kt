package com.studyhub.tz.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.studyhub.tz.data.model.Subject
import com.studyhub.tz.databinding.ItemSubjectCardBinding

/**
 * Adapter for displaying subjects in RecyclerView
 */
class SubjectAdapter(
    private val onSubjectClick: (Subject) -> Unit
) : ListAdapter<Subject, SubjectAdapter.SubjectViewHolder>(SubjectDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val binding = ItemSubjectCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SubjectViewHolder(binding, onSubjectClick)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SubjectViewHolder(
        private val binding: ItemSubjectCardBinding,
        private val onSubjectClick: (Subject) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(subject: Subject) {
            binding.apply {
                tvSubjectName.text = subject.name
                tvSubjectDescription.text = subject.description
                tvNotesCount.text = "${subject.notesCount} Notes"
                tvQuizzesCount.text = "${subject.quizzesCount} Quizzes"
                
                // Set subject color
                try {
                    root.setCardBackgroundColor(Color.parseColor(subject.color))
                    // Make text white if card has color
                    val textColor = Color.WHITE
                    tvSubjectName.setTextColor(textColor)
                    tvSubjectDescription.setTextColor(textColor)
                } catch (e: Exception) {
                    // Keep default colors if parsing fails
                }
                
                root.setOnClickListener {
                    onSubjectClick(subject)
                }
            }
        }
    }

    class SubjectDiffCallback : DiffUtil.ItemCallback<Subject>() {
        override fun areItemsTheSame(oldItem: Subject, newItem: Subject): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Subject, newItem: Subject): Boolean {
            return oldItem == newItem
        }
    }
}
