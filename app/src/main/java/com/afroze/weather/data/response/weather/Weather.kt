package com.afroze.weather.data.response.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    @PrimaryKey()
    @SerialName("id") val id : Int,
    @SerialName("main") val main : String,
    @SerialName("description") val description : String,
    @SerialName("icon") val icon : String
)