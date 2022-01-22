package com.example.rentmycar.activity


import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.rentmycar.R
import com.example.rentmycar.ServiceBuilder
import com.example.rentmycar.api.TripAPI
import com.example.rentmycar.api.UserAPI
import com.example.rentmycar.model.Acceleration
import com.example.rentmycar.model.Trip
import com.example.rentmycar.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrivingActivity: AppCompatActivity(), SensorEventListener {
    lateinit var gps: TextView
    lateinit var credit_score_text: TextView
    lateinit var stop_button : Button
    lateinit var sensorManager : SensorManager
    var trip_id : Int = 0
    var active : Boolean = true

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
        //Retrieves trip_id from intent
        trip_id = intent.getStringExtra("trip_id").toString().toInt()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.driving_layout)

        //Creates sensorManager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_NORMAL)
        }
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_NORMAL)
        }

        //Gets and sets labels
        gps = findViewById(R.id.gps)
        credit_score_text = findViewById(R.id.credit_score)

        //Gets and sets button
        stop_button = findViewById(R.id.stop_button)
        stop_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if(active){
                    //Deactivates speed observation
                    active = false;
                    stop_button.setBackgroundColor(Color.GRAY)
                } else {
                    //Activates speed observation
                    active = true;
                    stop_button.setBackgroundColor(Color.MAGENTA)
                }
            }
        })
    }

    private fun acceleration(params: Trip, onResult: (Boolean?) -> Unit){
        //Builds API service to check acceleration with
        val retrofit = ServiceBuilder.buildService(TripAPI::class.java)
        //Calls API endpoint
        retrofit.acceleration(params).enqueue(
            object : Callback<Boolean> {
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<Boolean>, response: Response<Boolean>) {
                    //Passes result back
                    val addedTrip = response.body()
                    onResult(addedTrip)
                }
            }
        )
    }

    private fun updateSpeed(xAxis: Float, yAxis: Float){
        //Creates a Trip object with an Acceleration object within to hold the current speed.
        val params = Trip(id = trip_id, acceleration = Acceleration(id = null, xAxis = xAxis.toDouble(), yAxis = yAxis.toDouble(), zAxis = null),
            startDateTime = null, endDateTime = null, distance = null, location = null, car = null, user = null)
        //Checks if accelerating too quickly
        acceleration(params){
            if (it != null) {
                d("succes", it.toString())
                if(it.toString() == "false"){
                    //Builds API service to lower credit score with
                    val retrofit = ServiceBuilder.buildService(UserAPI::class.java)
                    //Calls API endpoint
                    retrofit.lowerScore(User(id = 1,null, null, null, null, null, null,
                        null, null, null, null, null, null, null)).enqueue(
                        object : Callback<Int> {
                            override fun onFailure(call: Call<Int>, t: Throwable) {
                                d("Error", "Rejoice citizen. Social score failed to update.")
                            }
                        override fun onResponse( call: Call<Int>, response: Response<Int>) {
                            //Shows new credit score on screen
                            credit_score_text.text = response.body().toString()
                        }
                    }
                    )
                }
            } else {
                d("Error", ":(");
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(active) {
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                //Gets speed values from sensor
                val xAxis = event.values[0]
                val yAxis = event.values[1]
                updateSpeed(xAxis, yAxis)
            } else if (event?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR) {
                //Shows rotation values from sensor
                gps.text = event.values[0].toString()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

}