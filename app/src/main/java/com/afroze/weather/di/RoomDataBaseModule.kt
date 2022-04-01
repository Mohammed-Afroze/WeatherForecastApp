package com.afroze.weather.di

import android.content.Context
import androidx.room.Room
import com.afroze.weather.data.persistance.WeatherDBRepo
import com.afroze.weather.data.persistance.WeatherDatabase
import com.afroze.weather.data.persistance.WeatherInfoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object RoomDataBaseModule {

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        WeatherDatabase::class.java,
        "weather.db"
    ).build()

    @Provides
    @Singleton
    fun provideRouteDBRepo(weatherInfoDao: WeatherInfoDao): WeatherDBRepo {
        return WeatherDBRepo(weatherInfoDao)
    }


    @Provides
    @Singleton
    fun provideWeatherInfoDao(db: WeatherDatabase): WeatherInfoDao = db.weatherInfoDao()
}