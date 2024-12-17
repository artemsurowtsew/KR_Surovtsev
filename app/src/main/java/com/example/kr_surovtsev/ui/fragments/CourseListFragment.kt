package com.example.kr_surovtsev.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kr_surovtsev.R
import com.example.kr_surovtsev.data.Repository
import com.example.kr_surovtsev.ui.adapters.CourseAdapter
import com.example.kr_surovtsev.viewmodel.CourseViewModel
import com.example.kr_surovtsev.viewmodel.CourseViewModelFactory
import kotlinx.android.synthetic.main.fragment_course_list.*

class CourseListFragment : Fragment() {

    private lateinit var viewModel: CourseViewModel
    private lateinit var adapter: CourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_course_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Repository(requireContext())
        val factory = CourseViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(CourseViewModel::class.java)

        adapter = CourseAdapter(emptyList()) { course ->
            // Обробка кліку на курс, навігація до деталей або редагування
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.courses.observe(viewLifecycleOwner) { courses ->
            adapter.updateCourses(courses)
        }

        viewModel.fetchCourses()

        buttonAddCourse.setOnClickListener {
            // Навігація до AddCourseFragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, AddCourseFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
