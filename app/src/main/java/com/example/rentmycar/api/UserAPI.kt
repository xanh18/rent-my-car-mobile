package com.example.rentmycar.api

import retrofit2.Call
import com.example.rentmycar.model.User
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserAPI {
    @POST("user/login")
    @Headers("Content-Type:application/json")
    fun loginUser(@Body params: User): Call<User>

    @POST("user/register")
    @Headers("Content-Type:application/json")
    fun registerUser(@Body params: User): Call<User>

    @POST("user/lower")
    @Headers("Content-Type:application/json")
    fun lowerScore(@Body params: User): Call<Int>
}