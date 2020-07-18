package com.sunday.sustainweatherforcast.data.db.unitLocalized

import androidx.room.ColumnInfo


data class ImperialCurrentWeatherEntry (
    override val feelslike: Double,
    override val isDay: String,
    override val precip: Double,
    override val temperature: Double,
    override val visibility: Double,
    override val weatherCode: Double,
    override val weatherDescriptions: List<String>,
    override val weatherIcons: List<String>,
    override val windDir: String,
    override val windSpeed: Double

): UnitSpecificCurrentWeatherEntry