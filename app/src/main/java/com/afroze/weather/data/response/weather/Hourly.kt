package com.afroze.weather.data.response.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Hourly(

    @SerialName("dt") val dt : Int,
    @SerialName("temp") val temp : Double,
    @SerialName("feels_like") val feelsLike : Double,
    @SerialName("pressure") val pressure : Int,
    @SerialName("humidity") val humidity : Int,
    @SerialName("dew_point") val dewPoint : Double,
    @SerialName("clouds") val clouds : Int,
    @SerialName("visibility") val visibility : Int,
    @SerialName("wind_speed") val windSpeed : Double,
    @SerialName("wind_deg") val windDeg : Int,
    @SerialName("weather") val weather : List<Weather>,
    @SerialName("pop") val pop : Double,
    @SerialName("wind_gust") val wind_gust : Double
)