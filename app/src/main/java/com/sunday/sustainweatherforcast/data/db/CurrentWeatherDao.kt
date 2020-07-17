package com.sunday.sustainweatherforcast.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sunday.sustainweatherforcast.data.db.entity.CurrentWeatherEntry
import com.sunday.sustainweatherforcast.data.db.unitLocalized.ImperialCurrentWeatherEntry
import com.sunday.sustainweatherforcast.data.db.unitLocalized.MetricCurrentWeatherEntry

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upSert(weatherEntry: CurrentWeatherEntry)

    @Query("SELECT * FROM current_weather")
    fun getWeatherMetric(): LiveData<MetricCurrentWeatherEntry>

    @Query("SELECT * FROM current_weather")
    fun getWeatherImperial(): LiveData<ImperialCurrentWeatherEntry>
}