package com.example.rentmycar.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.rentmycar.R
import com.example.rentmycar.ServiceBuilder
import com.example.rentmycar.activity.DrivingActivity
import com.example.rentmycar.activity.TripActivity
import com.example.rentmycar.api.TripAPI
import com.example.rentmycar.model.Car
import com.example.rentmycar.model.Trip
import com.example.rentmycar.model.User
import kotlinx.android.synthetic.main.row_cars.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarAdapter (val context: Context, var carList: List<Car>): RecyclerView.Adapter<CarAdapter.ViewHolder>() {
    class ViewHolder(carView: View): RecyclerView.ViewHolder(carView) {
        var brand: TextView
        var brand_model: TextView
        var plan_button: Button

        init {
            brand = carView.brand
            brand_model = carView.brand_model
            plan_button = carView.plan_button
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.row_cars, parent, false)
        return ViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Puts brand and model in respective labels
        holder.brand.text = carList[position].brand
        holder.brand_model.text = carList[position].brandModel

        //Saves id in order to pass it to API endpoint
        val id = carList[position].id
        holder.plan_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                //Creates new trip with car id
                val params = Trip(startDateTime = "2021-12-10T13:49:51.141Z", endDateTime = "2021-12-12T13:49:51.141Z", acceleration = null, distance = null, id = null, location = null,
                    car = Car(id, null, null, null, null, null, null, null, null, null, null, null),
                    user = User(1, null, null, null, null, null, null, null, null, null, null,null, null, null)
                )
                addTrip(params){
                    if (it?.id != null) {
                        d("Success", it.id.toString())
                        holder.plan_button.setBackgroundColor(0x00FF00)

                        val intent = Intent(view.context, TripActivity::class.java)
                        //Saves succes in intent
                        intent.putExtra("success", true.toString())
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        view.context.startActivity(intent)
                    } else {
                        d("Error", ":(");
                    }
                }
            }
        })
    }

    private fun addTrip(params: Trip, onResult: (Trip?) -> Unit){
        //Builds API service to add trip with
        val retrofit = ServiceBuilder.buildService(TripAPI::class.java)
        //Calls API endpoint
        retrofit.planTrip(params).enqueue(
            object : Callback<Trip> {
                override fun onFailure(call: Call<Trip>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<Trip>, response: Response<Trip>) {
                    //Passes back response
                    val addedTrip = response.body()
                    onResult(addedTrip)
                }
            }
        )
    }

    override fun getItemCount(): Int {
        return carList.size
    }

    fun update(newCarList:List<Car>){
        carList = newCarList
        notifyDataSetChanged()
    }

}