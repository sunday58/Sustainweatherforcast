package com.sunday.sustainweatherforcast.data.db.unitLocalized


interface UnitSpecificCurrentWeatherEntry {
    val feelslike: Int
    val isDay: String
    val precip: Int
    val temperature: Int
    val visibility: Int
    val weatherCode: Int
    val weatherDescriptions: List<String>
    val weatherIcons: List<String>
    val windDir: String
    val windSpeed: Int
}