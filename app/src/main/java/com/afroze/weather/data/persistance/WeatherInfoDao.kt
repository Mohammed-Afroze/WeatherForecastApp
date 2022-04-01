package com.afroze.weather.data.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.afroze.weather.data.response.weather.WeatherData
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg weatherInfo: WeatherData)

    @Query("SELECT * FROM WeatherData")
    fun fetchOfflineWeatherAsFlow(): Flow<WeatherData>
}