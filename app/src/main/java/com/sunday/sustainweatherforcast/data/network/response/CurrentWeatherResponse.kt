package com.sunday.sustainweatherforcast.data.network.response

import com.google.gson.annotations.SerializedName
import com.sunday.sustainweatherforcast.data.db.entity.CurrentWeatherEntry
import com.sunday.sustainweatherforcast.data.db.entity.WeatherLocation
import com.sunday.sustainweatherforcast.data.db.entity.Request


data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: WeatherLocation,
    val request: Request
)