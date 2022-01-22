package com.example.rentmycar.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rentmycar.R
import com.example.rentmycar.ServiceBuilder
import com.example.rentmycar.adapter.TripAdapter
import com.example.rentmycar.api.TripAPI
import com.example.rentmycar.model.Trip
import com.example.rentmycar.viewmodel.TripViewModel
import kotlinx.android.synthetic.main.trips_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TripActivity: AppCompatActivity() {
    lateinit var tripAdapter: TripAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    //Creates option menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navmenu,menu)
        return true
    }

    //Sets intents for option menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_menu_my_trip_btn -> startActivity(Intent(this, TripActivity::class.java))
            R.id.main_menu_plan_trips_btn -> startActivity(Intent(this, CarActivity::class.java))
            R.id.main_menu_register_car_btn -> startActivity(Intent(this, CarRegisterActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        getTrips(false)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.trips_layout)

        recyclerview_trips.setHasFixedSize(true)
        
        linearLayoutManager = LinearLayoutManager(this)
        recyclerview_trips.layoutManager = linearLayoutManager

        getTrips(false)
    }

    //Create car adapter to retrieve car data and put it in a recyclerview
    private fun getTrips(update: Boolean) {
        val model: TripViewModel by viewModels()
        model.getTrips().observe(this, Observer<List<Trip>>{ trips ->
            tripAdapter = TripAdapter(baseContext, trips)
            tripAdapter.notifyDataSetChanged()
            recyclerview_trips.adapter = tripAdapter
        })
        if (update) {
            //updates viewmodel with potential new trip
            model.updateTrips()
            model.getTrips().value?.let { tripAdapter.update(it) }
        }
    }

}

