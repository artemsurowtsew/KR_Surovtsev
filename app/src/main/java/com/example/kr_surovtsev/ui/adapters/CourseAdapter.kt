package com.example.kr_surovtsev.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kr_surovtsev.R
import com.example.kr_surovtsev.model.Course

class CourseAdapter(
    private var courses: List<Course>,
    private val onItemClick: (Course) -> Unit
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    class CourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.textCourseName)
        val descriptionTextView: TextView = view.findViewById(R.id.textCourseDescription)
        val levelTextView: TextView = view.findViewById(R.id.textCourseLevel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_item_layout, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]
        holder.nameTextView.text = course.name
        holder.descriptionTextView.text = course.description
        holder.levelTextView.text = "Level: ${course.level}"

        holder.itemView.setOnClickListener { onItemClick(course) }
    }

    override fun getItemCount() = courses.size

    fun updateCourses(newCourses: List<Course>) {
        courses = newCourses
        notifyDataSetChanged()
    }
}
