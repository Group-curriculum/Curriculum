package com.studyhub.tz.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.studyhub.tz.data.model.PastPaper
import com.studyhub.tz.databinding.ItemPastPaperCardBinding

/**
 * Adapter for displaying past papers in RecyclerView
 */
class PastPaperAdapter(
    private val onDownloadClick: (PastPaper) -> Unit,
    private val onViewSolutionsClick: (PastPaper) -> Unit
) : ListAdapter<PastPaper, PastPaperAdapter.PastPaperViewHolder>(PastPaperDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastPaperViewHolder {
        val binding = ItemPastPaperCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PastPaperViewHolder(binding, onDownloadClick, onViewSolutionsClick)
    }

    override fun onBindViewHolder(holder: PastPaperViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PastPaperViewHolder(
        private val binding: ItemPastPaperCardBinding,
        private val onDownloadClick: (PastPaper) -> Unit,
        private val onViewSolutionsClick: (PastPaper) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(paper: PastPaper) {
            binding.apply {
                tvPaperTitle.text = paper.title
                tvYear.text = "Year: ${paper.year}"
                tvPaperNumber.text = "Paper ${paper.paperNumber}"
                tvMarks.text = "${paper.totalMarks} Marks • ${paper.duration / 60} Hours"
                
                btnDownload.setOnClickListener {
                    onDownloadClick(paper)
                }
                
                btnViewSolutions.setOnClickListener {
                    onViewSolutionsClick(paper)
                }
                
                // Disable solutions button if not available
                btnViewSolutions.isEnabled = paper.hasSolutions
            }
        }
    }

    class PastPaperDiffCallback : DiffUtil.ItemCallback<PastPaper>() {
        override fun areItemsTheSame(oldItem: PastPaper, newItem: PastPaper): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PastPaper, newItem: PastPaper): Boolean {
            return oldItem == newItem
        }
    }
}
