package com.afroze.weather.data.response.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Daily(

    @SerialName("dt") val dt: Int,
    @SerialName("sunrise") val sunrise: Int,
    @SerialName("sunset") val sunset: Int,
    @SerialName("temp") val temp: Temp,
    @SerialName("feels_like") val feelsLike: FeelsLike,
    @SerialName("pressure") val pressure: Int,
    @SerialName("humidity") val humidity: Int,
    @SerialName("dew_point") val dewPoint: Double,
    @SerialName("wind_speed") val wind_speed: Double,
    @SerialName("wind_deg") val wind_deg: Int,
    @SerialName("weather") val weather: List<Weather>,
    @SerialName("clouds") val clouds: Int,
    @SerialName("pop") val pop: Double,
    @SerialName("uvi") val uvi: Double
)