package com.afroze.weather.data.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.afroze.weather.data.response.weather.WeatherData

@Database(entities = [WeatherData::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherInfoDao(): WeatherInfoDao
}