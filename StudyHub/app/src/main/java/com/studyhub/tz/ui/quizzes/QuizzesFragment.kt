package com.studyhub.tz.ui.quizzes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.studyhub.tz.databinding.FragmentQuizzesBinding
import com.studyhub.tz.data.model.QuizType
import com.studyhub.tz.ui.viewmodel.QuizViewModel

/**
 * Quizzes fragment showing available quizzes
 */
class QuizzesFragment : Fragment() {

    private var _binding: FragmentQuizzesBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: QuizViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizzesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        observeData()
    }
    
    private fun setupUI() {
        // Setup RecyclerView
        binding.rvQuizzes.layoutManager = LinearLayoutManager(context)
        
        // Setup chip filters
        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.chipPractice.id -> loadQuizzesByType(QuizType.PRACTICE)
                binding.chipMockExam.id -> loadQuizzesByType(QuizType.MOCK_EXAM)
                binding.chipTopicTest.id -> loadQuizzesByType(QuizType.TOPIC_TEST)
                else -> loadAllQuizzes()
            }
        }
        
        // Setup swipe to refresh
        binding.swipeRefresh.setOnRefreshListener {
            loadAllQuizzes()
        }
    }
    
    private fun observeData() {
        // Observe loading state
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.swipeRefresh.isRefreshing = isLoading
        }
        
        // Load initial quizzes
        loadAllQuizzes()
    }
    
    private fun loadAllQuizzes() {
        val adapter = com.studyhub.tz.ui.adapters.QuizAdapter { quiz ->
            // Handle quiz click - navigate to quiz activity
            val intent = android.content.Intent(requireContext(), com.studyhub.tz.ui.quiz.QuizActivity::class.java)
            intent.putExtra("QUIZ_ID", quiz.id)
            startActivity(intent)
        }
        binding.rvQuizzes.adapter = adapter
        
        viewModel.getPopularQuizzes(50).observe(viewLifecycleOwner) { quizzes ->
            adapter.submitList(quizzes)
        }
    }
    
    private fun loadQuizzesByType(type: QuizType) {
        val adapter = binding.rvQuizzes.adapter as? com.studyhub.tz.ui.adapters.QuizAdapter
        
        viewModel.getQuizzesByType(type).observe(viewLifecycleOwner) { quizzes ->
            adapter?.submitList(quizzes)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
