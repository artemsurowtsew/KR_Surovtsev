package com.example.kr_surovtsev.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kr_surovtsev.databinding.FragmentAddCourseBinding
import com.example.kr_surovtsev.data.Repository
import com.example.kr_surovtsev.model.Course
import com.example.kr_surovtsev.viewmodel.CourseViewModel
import com.example.kr_surovtsev.viewmodel.CourseViewModelFactory

class AddCourseFragment : Fragment() {

    private lateinit var binding: FragmentAddCourseBinding
    private lateinit var viewModel: CourseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Ініціалізація binding
        binding = FragmentAddCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ініціалізація ViewModel
        val repository = Repository(requireContext())
        val factory = CourseViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(CourseViewModel::class.java)

        // Обробка натискання кнопки збереження
        binding.buttonSave.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val description = binding.editTextDescription.text.toString()
            val level = binding.editTextLevel.text.toString()
            val originalPriceText = binding.editTextOriginalPrice.text.toString()
            val discountedPriceText = binding.editTextDiscountedPrice.text.toString()
            val storeName = "Default Store" // За замовчуванням або отримати з іншого поля

            if (name.isNotBlank() && description.isNotBlank() && level.isNotBlank() &&
                originalPriceText.isNotBlank() && discountedPriceText.isNotBlank()) {

                val originalPrice = originalPriceText.toDoubleOrNull() ?: 0.0
                val discountedPrice = discountedPriceText.toDoubleOrNull() ?: 0.0

                val course = Course(
                    name = name,
                    description = description,
                    storeName = storeName,
                    originalPrice = originalPrice,
                    discountedPrice = discountedPrice,
                    level = level
                )

                viewModel.addCourse(course)
                Toast.makeText(requireContext(), "Course added successfully", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
