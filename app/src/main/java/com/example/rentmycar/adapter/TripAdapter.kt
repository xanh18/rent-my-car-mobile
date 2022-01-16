package com.example.rentmycar.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.rentmycar.R
import com.example.rentmycar.activity.DrivingActivity
import com.example.rentmycar.activity.TripActivity
import com.example.rentmycar.model.Trip
import kotlinx.android.synthetic.main.row_trips.view.*
import kotlinx.android.synthetic.main.trips_layout.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TripAdapter (val context: Context, var tripList: List<Trip>): RecyclerView.Adapter<TripAdapter.ViewHolder>() {
    class ViewHolder(tripView: View): RecyclerView.ViewHolder(tripView) {
        var start_date: TextView
        var end_date: TextView
        var start_button: Button

        init {
            start_date = tripView.start_date
            end_date = tripView.end_date
            start_button = tripView.start_button
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.row_trips, parent, false)
        return ViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.start_date.text = LocalDateTime.parse(tripList[position].startDateTime).format(DateTimeFormatter.ofPattern("dd-MM-yy   HH:mm"))
        holder.end_date.text = LocalDateTime.parse(tripList[position].endDateTime).format(DateTimeFormatter.ofPattern("dd-MM-yy   HH:mm"))
        val id = tripList[position].id
        holder.start_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val intent = Intent(view.context, DrivingActivity::class.java)
                intent.putExtra("trip_id", id.toString())
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.context.startActivity(intent)
            }
        })
    }

    fun update(newTripList:List<Trip>){
        tripList = newTripList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return tripList.size
    }


}