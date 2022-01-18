package com.example.rentmycar.activity


import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
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

    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var gps: TextView
    lateinit var credit_score_text: TextView
    lateinit var stop_button : Button
    lateinit var sensorManager : SensorManager
    var trip_id : Int = 0
    var active : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        trip_id = intent.getStringExtra("trip_id").toString().toInt()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.driving_layout)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_NORMAL)
        }
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_NORMAL)
        }

        gps = findViewById(R.id.gps)
        credit_score_text = findViewById(R.id.credit_score)
        stop_button = findViewById(R.id.stop_button)
        stop_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if(active){
                    active = false;
                    stop_button.setBackgroundColor(Color.GRAY)
                } else {
                    active = true;
                    stop_button.setBackgroundColor(Color.MAGENTA)
                }
            }
        })
    }

    private fun acceleration(params: Trip, onResult: (Boolean?) -> Unit){
        val retrofit = ServiceBuilder.buildService(TripAPI::class.java)
        retrofit.acceleration(params).enqueue(
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

    private fun updateSpeed(xAxis: Float, yAxis: Float){
        val params = Trip(id = trip_id, acceleration = Acceleration(id = null, xAxis = xAxis.toDouble(), yAxis = yAxis.toDouble(), zAxis = null),
            startDateTime = null, endDateTime = null, distance = null, location = null, car = null, user = null)
        acceleration(params){
            if (it != null) {
                d("succes", it.toString())
                if(it.toString() == "false"){
                    val retrofit = ServiceBuilder.buildService(UserAPI::class.java)
                        retrofit.lowerScore(User(id = 1,null, null, null, null, null, null,
                            null, null, null, null, null, null, null)).enqueue(
                            object : Callback<Int> {
                                override fun onFailure(call: Call<Int>, t: Throwable) {
                                    d("Error", "Rejoice citizen. Social score failed to update.")
                                }
                            override fun onResponse( call: Call<Int>, response: Response<Int>) {
                                d("waaaaaaaaaaaaaaaaaaaaaa", response.body().toString())
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
                val xAxis = event.values[0]
                val yAxis = event.values[1]
                updateSpeed(xAxis, yAxis)
            } else if (event?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR) {
                gps.text = event.values[0].toString()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

}