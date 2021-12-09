package com.example.rentmycar.api

import com.example.rentmycar.model.Trip
import retrofit2.Call
import retrofit2.http.GET

interface UserAPI {
    @GET("user/login")
    fun getData(): Call<String>
}