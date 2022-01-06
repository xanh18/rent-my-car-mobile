package com.example.rentmycar.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.example.rentmycar.R
import com.example.rentmycar.ServiceBuilder
import com.example.rentmycar.adapter.UserAdapter
import com.example.rentmycar.api.TripAPI
import com.example.rentmycar.api.UserAPI
import com.example.rentmycar.model.Trip
import com.example.rentmycar.model.User
import com.example.rentmycar.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.login_layout.*
import kotlinx.android.synthetic.main.trips_layout.*
import okhttp3.*
import java.io.IOException

import okhttp3.OkHttpClient
import retrofit2.Response
import okhttp3.Route
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import java.net.URI.create


const val BASE_URL = "http://10.0.2.2:8090/"
class UserActivity: AppCompatActivity() {

    lateinit var userAdapter: UserAdapter
    lateinit var constraintLayout: ConstraintLayout
    lateinit var registerUserBtn : Button
    val register_username_input =findViewById(R.id.register_username_input) as EditText
    val register_password_input =findViewById(R.id.register_password_input) as EditText




    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_register)

        //getMyData()
    }




    private fun RegistrerUser(register_username_input: String,register_password_input: String  ){

        registerUserBtn = findViewById(R.id.register_button)
        registerUserBtn.setOnClickListener {

            val retrofit = ServiceBuilder.buildService(UserAPI::class.java)
            retrofit.registrerUser().enqueue(
                object : Callback<User> {
                    override fun onFailure(call: Call<User>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        val addedTrip = response.body()
                        onResult(addedTrip)
                    }
                }
            )
        }


//
    private fun getMyData() {
        val model: UserViewModel by viewModels()
        model.getUsers().observe(this, Observer<List<User>> { users ->
            userAdapter = UserAdapter(baseContext, users)
            userAdapter.notifyDataSetChanged()


        })
    }
}
