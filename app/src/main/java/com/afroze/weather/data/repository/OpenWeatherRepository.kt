package com.afroze.weather.data.repository

import android.util.Log
import com.afroze.weather.data.persistance.WeatherDBRepo
import com.afroze.weather.data.response.Resource
import com.afroze.weather.data.response.weather.WeatherData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class OpenWeatherRepository @Inject constructor(
    private var apiService: OpenWeatherDataApi,
    private var dbService: WeatherDBRepo
) : BaseRepository() {

    val TAG = OpenWeatherRepository::class.java.simpleName

    val coroutineErrorHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, exception.printStackTrace().toString())
    }


    fun insertWeatherInfo(weatherInfo: WeatherData) {
        dbService.weatherInfoDao.insert(weatherInfo)
    }

    fun getOfflineWeatherInfo(): Flow<Resource.Success<WeatherData>> {
        return flow {
            dbService.weatherInfoDao.fetchOfflineWeatherAsFlow().collect {
                emit(Resource.Success(it))
            }
        }
    }

    suspend fun getWeather(
        lat: String,
        lon: String,
        app_id: String
    ) = safeApiCall {
        apiService.getPost(lat, lon, app_id)
    }
}
