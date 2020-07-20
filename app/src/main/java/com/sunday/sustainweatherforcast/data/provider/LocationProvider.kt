package com.sunday.sustainweatherforcast.data.provider

import com.sunday.sustainweatherforcast.data.db.entity.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferredLocationString(): String
}