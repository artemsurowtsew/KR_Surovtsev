package com.example.kr_surovtsev.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kr_surovtsev.data.Repository
import com.example.kr_surovtsev.model.Course
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CourseViewModel(private val repository: Repository) : ViewModel() {

    // Приватне MutableLiveData для внутрішнього використання
    private val _courses = MutableLiveData<List<Course>>()

    // Публічне LiveData для спостереження у UI
    val courses: LiveData<List<Course>> get() = _courses

    // Додавання нового курсу
    fun addCourse(course: Course) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCourse(course)
            fetchCourses() // Оновити список після додавання
        }
    }

    // Завантаження всіх курсів
    fun fetchCourses() {
        viewModelScope.launch(Dispatchers.IO) {
            val courseList = repository.getAllCourses()
            _courses.postValue(courseList)
        }
    }

    // Пошук курсу за ID
    fun getCourseById(courseId: Int): LiveData<Course?> {
        val courseLiveData = MutableLiveData<Course?>()
        viewModelScope.launch(Dispatchers.IO) {
            val course = repository.getCourseById(courseId)
            courseLiveData.postValue(course)
        }
        return courseLiveData
    }

}
