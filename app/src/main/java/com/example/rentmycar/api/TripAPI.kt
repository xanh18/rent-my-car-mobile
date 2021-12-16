package com.example.rentmycar.api

import com.example.rentmycar.model.Trip
import retrofit2.Call
import retrofit2.http.GET
import java.util.*

interface TripAPI {

    @GET("trip")
    fun findAll(): Call<List<Trip>>

    @GET("trip/person/1")
    fun findByUser_Id(): Call<List<Trip>>

    @GET("trip/car/1")
    fun findByCar_Id(): Call<List<Trip>>

    @GET("trip/1")
    fun findById(): Call<Optional<Trip>>




}