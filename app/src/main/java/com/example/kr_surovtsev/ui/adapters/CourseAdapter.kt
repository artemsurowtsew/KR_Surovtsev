package com.example.kr_surovtsev.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kr_surovtsev.databinding.CourseItemLayoutBinding
import com.example.kr_surovtsev.model.Course

class CourseAdapter(
    private var courses: List<Course>,
    private val onItemClick: (Course) -> Unit
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    inner class CourseViewHolder(private val binding: CourseItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course) {
            binding.textCourseName.text = course.name
            binding.textCourseDescription.text = course.description
            binding.textCourseLevel.text = "Level: ${course.level}"

            // Обробка натискання на курс
            binding.root.setOnClickListener {
                onItemClick(course)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = CourseItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(courses[position])
    }

    override fun getItemCount(): Int = courses.size

    // Оновлення списку з використанням DiffUtil для кращої продуктивності
    fun updateCourses(newCourses: List<Course>) {
        val diffResult = DiffUtil.calculateDiff(CourseDiffCallback(courses, newCourses))
        courses = newCourses
        diffResult.dispatchUpdatesTo(this)
    }

    // Клас для DiffUtil
    private class CourseDiffCallback(
        private val oldList: List<Course>,
        private val newList: List<Course>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
