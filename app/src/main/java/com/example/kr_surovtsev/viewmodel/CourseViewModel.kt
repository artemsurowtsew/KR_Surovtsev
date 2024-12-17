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
    private val _courses = MutableLiveData<List<Course>>()
    val courses: LiveData<List<Course>> get() = _courses

    fun fetchCourses() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getAllCourses()
            _courses.postValue(data)
        }
    }

    fun addCourse(course: Course) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCourse(course)
            fetchCourses()
        }
    }
}
