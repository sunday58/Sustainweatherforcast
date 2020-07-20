package com.sunday.sustainweatherforcast.ui.weather.current

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sunday.sustainweatherforcast.R
import com.sunday.sustainweatherforcast.internal.glide.GlideApp
import com.sunday.sustainweatherforcast.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: CurrentWeatherViewModelFactory  by instance()

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)
        bindUI()

    }

    private fun bindUI() = launch{
        val currentWeather = viewModel.weather.await()

        val weatherLocation = viewModel.weatherLocation.await()
        weatherLocation.observe(viewLifecycleOwner, Observer {location ->
            if (location == null) return@Observer
            updateLocation(location.name)
        })

        currentWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            group_loading.visibility = View.GONE
            updateDateToday()
            updateTemperature(it.temperature, it.feelslike)
            updateCondition(it.weatherDescriptions)
            updatePrecipitation(it.precip)
            updateWind(it.windDir, it.windSpeed)
            updateVisibility(it.visibility)

            val imageList: MutableList<String> = ArrayList()
            imageList.add(it.weatherIcons.toString())
            val imageBuilder = StringBuilder()
            for (imageValue in imageList){
                imageBuilder.append(imageValue)
            }
            val formattedImage = imageBuilder.toString()
                .replace("[","")
                .replace("]", "")
                .trim()

            GlideApp.with(this@CurrentWeatherFragment)
                .load(formattedImage)
                .into(imageView_con)
            Log.d("weatherIcon", formattedImage)
        })
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
      return  if (viewModel.isMetric) "\u2103" else "\u2109"
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperature(temperature: Double, feelsLike: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("\u2103","\u2109")
        textView_temperature.text = "$temperature$unitAbbreviation"
        textView_feels_like_temperature.text = "Feels like $feelsLike$unitAbbreviation"
    }

    private fun updateCondition(condition: List<String>) {

    val list: MutableList<String> = ArrayList()
        list.add(condition.toString())
        val builder = StringBuilder()
        for (value in list){
            builder.append(value)
        }
        val formattedString = builder.toString()
            .replace("[", "")
            .replace("]", "")
            .trim()


        textView_condition.text = formattedString
    }

    private fun updatePrecipitation(precipitationVolume: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        textView_precipitation.text = "Precipitation: $precipitationVolume mm"
    }

    private fun updateWind(windDirection: String, windSpeed: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph", "mph")
        textView_wind.text = "Wind: $windDirection, $windSpeed kph"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km", "mi")
        textView_visibility.text = "Visibility: $visibilityDistance km"
    }

}