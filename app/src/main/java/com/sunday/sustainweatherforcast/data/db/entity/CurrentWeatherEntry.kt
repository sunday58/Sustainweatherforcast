package com.sunday.sustainweatherforcast.data.db.entity


import androidx.room.*
import com.google.gson.annotations.SerializedName


@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(
    @PrimaryKey(autoGenerate = true)
    val feelslike: Int,
    @SerializedName("is_day")
    val isDay: String,
    val precip: Int,
    val temperature: Int,
    val visibility: Int,
    @SerializedName("weather_code")
    val weatherCode: Int,
    @TypeConverters(CurrentWeatherEntryConverter::class)
    @SerializedName("weather_descriptions")
    val weatherDescriptions: List<String>,
    @TypeConverters(CurrentWeatherEntryConverter::class)
    @SerializedName("weather_icons")
    val weatherIcons: List<String>,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_speed")
    val windSpeed: Int
) {

}