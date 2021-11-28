package com.example.rentmycar.model

data class Car(
    val brand: String,
    val brandModel: String,
    val carRange: Int,
    val endDateTime: String,
    val id: Int,
    val image: String,
    val kmDriven: Int,
    val kmRate: Any,
    val startDateTime: String,
    val startRate: Any,
    val tco: Double,
    val trips: List<Trip>
)