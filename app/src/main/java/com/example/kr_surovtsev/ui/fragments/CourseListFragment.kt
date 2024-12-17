package com.example.kr_surovtsev.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kr_surovtsev.R
import com.example.kr_surovtsev.data.Repository
import com.example.kr_surovtsev.databinding.FragmentCourseListBinding
import com.example.kr_surovtsev.ui.adapters.CourseAdapter
import com.example.kr_surovtsev.viewmodel.CourseViewModel
import com.example.kr_surovtsev.viewmodel.CourseViewModelFactory

class CourseListFragment : Fragment() {

    private var _binding: FragmentCourseListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CourseViewModel
    private lateinit var adapter: CourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseListBinding.inflate(inflater, container, false)
        val rootView = binding.root

        // Ініціалізація ViewModel
        val repository = Repository(requireContext())
        val factory = CourseViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(CourseViewModel::class.java)

        // Налаштування RecyclerView
        adapter = CourseAdapter(emptyList()) { course ->
            // Переходимо до фрагмента з деталями курсу
            val action = CourseListFragmentDirections
                .actionCourseListFragmentToCourseDetailsFragment(course.id)
            findNavController().navigate(action)
        }


        // Кнопка для додавання курсу
        binding.buttonAddCourse.setOnClickListener {
            findNavController().navigate(R.id.action_courseListFragment_to_addCourseFragment)
        }

        // Спостерігаємо за даними
        viewModel.courses.observe(viewLifecycleOwner) { courses ->
            adapter.updateCourses(courses)
        }

        viewModel.fetchCourses()

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
