package com.sunday.sustainweatherforcast.internal

import java.io.IOException
import java.lang.Exception

class NoConnectivityException: IOException()
class locationPermissionNotGrantedException: Exception()