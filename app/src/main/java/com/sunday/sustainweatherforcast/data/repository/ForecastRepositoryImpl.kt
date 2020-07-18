package com.sunday.sustainweatherforcast.data.repository

import androidx.lifecycle.LiveData
import com.sunday.sustainweatherforcast.data.db.CurrentWeatherDao
import com.sunday.sustainweatherforcast.data.db.unitLocalized.UnitSpecificCurrentWeatherEntry
import com.sunday.sustainweatherforcast.data.network.WeatherNetworkDataSource
import com.sunday.sustainweatherforcast.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever {newCurrentWeather ->
            //persist current weather
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }


    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
      return  withContext(Dispatchers.IO) {
          initWeatherData()
          return@withContext if (metric) currentWeatherDao.getWeatherMetric()
          else currentWeatherDao.getWeatherImperial()
      }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upSert(fetchedWeather.currentWeatherEntry)
        }
    }
    private suspend fun initWeatherData(){
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(
            "Lagos"
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}