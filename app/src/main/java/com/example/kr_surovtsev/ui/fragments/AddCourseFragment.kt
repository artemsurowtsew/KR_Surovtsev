package com.example.kr_surovtsev.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kr_surovtsev.R
import com.example.kr_surovtsev.data.Repository
import com.example.kr_surovtsev.model.Course
import com.example.kr_surovtsev.viewmodel.CourseViewModel
import com.example.kr_surovtsev.viewmodel.CourseViewModelFactory
import kotlinx.android.synthetic.main.fragment_add_course.*

class AddCourseFragment : Fragment() {

    private lateinit var viewModel: CourseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_course, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Repository(requireContext())
        val factory = CourseViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(CourseViewModel::class.java)

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString()
            val description = editTextDescription.text.toString()
            val level = editTextLevel.text.toString()

            if (name.isNotBlank() && description.isNotBlank() && level.isNotBlank()) {
                val course = Course(name = name, description = description, level = level)
                viewModel.addCourse(course)
                Toast.makeText(requireContext(), "Course added!", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
