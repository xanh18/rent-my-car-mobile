package com.example.rentmycar.api

import com.example.rentmycar.model.Car
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.*

interface CarAPI {

    @GET("car")
    fun findAll(): Call<List<Car>>

    @GET("car/get/{id}")
    fun findById(): Call<Optional<Car>>

    @POST("car/save")
    fun saveCar(): Call<Car>
}