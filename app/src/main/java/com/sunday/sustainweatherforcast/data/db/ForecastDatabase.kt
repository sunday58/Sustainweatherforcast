package com.sunday.sustainweatherforcast.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sunday.sustainweatherforcast.data.db.entity.CurrentWeatherEntry
import com.sunday.sustainweatherforcast.data.db.entity.CurrentWeatherEntryConverter
import com.sunday.sustainweatherforcast.data.db.entity.WeatherLocation


@Database(
    exportSchema = false,
    entities = [CurrentWeatherEntry::class, WeatherLocation::class],
    version = 6
)
@TypeConverters(CurrentWeatherEntryConverter::class)
abstract class ForecastDatabase : RoomDatabase() {
        abstract fun currentWeatherDao(): CurrentWeatherDao
        abstract fun weatherLocationDao(): WeatherLocationDao

    companion object {
       @Volatile private var instance: ForecastDatabase? = null
        private val LOCK = Any()

         operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
             instance ?: buildDatabase(context).also { instance = it }
         }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                    ForecastDatabase::class.java,
                    "forecast.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}