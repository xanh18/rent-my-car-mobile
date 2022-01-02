package com.example.rentmycar.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rentmycar.BASE_URL
import com.example.rentmycar.api.CarAPI
import com.example.rentmycar.model.Car
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CarViewModel : ViewModel() {

    private val cars: MutableLiveData<List<Car>> by lazy {
        MutableLiveData<List<Car>>().also {
            loadCars()
        }
    }

    fun getCars(): LiveData<List<Car>> {
        return cars
    }

    private fun loadCars() {
        // Do an asynchronous operation to fetch cars.
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CarAPI::class.java)

        val retrofitData = retrofitBuilder.findAll()

        retrofitData.enqueue(object : Callback<List<Car>?> {
            override fun onResponse(call: Call<List<Car>?>, response: Response<List<Car>?>) {
                cars.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Car>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)
            }
        })
    }
}