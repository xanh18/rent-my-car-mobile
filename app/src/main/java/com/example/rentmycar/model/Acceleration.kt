package com.example.rentmycar.model

import com.google.gson.annotations.SerializedName

data class Acceleration(
    @SerializedName("id") val id: Int?,
    @SerializedName("xAxis") val xAxis: Double?,
    @SerializedName("yAxis") val yAxis: Double?,
    @SerializedName("zAxis") val zAxis: Double?
)