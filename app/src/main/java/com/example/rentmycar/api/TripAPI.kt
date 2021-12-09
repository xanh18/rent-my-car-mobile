package com.example.rentmycar.api

import com.example.rentmycar.model.Trip
import retrofit2.Call
import retrofit2.http.GET

interface TripAPI {
    @GET("trip/person/1")
    fun getData(): Call<List<Trip>>
}