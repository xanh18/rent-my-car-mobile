package com.example.rentmycar.model

data class Trip(
    val acceleration: Acceleration,
    val distance: Double,
    val endDateTime: String,
    val id: Int,
    val location: Any,
    val startDateTime: String
)