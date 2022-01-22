package com.example.rentmycar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rentmycar.activity.MainMenuActivity
import com.example.rentmycar.api.UserAPI
import com.example.rentmycar.model.User
import kotlinx.android.synthetic.main.main_layout.*
import kotlinx.android.synthetic.main.main_menu.*
import kotlinx.android.synthetic.main.trips_layout.*
import kotlinx.android.synthetic.main.user_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val BASE_URL = "http://10.0.2.2:8090/"
class MainActivity : AppCompatActivity() {
    lateinit var constraintLayout: ConstraintLayout
    lateinit var registerUserBtn : Button
    lateinit var loginUserBtn : Button
    lateinit var register_username_input : EditText
    lateinit var register_password_input : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_register)

        //Finds and sets input fields and buttons
        register_username_input = findViewById(R.id.register_username_input)
        register_password_input = findViewById(R.id.register_password_input)
        registerUserBtn = findViewById(R.id.register_button)
        loginUserBtn = findViewById(R.id.login_button)

        registerUserBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                registerUser(User(null, null, null, null, null, null, 999   , null, null,
                    null, register_username_input.text.toString(), register_password_input.text.toString(), null, null)){
                    if (it?.id != null) {
                        Log.d("Success", it.id.toString())
                        //Shows success message on screen
                        Toast.makeText(this@MainActivity, "New user registered", Toast.LENGTH_SHORT).show()
                    } else {
                        //Shows failure message on screen
                        Toast.makeText(this@MainActivity, "User already exists", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        loginUserBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                loginUser(User(null, null, null, null, null, null, null, null, null,
                    null, register_username_input.text.toString(), register_password_input.text.toString(), null, null)){
                    if (it?.id != null) {
                        Log.d("Success", it.id.toString())
                            val intent = Intent(this@MainActivity, MainMenuActivity::class.java)
                            startActivity(intent)
                    } else {
                        //Shows failure message on screen
                        Toast.makeText(this@MainActivity, "Failed to log in", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun loginUser(params: User, onResult: (User?) -> Unit) {
        //Builds API service to login with
        val retrofit = ServiceBuilder.buildService(UserAPI::class.java)
        //Calls API endpoint
        retrofit.loginUser(params).enqueue(
            object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    //Passes result back
                    val user = response.body()
                    onResult(user)
                }
            }
        )
    }

    private fun registerUser(params: User, onResult: (User?) -> Unit) {
        //Builds API service to register with
        val retrofit = ServiceBuilder.buildService(UserAPI::class.java)
        //Calls API endpoint
        retrofit.registerUser(params).enqueue(
            object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    //Passes result back
                    val user = response.body()
                    onResult(user)
                }
            }
        )
    }
}