package com.studyhub.tz.ui.progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.studyhub.tz.databinding.FragmentProgressBinding
import com.studyhub.tz.ui.viewmodel.ProgressViewModel

/**
 * Progress fragment showing student performance analytics
 */
class ProgressFragment : Fragment() {

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: ProgressViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        observeData()
    }
    
    private fun setupUI() {
        // Setup RecyclerViews
        binding.rvStrongSubjects.layoutManager = LinearLayoutManager(context)
        binding.rvWeakSubjects.layoutManager = LinearLayoutManager(context)
        
        // Setup chart
        setupPerformanceChart()
    }
    
    private fun observeData() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        
        // Setup adapters
        val strongSubjectsAdapter = com.studyhub.tz.ui.adapters.ProgressSubjectAdapter()
        binding.rvStrongSubjects.adapter = strongSubjectsAdapter
        
        val weakSubjectsAdapter = com.studyhub.tz.ui.adapters.ProgressSubjectAdapter()
        binding.rvWeakSubjects.adapter = weakSubjectsAdapter
        
        // Observe all progress
        viewModel.getAllProgressByUser(userId).observe(viewLifecycleOwner) { progressList ->
            updateStatistics(progressList)
            
            // Separate into strong and weak subjects
            val sorted = progressList.sortedByDescending { it.averageScore }
            val strong = sorted.take(3)
            val weak = sorted.reversed().take(2)
            
            strongSubjectsAdapter.submitList(strong)
            weakSubjectsAdapter.submitList(weak)
        }
        
        // Observe top subjects
        viewModel.getTopSubjects(userId, 5).observe(viewLifecycleOwner) { topSubjects ->
            strongSubjectsAdapter.submitList(topSubjects)
        }
        
        // Observe total study time
        viewModel.getTotalStudyTime(userId).observe(viewLifecycleOwner) { totalTime ->
            totalTime?.let {
                val hours = it / (1000 * 60 * 60)
                binding.tvStudyTime.text = "${hours}h"
            }
        }
    }
    
    private fun updateStatistics(progressList: List<com.studyhub.tz.data.model.UserProgress>) {
        val totalQuizzes = progressList.sumOf { it.quizzesTaken }
        val averageScore = if (progressList.isNotEmpty()) {
            progressList.map { it.averageScore }.average().toFloat()
        } else 0f
        
        binding.tvQuizzesTaken.text = totalQuizzes.toString()
        binding.tvAverageScore.text = "${averageScore.toInt()}%"
    }
    
    private fun setupPerformanceChart() {
        // Configure chart
        binding.chartPerformance.apply {
            description.isEnabled = false
            setTouchEnabled(true)
            setPinchZoom(false)
            // TODO: Add chart data
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
