package com.example.rentmycar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rentmycar.adapter.MyAdapter
import com.example.rentmycar.api.ApiInterface
import com.example.rentmycar.model.Trip
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://10.0.2.2:8080/"
class MainActivity : AppCompatActivity() {

    lateinit var myAdapter: MyAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        getMyData()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview_trips.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerview_trips.layoutManager = linearLayoutManager

        getMyData()
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<Trip>?> {
            override fun onResponse(call: Call<List<Trip>?>, response: Response<List<Trip>?>) {
                val responseBody = response.body()!!

                myAdapter = MyAdapter(baseContext, responseBody)
                myAdapter.notifyDataSetChanged()
                recyclerview_trips.adapter = myAdapter

//                val text = StringBuilder()
//                for (myData in responseBody){
//                    text.append(myData.id)
//                    text.append(myData.acceleration)
//                    text.append(myData.distance)
//                    text.append(myData.startDateTime)
//                    text.append(myData.endDateTime)
//                    text.append(myData.location)
//                }
//                txtId.text = text
            }

            override fun onFailure(call: Call<List<Trip>?>, t: Throwable) {
                d("MainActivity", "onFailure: "+t.message)
            }
        })
    }
}