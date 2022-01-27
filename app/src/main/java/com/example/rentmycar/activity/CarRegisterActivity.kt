package com.example.rentmycar.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.rentmycar.R
import com.example.rentmycar.ServiceBuilder
import com.example.rentmycar.api.CarAPI
import com.example.rentmycar.model.Car
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarRegisterActivity: AppCompatActivity() {
    lateinit var car_brand_input: EditText
    lateinit var car_model_input: EditText
    lateinit var car_price_input: EditText
    lateinit var submit_button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.car_register)

        //finds and sets all the input fields
        car_brand_input = findViewById(R.id.car_brand_input)
        car_model_input = findViewById(R.id.car_model_input)
        car_price_input = findViewById(R.id.car_price_input)

        //finds and sets the button
        submit_button = findViewById(R.id.submit_button)
        submit_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                //Uses input data to create new car object with input data
                val params = Car(null, car_brand_input.text.toString(), car_model_input.text.toString(), null, null, null, null, null, null, car_price_input.text.toString(), null, null)
                //Saves Car
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

    private fun saveCar(params: Car, onResult: (Boolean?) -> Unit){
        //Builds API service to save car with
        val retrofit = ServiceBuilder.buildService(CarAPI::class.java)
        //Calls API endpoint
        retrofit.saveCar(params).enqueue(
            object : Callback<Boolean> {
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<Boolean>, response: Response<Boolean>) {
                    //Passes result back
                    val addedCar = response.body()
                    onResult(addedCar)
                    Toast.makeText(this@CarRegisterActivity, "New car registered", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}