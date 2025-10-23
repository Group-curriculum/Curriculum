package com.studyhub.tz.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.studyhub.tz.data.model.UserProgress
import com.studyhub.tz.databinding.ItemProgressSubjectBinding

/**
 * Adapter for displaying subject progress in RecyclerView
 */
class ProgressSubjectAdapter(
    private val subjectNames: Map<String, String> = emptyMap()
) : ListAdapter<UserProgress, ProgressSubjectAdapter.ProgressViewHolder>(ProgressDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressViewHolder {
        val binding = ItemProgressSubjectBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProgressViewHolder(binding, subjectNames)
    }

    override fun onBindViewHolder(holder: ProgressViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProgressViewHolder(
        private val binding: ItemProgressSubjectBinding,
        private val subjectNames: Map<String, String>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(progress: UserProgress) {
            binding.apply {
                tvSubjectName.text = subjectNames[progress.subjectId] ?: progress.subjectId
                tvQuizInfo.text = "${progress.quizzesTaken} quizzes • ${progress.averageScore.toInt()}% avg score"
                tvScore.text = "${progress.averageScore.toInt()}%"
            }
        }
    }

    class ProgressDiffCallback : DiffUtil.ItemCallback<UserProgress>() {
        override fun areItemsTheSame(oldItem: UserProgress, newItem: UserProgress): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserProgress, newItem: UserProgress): Boolean {
            return oldItem == newItem
        }
    }
}
