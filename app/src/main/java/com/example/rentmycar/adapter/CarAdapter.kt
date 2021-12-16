package com.example.rentmycar.adapter

import android.content.Context
import android.os.Build
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.rentmycar.R
import com.example.rentmycar.model.Car
import kotlinx.android.synthetic.main.login_layout.view.*

class CarAdapter (val context: Context, val carList: List<Car>): RecyclerView.Adapter<CarAdapter.ViewHolder>() {
    class ViewHolder(carView: View): RecyclerView.ViewHolder(carView) {
        //var login_input: TextView
        //var distance: TextView
        var login_button: Button

        init {
//            tripDates = tripView.tripDates
//            distance = tripView.distance
            login_button = carView.login_button
            login_button.setOnClickListener{
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var itemView = LayoutInflater.from(context).inflate(R.layout.row_trips, parent, false)
        return ViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.tripDates.text = "From: " + LocalDateTime.parse(tripList[position].startDateTime).format(DateTimeFormatter.ofPattern("dd-MM-yy   HH:mm")) + "\n" + "Until: " + LocalDateTime.parse(tripList[position].endDateTime).format(DateTimeFormatter.ofPattern("dd-MM-yy   HH:mm"))
//        holder.distance.text = tripList[position].distance.toString
        holder.login_button
    }

    override fun getItemCount(): Int {
        return carList.size
    }

}