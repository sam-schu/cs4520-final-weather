package com.samschu.cs4520.weather.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel

class WeatherViewModel: ViewModel() {
    val currentLocationIndex = mutableIntStateOf(0)
}