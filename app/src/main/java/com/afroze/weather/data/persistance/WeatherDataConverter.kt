package com.afroze.weather.data.persistance

import androidx.room.TypeConverter
import com.afroze.weather.data.response.weather.Current
import com.afroze.weather.data.response.weather.Daily
import com.afroze.weather.data.response.weather.Hourly
import com.afroze.weather.data.response.weather.Minutely
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class WeatherDataConverter {

    @TypeConverter
    fun minutelyToList(json: String?): List<Minutely>? {
        return json?.let { Json.decodeFromString(ListSerializer(Minutely.serializer()), it) }
    }

    @TypeConverter
    fun minutelylistToString(list: List<Minutely?>?): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun hourlyToList(json: String?): List<Hourly>? {
        return json?.let { Json.decodeFromString(ListSerializer(Hourly.serializer()), it) }
    }

    @TypeConverter
    fun hourlylistToString(list: List<Hourly?>?): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun dailyToList(json: String?): List<Daily>? {
        return json?.let { Json.decodeFromString(ListSerializer(Daily.serializer()), it) }
    }

    @TypeConverter
    fun dailyToString(list: List<Daily?>?): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun stringToObj(json: String): Current {
        return json.let { Json.decodeFromString(Current.serializer(), it) }
    }

    @TypeConverter
    fun objToString(current: Current): String {
        return Json.encodeToString(current)
    }
}