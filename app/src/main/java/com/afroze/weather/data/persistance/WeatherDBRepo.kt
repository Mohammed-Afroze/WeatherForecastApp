package com.afroze.weather.data.persistance

import javax.inject.Inject

class WeatherDBRepo @Inject constructor(val weatherInfoDao: WeatherInfoDao){
    private val mTAG = WeatherDBRepo::class.java.simpleName
}