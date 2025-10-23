package com.studyhub.tz.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.studyhub.tz.R
import com.studyhub.tz.databinding.FragmentHomeBinding
import com.studyhub.tz.ui.viewmodel.NoteViewModel
import com.studyhub.tz.ui.viewmodel.QuizViewModel
import com.studyhub.tz.ui.viewmodel.SubjectViewModel

/**
 * Home fragment showing overview and quick access
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private val subjectViewModel: SubjectViewModel by viewModels()
    private val noteViewModel: NoteViewModel by viewModels()
    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        observeData()
    }
    
    private fun setupUI() {
        // Setup welcome message
        val currentUser = FirebaseAuth.getInstance().currentUser
        val displayName = currentUser?.displayName ?: "Student"
        binding.tvWelcome.text = getString(R.string.welcome, displayName)
        
        // Setup RecyclerViews
        binding.rvPopularSubjects.layoutManager = 
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        
        binding.rvRecentNotes.layoutManager = LinearLayoutManager(context)
        
        binding.rvQuickQuizzes.layoutManager = 
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
    
    private fun observeData() {
        // Observe popular subjects
        subjectViewModel.getPopularSubjects(6).observe(viewLifecycleOwner) { subjects ->
            // Setup adapter for popular subjects
            // TODO: Create and set adapter
        }
        
        // Observe recent notes
        noteViewModel.getPopularNotes(5).observe(viewLifecycleOwner) { notes ->
            // Setup adapter for recent notes
            // TODO: Create and set adapter
        }
        
        // Observe quick quizzes
        quizViewModel.getPopularQuizzes(4).observe(viewLifecycleOwner) { quizzes ->
            // Setup adapter for quick quizzes
            // TODO: Create and set adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
