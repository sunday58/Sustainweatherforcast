package com.sunday.sustainweatherforcast.data.provider

import com.sunday.sustainweatherforcast.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}