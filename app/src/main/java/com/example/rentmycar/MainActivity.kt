package com.example.rentmycar

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rentmycar.adapter.TripAdapter
import com.example.rentmycar.model.Trip
import com.example.rentmycar.viewmodel.TripViewModel
import kotlinx.android.synthetic.main.trips_layout.*

const val BASE_URL = "http://10.0.2.2:8080/"
class MainActivity : AppCompatActivity() {

    lateinit var tripAdapter: TripAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var test_button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        getMyData()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.trips_layout)

        test_button = findViewById(R.id.test_button)
        test_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                test_button.setBackgroundColor(Color.GRAY)
            }
        })

        recyclerview_trips.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerview_trips.layoutManager = linearLayoutManager


        getMyData()
    }

    private fun getMyData() {
        val model: TripViewModel by viewModels()
        model.getTrips().observe(this, Observer<List<Trip>>{ trips ->
            tripAdapter = TripAdapter(baseContext, trips)
            tripAdapter.notifyDataSetChanged()
            recyclerview_trips.adapter = tripAdapter
        })
    }
}