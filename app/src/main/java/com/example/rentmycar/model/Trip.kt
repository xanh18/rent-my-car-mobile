package com.example.rentmycar.model

import com.google.gson.annotations.SerializedName

data class Trip(
        @SerializedName("id") val id: Int?,
        @SerializedName("acceleration") val acceleration: Acceleration?,
        @SerializedName("distance") val distance: Double?,
        @SerializedName("endDateTime") val endDateTime: String?,
        @SerializedName("location") val location: Any?,
        @SerializedName("startDateTime") val startDateTime: String?,
        @SerializedName("car") val car: Car?,
        @SerializedName("user") val user: User?,
    )