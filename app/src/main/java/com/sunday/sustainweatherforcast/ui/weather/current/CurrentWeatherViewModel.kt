package com.sunday.sustainweatherforcast.ui.weather.current

import androidx.lifecycle.ViewModel
import com.sunday.sustainweatherforcast.data.provider.UnitProvider
import com.sunday.sustainweatherforcast.data.repository.ForecastRepository
import com.sunday.sustainweatherforcast.internal.UnitSystem
import com.sunday.sustainweatherforcast.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider

) : ViewModel() {
    private val unitSystem = unitProvider.getUnitSystem()
    val isMetric: Boolean
    get() = unitSystem == UnitSystem.METRIC
    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}