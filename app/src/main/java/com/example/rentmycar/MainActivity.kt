package com.example.rentmycar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rentmycar.activity.CarActivity
import com.example.rentmycar.activity.TripActivity
import com.example.rentmycar.activity.UserActivity
import com.example.rentmycar.adapter.TripAdapter
import com.example.rentmycar.adapter.UserAdapter
import com.example.rentmycar.api.TripAPI
import com.example.rentmycar.api.UserAPI
import com.example.rentmycar.model.Car
import com.example.rentmycar.model.Trip
import com.example.rentmycar.model.User
import com.example.rentmycar.viewmodel.TripViewModel
import kotlinx.android.synthetic.main.main_layout.*
import kotlinx.android.synthetic.main.main_menu.*
import kotlinx.android.synthetic.main.trips_layout.*
import kotlinx.android.synthetic.main.user_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val BASE_URL = "http://10.0.2.2:8070/"
class MainActivity : AppCompatActivity() {

    lateinit var linearLayoutManager: LinearLayoutManager

    lateinit var userAdapter: UserAdapter
    lateinit var constraintLayout: ConstraintLayout
    lateinit var registerUserBtn : Button
    lateinit var loginUserBtn : Button
    lateinit var register_username_input : EditText
    lateinit var register_password_input : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_register)
        register_username_input = findViewById(R.id.register_username_input)
        register_password_input = findViewById(R.id.register_password_input)
        registerUserBtn = findViewById(R.id.register_button)
        loginUserBtn = findViewById(R.id.login_button)

        registerUserBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                registerUser(User(null, null, null, null, null, null, null, null, null,
                    null, register_username_input.text.toString(), register_password_input.text.toString(), null, null)){
                    if (it?.id != null) {
                        Log.d("Success", it.id.toString())
                        Log.d("Success", ":)")
                        Toast.makeText(this@MainActivity, "New user registerd", Toast.LENGTH_SHORT).show()

                        // it = logged in user parsed as response
                        // it?.id = user ID
                    } else {
                        Log.d("Error", ":(")
                        Toast.makeText(this@MainActivity, "user already exists", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        loginUserBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                loginUser(User(null, null, null, null, null, null, null, null, null,
                    null, register_username_input.text.toString(), register_password_input.text.toString(), null, null)){
                    if (it?.id != null) { //username == register_username_input.text.toString() && it?.password == register_password_input.text.toString() ) {
                        Log.d("Success", it.id.toString())
                        Log.d("Success", ":)")
                            val intent = Intent(this@MainActivity, TripActivity::class.java)
                            startActivity(intent)
                    } else {
                        Log.d("Error", ":(")
                    }
                }
            }
        })
        //getMyData()
    }

    private fun loginUser(params: User, onResult: (User?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(UserAPI::class.java)
        retrofit.loginUser(params).enqueue(
            object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val user = response.body()
                    onResult(user)
                }
            }
        )
    }

    private fun registerUser(params: User, onResult: (User?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(UserAPI::class.java)
        retrofit.registerUser(params).enqueue(
            object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val user = response.body()
                    onResult(user)
                }
            }
        )
    }

//    private fun addTrip(params: Trip, onResult: (Trip?) -> Unit){
//        val retrofit = ServiceBuilder.buildService(TripAPI::class.java)
//        retrofit.planTrip(params).enqueue(
//            object : Callback<Trip> {
//                override fun onFailure(call: Call<Trip>, t: Throwable) {
//                    onResult(null)
//                }
//                override fun onResponse( call: Call<Trip>, response: Response<Trip>) {
//                    val addedTrip = response.body()
//                    onResult(addedTrip)
//                }
//            }
//        )
//    }
//
//    private fun getMyData() {
//        val model: TripViewModel by viewModels()
//        model.getTrips().observe(this, Observer<List<Trip>>{ trips ->
//            tripAdapter = TripAdapter(baseContext, trips)
//            tripAdapter.notifyDataSetChanged()
//            recyclerview_trips.adapter = tripAdapter
//        })
//    }
}