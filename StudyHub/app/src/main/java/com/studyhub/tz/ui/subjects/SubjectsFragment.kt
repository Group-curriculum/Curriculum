package com.studyhub.tz.ui.subjects

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.studyhub.tz.databinding.FragmentSubjectsBinding
import com.studyhub.tz.data.model.EducationLevel
import com.studyhub.tz.ui.viewmodel.SubjectViewModel

/**
 * Subjects fragment showing O-Level and A-Level subjects
 */
class SubjectsFragment : Fragment() {

    private var _binding: FragmentSubjectsBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: SubjectViewModel by viewModels()
    private var currentLevel = EducationLevel.O_LEVEL

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubjectsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        observeData()
    }
    
    private fun setupUI() {
        // Setup RecyclerView with grid layout
        binding.rvSubjects.layoutManager = GridLayoutManager(context, 2)
        
        // Setup tab selection
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                currentLevel = when (tab?.position) {
                    1 -> EducationLevel.A_LEVEL
                    else -> EducationLevel.O_LEVEL
                }
                loadSubjects()
            }
            
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        
        // Setup swipe to refresh
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.syncSubjects()
        }
    }
    
    private fun observeData() {
        // Observe loading state
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.swipeRefresh.isRefreshing = isLoading
        }
        
        // Load initial subjects
        loadSubjects()
    }
    
    private fun loadSubjects() {
        viewModel.getSubjectsByLevel(currentLevel).observe(viewLifecycleOwner) { subjects ->
            // Setup adapter with subjects
            // TODO: Create and set adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
