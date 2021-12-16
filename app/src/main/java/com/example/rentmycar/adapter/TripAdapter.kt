package com.example.rentmycar.adapter

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.rentmycar.R
import com.example.rentmycar.model.Trip
import kotlinx.android.synthetic.main.row_trips.view.*
import kotlinx.android.synthetic.main.trips_layout.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TripAdapter (val context: Context, val tripList: List<Trip>): RecyclerView.Adapter<TripAdapter.ViewHolder>() {
    class ViewHolder(tripView: View): RecyclerView.ViewHolder(tripView) {
        var start_date: TextView
        var end_date: TextView

        init {
            start_date = tripView.start_date
            end_date = tripView.end_date
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
    }

    override fun getItemCount(): Int {
        return tripList.size
    }


}