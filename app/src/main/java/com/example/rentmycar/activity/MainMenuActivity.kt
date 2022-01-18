package com.example.rentmycar.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rentmycar.R



class MainMenuActivity : AppCompatActivity() {

    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var mainMenuPlanTripsBtn: Button
    lateinit var mainMenuMyTripBtn : Button
    lateinit var mainMenuCarBtn : Button
    lateinit var mainMenuRegisterCarBtn : Button


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.trips_layout)


        mainMenuMyTripBtn = findViewById(R.id.main_menu_my_trip_btn)
        mainMenuMyTripBtn.setOnClickListener{
            val Intent = Intent(this, UserActivity::class.java)
            startActivity(Intent)
        }

        mainMenuRegisterCarBtn = findViewById(R.id.main_menu_register_car_btn)
        mainMenuRegisterCarBtn.setOnClickListener{
            val Intent = Intent(this, UserActivity::class.java)
            startActivity(Intent)
        }

        mainMenuPlanTripsBtn = findViewById(R.id.main_menu_plan_trips_btn)
        mainMenuPlanTripsBtn.setOnClickListener{
            val Intent = Intent(this, CarActivity::class.java)
            startActivity(Intent)
        }


    }
}