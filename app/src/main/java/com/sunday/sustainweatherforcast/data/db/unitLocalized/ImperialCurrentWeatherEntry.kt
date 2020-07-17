package com.sunday.sustainweatherforcast.data.db.unitLocalized

import androidx.room.ColumnInfo

data class ImperialCurrentWeatherEntry (
    override val temperature: Int,
    @ColumnInfo(name = "weather_descriptions")
    override val weatherDescriptions: List<String>,
    @ColumnInfo(name = "weather_icons")
    override val weatherIcons: List<String>,
    @ColumnInfo(name = "wind_speed")
    override val windSpeed: Int,
    @ColumnInfo(name = "wind_dir")
    override val windDir: String,
    override val precip: Int,
    override val feelslike: Int,
    override val visibility: Int

): UnitSpecificCurrentWeatherEntry