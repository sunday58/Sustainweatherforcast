package com.sunday.sustainweatherforcast.data.db.unitLocalized

data class MetricCurrentWeatherEntry (

    override val feelslike: Int,
    override val isDay: String,
    override val precip: Int,
    override val temperature: Int,
    override val visibility: Int,
    override val weatherCode: Int,
    override val weatherDescriptions: List<String>,
    override val weatherIcons: List<String>,
    override val windDir: String,
    override val windSpeed: Int


): UnitSpecificCurrentWeatherEntry