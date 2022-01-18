package com.example.rentmycar.model

import com.google.gson.annotations.SerializedName

data class Car(
    @SerializedName("id") val id: Int?,
    @SerializedName("brand") val brand: String?,
    @SerializedName("brandModel") val brandModel: String?,
    @SerializedName("carRange") val carRange: Int?,
    @SerializedName("endDateTime") val endDateTime: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("kmDriven") val kmDriven: Int?,
    @SerializedName("kmRate") val kmRate: Any?,
    @SerializedName("startDateTime") val startDateTime: String?,
    @SerializedName("startRate") val startRate: Any?,
    @SerializedName("tco") val tco: Double?,
    @SerializedName("trips") val trips: List<Trip>?
)