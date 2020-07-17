package com.sunday.sustainweatherforcast.data.network

import androidx.lifecycle.LiveData
import com.sunday.sustainweatherforcast.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
       location: String
    )
}