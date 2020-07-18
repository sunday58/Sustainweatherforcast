package com.sunday.sustainweatherforcast.data.db.unitLocalized


interface UnitSpecificCurrentWeatherEntry {
    val feelslike: Double
    val isDay: String
    val precip: Double
    val temperature: Double
    val visibility: Double
    val weatherCode: Double
    val weatherDescriptions: List<String>
    val weatherIcons: List<String>
    val windDir: String
    val windSpeed: Double
}