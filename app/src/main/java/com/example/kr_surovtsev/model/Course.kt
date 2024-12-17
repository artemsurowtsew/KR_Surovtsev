package com.example.kr_surovtsev.model


data class Course(
    val id: Int = 0,
    var name: String,
    var description: String,
    var storeName: String, // Назва платформи/ресурсу
    var originalPrice: Double,
    var discountedPrice: Double,
    var level: String // Рівень курсу: початковий, середній, просунутий
)
