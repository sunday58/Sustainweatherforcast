package com.sunday.sustainweatherforcast.ui.weather.current

import androidx.lifecycle.ViewModel
import com.sunday.sustainweatherforcast.data.repository.ForecastRepository
import com.sunday.sustainweatherforcast.internal.UnitSystem
import com.sunday.sustainweatherforcast.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    private val unitSystem = UnitSystem.METRIC
    val isMetric: Boolean
    get() = unitSystem == UnitSystem.METRIC
    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}