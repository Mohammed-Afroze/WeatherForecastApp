package com.afroze.weather.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afroze.weather.data.repository.OpenWeatherRepository
import com.afroze.weather.data.response.Resource
import com.afroze.weather.data.response.weather.WeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@HiltViewModel
class DashBoardViewModel @Inject constructor(val repository: OpenWeatherRepository) : ViewModel() {

    private val TAG = DashBoardViewModel::class.java.simpleName
    private val _weatherResponse: MutableLiveData<Resource<WeatherData>> = MutableLiveData()
    val weatherResponse: LiveData<Resource<WeatherData>>
        get() = _weatherResponse


    fun getWeather(
        lat: String,
        lon: String,
        app_id: String
    ) = viewModelScope.launch {
        _weatherResponse.value = repository.getWeather(lat, lon, app_id)
    }

    fun getOfflineData() {
        viewModelScope.launch {
            repository.getOfflineWeatherInfo().collect { result ->
                _weatherResponse.value = result
            }
        }

    }
}

