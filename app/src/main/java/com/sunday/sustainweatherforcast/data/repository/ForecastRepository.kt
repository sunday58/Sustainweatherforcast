package com.sunday.sustainweatherforcast.data.repository

import androidx.lifecycle.LiveData
import com.sunday.sustainweatherforcast.data.db.entity.WeatherLocation
import com.sunday.sustainweatherforcast.data.db.unitLocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}