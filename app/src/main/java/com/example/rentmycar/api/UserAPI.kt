package com.example.rentmycar.api


import retrofit2.Call
import retrofit2.http.GET
import com.example.rentmycar.model.User

import okhttp3.RequestBody
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
}