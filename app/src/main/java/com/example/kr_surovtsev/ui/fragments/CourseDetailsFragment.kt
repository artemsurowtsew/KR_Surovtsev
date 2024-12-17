package com.example.kr_surovtsev.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kr_surovtsev.databinding.FragmentCourseDetailsBinding
import com.example.kr_surovtsev.viewmodel.CourseViewModel
import com.example.kr_surovtsev.viewmodel.CourseViewModelFactory
import com.example.kr_surovtsev.data.Repository

class CourseDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCourseDetailsBinding
    private lateinit var viewModel: CourseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCourseDetailsBinding.inflate(inflater, container, false)

        // Ініціалізація ViewModel
        val repository = Repository(requireContext())
        val factory = CourseViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(CourseViewModel::class.java)

        // Отримуємо аргумент courseId
        val args = CourseDetailsFragmentArgs.fromBundle(requireArguments())
        val courseId = args.courseId

        viewModel.getCourseById(courseId).observe(viewLifecycleOwner) { course ->
            if (course != null) {
                binding.textCourseName.text = course.name
                binding.textCourseDescription.text = course.description
                binding.textCourseLevel.text = course.level
                binding.textCoursePrice.text =
                    "Discounted: ${course.discountedPrice}, Original: ${course.originalPrice}"
            }
        }


        return binding.root
    }
}
