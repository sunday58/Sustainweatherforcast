package com.sunday.sustainweatherforcast.data.provider

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.sunday.sustainweatherforcast.data.db.entity.WeatherLocation
import com.sunday.sustainweatherforcast.internal.asDeferred
import com.sunday.sustainweatherforcast.internal.locationPermissionNotGrantedException
import kotlinx.coroutines.Deferred


const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"

class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context
) : PreferenceProvider(context), LocationProvider {
    private val appContext = context.applicationContext

    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        val deviceLocationChanged = try {
            hasDeviceLocationChanged(lastWeatherLocation)
        }catch (e: locationPermissionNotGrantedException){
            return false
        }

        return deviceLocationChanged || hasCustomLocationChanged(lastWeatherLocation)
    }

    override suspend fun getPreferredLocationString(): String {
       if (isUsingDeviceLocation()) {
            try {
                val deviceLocation = getLastDeviceLocation().await()
                    ?: return "${getCustomLocationName()}"
                return "${deviceLocation.latitude}, ${deviceLocation.longitude}"
            }catch (e: locationPermissionNotGrantedException){
                return "${getCustomLocationName()}"
            }
       }
        else
           return "${getCustomLocationName()}"
    }
    private suspend fun hasDeviceLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        if (!isUsingDeviceLocation())
            return false

        val deviceLocation = getLastDeviceLocation().await()
            ?: return false

        val comparisonThreshold = 0.03
        return Math.abs(deviceLocation.latitude - lastWeatherLocation.lat.toDouble()) > comparisonThreshold &&
                Math.abs(deviceLocation.longitude - lastWeatherLocation.lon.toDouble()) > comparisonThreshold
    }

    private fun hasCustomLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        val customLocationName = getCustomLocationName()
        return customLocationName != lastWeatherLocation.name
    }

    private fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(USE_DEVICE_LOCATION, true)
    }
    private fun getCustomLocationName(): String? {
        return preferences.getString(CUSTOM_LOCATION, null)
    }

    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocation(): Deferred<Location?> {
       return if (hasLocationPermission())
           fusedLocationProviderClient.lastLocation.asDeferred()
        else
           throw locationPermissionNotGrantedException()
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(appContext,
        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}