package com.example.rentmycar.adapter

import android.content.Context
import android.os.Build
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.rentmycar.R
import com.example.rentmycar.model.Car
import kotlinx.android.synthetic.main.row_cars.view.*

class CarAdapter (val context: Context, var carList: List<Car>): RecyclerView.Adapter<CarAdapter.ViewHolder>() {
    class ViewHolder(carView: View): RecyclerView.ViewHolder(carView) {

        var iv_image: ImageView
        var tv_brand: TextView
        var tv_brand_model: TextView
        var tv_price: TextView

        init {
            iv_image = carView.iv_image
            tv_brand = carView.tv_brand
            tv_brand_model = carView.tv_brand_model
            tv_price = carView.tv_price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var itemView = LayoutInflater.from(context).inflate(R.layout.row_cars, parent, false)
        return ViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_brand.text = carList[position].brand
        holder.tv_brand_model.text = carList[position].brandModel
        holder.tv_price.text = carList[position].startRate.toString()
    }

    override fun getItemCount(): Int {
        return carList.size
    }

    fun update(newCarList:List<Car>){
        carList = newCarList
        notifyDataSetChanged()
    }

}