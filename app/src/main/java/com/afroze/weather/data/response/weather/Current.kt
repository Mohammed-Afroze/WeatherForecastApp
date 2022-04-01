package com.afroze.weather.data.response.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Current(

    @SerialName("dt") val dt: Int,
    @SerialName("sunrise") val sunrise: Int,
    @SerialName("sunset") val sunset: Int,
    @SerialName("temp") val temp: Double,
    @SerialName("feels_like") val feelsLike: Double,
    @SerialName("pressure") val pressure: Int,
    @SerialName("humidity") val humidity: Int,
    @SerialName("dew_point") val dew_point: Double,
    @SerialName("uvi") val uvi: Double,
    @SerialName("clouds") val clouds: Int,
    @SerialName("visibility") val visibility: Int,
    @SerialName("wind_speed") val wind_speed: Double,
    @SerialName("wind_deg") val wind_deg: Int,
    @SerialName("weather") val weather: List<Weather>
)