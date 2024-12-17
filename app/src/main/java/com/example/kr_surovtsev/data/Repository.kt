package com.example.kr_surovtsev.data

import android.content.ContentValues
import android.content.Context
import com.example.kr_surovtsev.model.Course

class Repository(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun addCourse(course: Course) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NAME, course.name)
            put(DatabaseHelper.COLUMN_DESCRIPTION, course.description)
            put(DatabaseHelper.COLUMN_STORE_NAME, course.storeName)
            put(DatabaseHelper.COLUMN_ORIGINAL_PRICE, course.originalPrice)
            put(DatabaseHelper.COLUMN_DISCOUNTED_PRICE, course.discountedPrice)
            put(DatabaseHelper.COLUMN_LEVEL, course.level)
        }
        db.insert(DatabaseHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getAllCourses(): List<Course> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null)
        val courses = mutableListOf<Course>()

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME))
                val description = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPTION))
                val storeName = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_STORE_NAME))
                val originalPrice = getDouble(getColumnIndexOrThrow(DatabaseHelper.COLUMN_ORIGINAL_PRICE))
                val discountedPrice = getDouble(getColumnIndexOrThrow(DatabaseHelper.COLUMN_DISCOUNTED_PRICE))
                val level = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_LEVEL))

                courses.add(
                    Course(id, name, description, storeName, originalPrice, discountedPrice, level)
                )
            }
            close()
        }


        db.close()
        return courses
    }
}
