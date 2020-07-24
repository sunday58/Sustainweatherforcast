package com.sunday.sustainweatherforcast.data.network.response


import com.google.gson.annotations.SerializedName
import com.sunday.sustainweatherforcast.data.db.entity.WeatherLocation

data class FutureWeatherResponse(
    val forecast: Forecast,
    val location: WeatherLocation
)