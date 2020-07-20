package com.sunday.sustainweatherforcast.data.repository

import androidx.lifecycle.LiveData
import com.sunday.sustainweatherforcast.data.db.CurrentWeatherDao
import com.sunday.sustainweatherforcast.data.db.WeatherLocationDao
import com.sunday.sustainweatherforcast.data.db.entity.WeatherLocation
import com.sunday.sustainweatherforcast.data.db.unitLocalized.UnitSpecificCurrentWeatherEntry
import com.sunday.sustainweatherforcast.data.network.WeatherNetworkDataSource
import com.sunday.sustainweatherforcast.data.network.response.CurrentWeatherResponse
import com.sunday.sustainweatherforcast.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
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

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO) {
            return@withContext weatherLocationDao.getLocation()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upSert(fetchedWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }
    private suspend fun initWeatherData(){
        val lastWeatherLocation = weatherLocationDao.getLocation().value

        if (lastWeatherLocation == null
            || locationProvider.hasLocationChanged(lastWeatherLocation)) {
            fetchCurrentWeather()
            return
        }
        if (isFetchCurrentNeeded(lastWeatherLocation.zoneDateTime))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString()
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}