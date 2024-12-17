package com.example.kr_surovtsev.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kr_surovtsev.R
import com.example.kr_surovtsev.data.Repository
import com.example.kr_surovtsev.model.Course
import com.example.kr_surovtsev.ui.adapters.CourseAdapter
import com.example.kr_surovtsev.viewmodel.CourseViewModel
import com.example.kr_surovtsev.viewmodel.CourseViewModelFactory
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CourseViewModel
    private lateinit var repository: Repository
    private lateinit var adapter: CourseAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ініціалізуємо Repository та ViewModel
        repository = Repository(this)
        val factory = CourseViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(CourseViewModel::class.java)

        // Ініціалізація RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CourseAdapter(emptyList()) { course ->
            // Обробка кліку на курс
            Log.d("MainActivity", "Clicked on course: ${course.name}")
        }
        recyclerView.adapter = adapter

        // Додаємо тестові курси (тільки один раз)
        addTestCourses(repository)

        // Спостереження за курсами
        viewModel.courses.observe(this) { courses ->
            adapter.updateCourses(courses)
            Log.d("MainActivity", "Courses observed: ${courses.size}")
        }

        // Завантаження курсів
        viewModel.fetchCourses()
    }


    private fun addTestCourses(repository: Repository) {
        // Перевірка, чи вже є дані у БД, щоб уникнути дублювання
        val existingCourses = repository.getAllCourses()
        if (existingCourses.isEmpty()) {
            val course1 = Course(
                name = "Kotlin Programming Basics",
                description = "Learn the fundamentals of Kotlin programming.",
                storeName = "Online Platform",
                originalPrice = 100.0,
                discountedPrice = 50.0,
                level = "Beginner"
            )

            val course2 = Course(
                name = "Android Development",
                description = "Build Android applications with modern tools.",
                storeName = "Udemy",
                originalPrice = 150.0,
                discountedPrice = 75.0,
                level = "Intermediate"
            )

            // Додаємо курси у базу даних
            repository.addCourse(course1)
            repository.addCourse(course2)

            Log.d("MainActivity", "Test courses added to the database.")
        } else {
            Log.d("MainActivity", "Courses already exist in the database.")
        }
    }
}
