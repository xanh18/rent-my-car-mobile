package com.example.rentmycar.api

import com.example.rentmycar.model.Car
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.*

interface CarAPI {

    @GET("car")
    fun findAll(): Call<List<Car>>

    @GET("car/get/{id}")
    fun findById(): Call<Optional<Car>>

    @POST("car/save")
    @Headers("Content-Type:application/json")
    fun saveCar(@Body params: Car): Call<Boolean>

    @GET("car/brand/{brand}")
    fun findByBrandContaining(): Call<List<Car>>

    @GET("car/user/{id}")
    fun findByUser_Id(): Call<List<Car>>

    @GET("car/{car}/{available}")
    fun findByAvailable(): Call<List<Car>>

    @GET("car/search/kmrate/{kmrate}")
    fun findByKmRateLessThanEqual(): Call<List<Car>>

    @POST("car/search/available")
    fun findByStartDateTimeLessThanEqualAndEndDateTimeGreaterThanEqual(): Call<List<Car>>

    @GET("car/search/startRate/{startRate}")
    fun findByStartRateLessThanEqual(): Call<List<Car>>
}