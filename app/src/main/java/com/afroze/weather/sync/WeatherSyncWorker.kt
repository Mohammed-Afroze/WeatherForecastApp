package com.afroze.weather.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.afroze.weather.data.repository.OpenWeatherRepository
import com.afroze.weather.data.response.Resource
import com.afroze.weather.data.response.weather.WeatherData
import com.google.android.gms.location.LocationServices
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@HiltWorker
class WeatherSyncWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val openWeatherRepository: OpenWeatherRepository
) : CoroutineWorker(appContext, workerParams) {

    private val TAG = WeatherSyncWorker::class.java.canonicalName

    override suspend fun doWork(): Result {

        try {
            val fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(appContext)
            fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val lastKnownLocation = task.result
                    CoroutineScope(Dispatchers.IO).launch {
                        val result: Resource<WeatherData> =
                            openWeatherRepository.getWeather(
                                lastKnownLocation.latitude.toString(),
                                lastKnownLocation.longitude.toString(),
                                "87b29ca3d4018e56f727189384f24a29"
                            )
                        Log.d(TAG, "Result Got")
                        if(result is Resource.Success){
                            openWeatherRepository.insertWeatherInfo(result.value)
                        }
                        Log.d(TAG, "Result Insert")
                    }
                }
            }

        } catch (securityException: SecurityException) {
            Log.d(TAG, "securityException")
        }
        Log.d(TAG, "Result.success()")
        return Result.success()
    }
}
