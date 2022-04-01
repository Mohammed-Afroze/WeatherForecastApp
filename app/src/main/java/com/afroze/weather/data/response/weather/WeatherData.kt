package com.afroze.weather.data.response.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.afroze.weather.data.persistance.WeatherDataConverter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@Entity
@TypeConverters(WeatherDataConverter::class)
data class WeatherData(
    @PrimaryKey
    @SerialName("lat") val lat: Double,
    @SerialName("lon") val lon: Double,
    @SerialName("timezone") val timezone: String,
    @SerialName("timezone_offset") val timezoneOffset: Int,
    @SerialName("current") val current: Current,
    @SerialName("minutely") val minutely: List<Minutely> =  mutableListOf(),
    @SerialName("hourly") val hourly: List<Hourly>,
    @SerialName("daily") val daily: List<Daily>
)