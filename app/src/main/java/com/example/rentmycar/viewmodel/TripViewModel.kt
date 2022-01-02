package com.example.rentmycar.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rentmycar.BASE_URL
import com.example.rentmycar.api.TripAPI
import com.example.rentmycar.model.Trip
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TripViewModel: ViewModel() {

    private val trips: MutableLiveData<List<Trip>> by lazy {
        MutableLiveData<List<Trip>>().also {
            loadTrips()
        }
    }

    fun getTrips(): LiveData<List<Trip>> {
        return trips
    }

    fun updateTrips(){
        loadTrips()
    }

    private fun loadTrips() {
        // Do an asynchronous operation to fetch trips.
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(TripAPI::class.java)

        val retrofitData = retrofitBuilder.findAll()

        retrofitData.enqueue(object : Callback<List<Trip>?> {
            override fun onResponse(call: Call<List<Trip>?>, response: Response<List<Trip>?>) {
                trips.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Trip>?>, t: Throwable) {
                Log.d("TripViewModel", "onFailure: " + t.message)
            }
        })
    }
}