package com.example.rentmycar.activity


import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rentmycar.R
import com.example.rentmycar.ServiceBuilder
import com.example.rentmycar.adapter.TripAdapter
import com.example.rentmycar.api.TripAPI
import com.example.rentmycar.model.Car
import com.example.rentmycar.model.Trip
import com.example.rentmycar.model.User
import com.example.rentmycar.viewmodel.TripViewModel
import kotlinx.android.synthetic.main.trips_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//const val BASE_URL = "http://10.0.2.2:8090/"
class TripActivity: AppCompatActivity() {

    lateinit var tripAdapter: TripAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var test_button : Button
    lateinit var mainMenuPlanTripsBtn: Button
    lateinit var mainMenuRegisterCarBtn: Button
    lateinit var mainMenuMyTripBtn : Button
    lateinit var mainMenuCarBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        getMyData(false)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.trips_layout)

//
//        mainMenuMyTripBtn = findViewById(R.id.main_menu_my_trip_btn)
//        mainMenuMyTripBtn.setOnClickListener{
//            val Intent = Intent(this, UserActivity::class.java)
//            startActivity(Intent)
//        }
//
//        mainMenuRegisterCarBtn = findViewById(R.id.main_menu_register_car_btn)
//        mainMenuRegisterCarBtn.setOnClickListener{
//            val Intent = Intent(this, UserActivity::class.java)
//            startActivity(Intent)
//        }
//
//        mainMenuPlanTripsBtn = findViewById(R.id.main_menu_plan_trips_btn)
//        mainMenuPlanTripsBtn.setOnClickListener{
//            val Intent = Intent(this, CarActivity::class.java)
//            startActivity(Intent)
//        }


        test_button = findViewById(R.id.test_button)
        test_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val params = Trip(startDateTime = "2021-12-10T13:49:51.141Z", endDateTime = "2021-12-12T13:49:51.141Z", acceleration = null, distance = null, id = null, location = null,
                    car = Car(1, null, null, null, null, null, null, null, null, null, null, null),
                    user = User(1, null, null, null, null, null, null, null, null, null, null,null, null, null)
                )
                addTrip(params){
                    if (it?.id != null) {
                        d("succes", it.id.toString())
                        d("Success", ":)")
                        // it = newly added user parsed as response
                        // it?.id = newly added user ID
                        getMyData(true);
                    } else {
                        d("Error", ":(");
                    }
                }
                test_button.setBackgroundColor(Color.GRAY)
            }
        })

        recyclerview_trips.setHasFixedSize(true)
        
        linearLayoutManager = LinearLayoutManager(this)
        recyclerview_trips.layoutManager = linearLayoutManager


        getMyData(false)
    }

    private fun addTrip(params: Trip, onResult: (Trip?) -> Unit){
        val retrofit = ServiceBuilder.buildService(TripAPI::class.java)
        retrofit.planTrip(params).enqueue(
            object : Callback<Trip> {
                override fun onFailure(call: Call<Trip>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<Trip>, response: Response<Trip>) {
                    val addedTrip = response.body()
                    onResult(addedTrip)
                }
            }
        )
    }

    private fun getMyData(update: Boolean) {
        val model: TripViewModel by viewModels()
        model.getTrips().observe(this, Observer<List<Trip>>{ trips ->
            tripAdapter = TripAdapter(baseContext, trips)
            tripAdapter.notifyDataSetChanged()
            recyclerview_trips.adapter = tripAdapter
        })
        if (update) {
            model.updateTrips()
            model.getTrips().value?.let { tripAdapter.update(it) }
        }
    }

}

