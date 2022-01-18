package com.example.rentmycar.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rentmycar.R
import com.example.rentmycar.ServiceBuilder
import com.example.rentmycar.adapter.TripAdapter
import com.example.rentmycar.api.CarAPI
import com.example.rentmycar.api.TripAPI
import com.example.rentmycar.api.UserAPI
import com.example.rentmycar.model.Acceleration
import com.example.rentmycar.model.Car
import com.example.rentmycar.model.Trip
import com.example.rentmycar.model.User
import com.example.rentmycar.viewmodel.TripViewModel
import kotlinx.android.synthetic.main.trips_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarRegisterActivity: AppCompatActivity() {
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var car_brand_input: EditText
    lateinit var car_model_input: EditText
    lateinit var car_price_input: EditText
    lateinit var submit_button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.car_register)

        car_brand_input = findViewById(R.id.car_brand_input)
        car_model_input = findViewById(R.id.car_model_input)
        car_price_input = findViewById(R.id.car_price_input)

        submit_button = findViewById(R.id.submit_button)
        submit_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val params = Car(null, car_brand_input.text.toString(), car_model_input.text.toString(), null, null, null, null, null, null, car_price_input.text.toString(), null, null)
                saveCar(params){
                    if (it != null) {
                        d("Succes", it.toString())
                    } else {
                        d("Error", ":(")
                    }
                }
            }
        })
    }

    private fun saveCar(params: Car, onResult: (Boolean?) -> Unit){
        val retrofit = ServiceBuilder.buildService(CarAPI::class.java)
        retrofit.saveCar(params).enqueue(
            object : Callback<Boolean> {
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<Boolean>, response: Response<Boolean>) {
                    val addedTrip = response.body()
                    onResult(addedTrip)
                }
            }
        )
    }
}