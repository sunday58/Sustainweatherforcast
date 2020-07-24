package com.sunday.sustainweatherforcast.data.network.response


import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("2020-07-23")
    val x20200723: X20200723
)