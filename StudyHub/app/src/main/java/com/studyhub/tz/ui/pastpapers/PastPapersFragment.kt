package com.studyhub.tz.ui.pastpapers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.studyhub.tz.databinding.FragmentPastPapersBinding
import com.studyhub.tz.ui.viewmodel.PastPaperViewModel

/**
 * Past Papers fragment showing NECTA past examination papers
 */
class PastPapersFragment : Fragment() {

    private var _binding: FragmentPastPapersBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: PastPaperViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPastPapersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        observeData()
    }
    
    private fun setupUI() {
        // Setup RecyclerView
        binding.rvPastPapers.layoutManager = LinearLayoutManager(context)
        
        // Setup swipe to refresh
        binding.swipeRefresh.setOnRefreshListener {
            // Refresh past papers
        }
        
        // Setup filter button
        binding.btnFilter.setOnClickListener {
            // Show year filter dialog
            showYearFilterDialog()
        }
    }
    
    private fun observeData() {
        // Observe loading state
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.swipeRefresh.isRefreshing = isLoading
        }
        
        // Setup adapter
        val adapter = com.studyhub.tz.ui.adapters.PastPaperAdapter(
            onDownloadClick = { paper ->
                // Handle download
                viewModel.incrementDownloadCount(paper.id)
                android.widget.Toast.makeText(
                    requireContext(),
                    "Downloading ${paper.title}...",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            },
            onViewSolutionsClick = { paper ->
                // Handle view solutions
                val intent = android.content.Intent(requireContext(), com.studyhub.tz.ui.pastpapers.PastPaperDetailActivity::class.java)
                intent.putExtra("PAPER_ID", paper.id)
                startActivity(intent)
            }
        )
        binding.rvPastPapers.adapter = adapter
        
        // Observe bookmarked past papers (initial load)
        viewModel.getBookmarkedPastPapers().observe(viewLifecycleOwner) { papers ->
            adapter.submitList(papers)
        }
    }
    
    private fun showYearFilterDialog() {
        // Get available years
        viewModel.getAvailableYears().observe(viewLifecycleOwner) { years ->
            // Show dialog with year selection
            // TODO: Implement year filter dialog
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
