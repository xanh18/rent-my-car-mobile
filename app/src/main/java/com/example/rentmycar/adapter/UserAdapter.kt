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
import com.example.rentmycar.model.User
import kotlinx.android.synthetic.main.login_layout.view.*

class UserAdapter (val context: Context, val userList: List<User>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    class ViewHolder(userView: View): RecyclerView.ViewHolder(userView) {
        var login_button: Button

        init {
            login_button = userView.login_button
            login_button.setOnClickListener{
                d("haha","hihi")
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.row_trips, parent, false)
        return ViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.login_button
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}