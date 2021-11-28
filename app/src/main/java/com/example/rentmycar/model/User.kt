package com.example.rentmycar.model

class User (
    val email: String,
    val firstName: String,
    val lastName: String,
    val address: String,
    val phone: String,
    val socialCredit: Int,
    val longitude: Double,
    val latitude: Double,
    val loggedIn: Boolean,
    val password: String,   
    val trips: List<Trip>,
    val cars: List<Car>
)