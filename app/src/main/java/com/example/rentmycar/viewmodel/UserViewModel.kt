package com.example.rentmycar.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rentmycar.BASE_URL
import com.example.rentmycar.api.UserAPI
import com.example.rentmycar.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserViewModel : ViewModel() {

    private val users: MutableLiveData<List<User>> by lazy {
        MutableLiveData<List<User>>().also {
            loadUsers()
        }
    }

    fun getUsers(): LiveData<List<User>> {
        return users
    }

    private fun loadUsers() {
        // Do an asynchronous operation to fetch trips.
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(UserAPI::class.java)

//        val retrofitData = retrofitBuilder.findAll()
//
//        retrofitData.enqueue(object : Callback<List<User>?> {
//            override fun onResponse(call: Call<List<User>?>, response: Response<List<User>?>) {
//                users.postValue(response.body())
//            }
//
//            override fun onFailure(call: Call<List<User>?>, t: Throwable) {
//                Log.d("MainActivity", "onFailure: " + t.message)
//            }
//        })
    }
}