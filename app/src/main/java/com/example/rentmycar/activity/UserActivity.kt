package com.example.rentmycar.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.View
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


//const val BASE_URL = "http://10.0.2.2:8090/"
class UserActivity: AppCompatActivity() {

    lateinit var userAdapter: UserAdapter
    lateinit var constraintLayout: ConstraintLayout
    lateinit var registerUserBtn : Button
    lateinit var register_username_input : EditText
    lateinit var register_password_input : EditText
    //val register_username_input =findViewById(R.id.register_username_input) as EditText
    //val register_password_input =findViewById(R.id.register_password_input) as EditText
    //private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_register)
        register_username_input = findViewById(R.id.register_username_input)
        register_password_input = findViewById(R.id.register_password_input)
        registerUserBtn = findViewById(R.id.register_button)

        registerUserBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                registerUser(User(null, null, null, null, null, null, null, null, null,
                    null, register_username_input.text.toString(), register_password_input.text.toString(), null, null)){
                    if (it?.id != null) {
                        Log.d("Succes", it.id.toString())
                        Log.d("Succes", ":)")
                        // it = logged in user parsed as response
                        // it?.id = user ID
                    } else {
                        Log.d("Error", ":(");
                    }
                }
            }
        })
        //getMyData()
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

    private fun getMyData() {
        val model: UserViewModel by viewModels()
        model.getUsers().observe(this, Observer<List<User>> { users ->
            userAdapter = UserAdapter(baseContext, users)
            userAdapter.notifyDataSetChanged()
        })
    }
}
