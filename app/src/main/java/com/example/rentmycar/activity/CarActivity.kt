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
import com.example.rentmycar.adapter.CarAdapter
import com.example.rentmycar.model.Car
import com.example.rentmycar.viewmodel.CarViewModel
import kotlinx.android.synthetic.main.cars_layout.*

class CarActivity : AppCompatActivity() {
    lateinit var carAdapter: CarAdapter
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
        getCars()

        super.onCreate(savedInstanceState)

        setContentView(R.layout.cars_layout)
        recyclerview_cars.setHasFixedSize(true)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerview_cars.layoutManager = linearLayoutManager

        getCars()
    }

    //Create car adapter to retrieve car data and put it in a recyclerview
    private fun getCars() {
        val model: CarViewModel by viewModels()
        model.getCars().observe(this, Observer<List<Car>> { cars ->
            carAdapter = CarAdapter(baseContext, cars)
            carAdapter.notifyDataSetChanged()
            recyclerview_cars.adapter = carAdapter
        })
    }
}
