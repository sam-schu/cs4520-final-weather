package com.samschu.cs4520.weather.model

interface WeatherRepo {
    fun replaceStoredWeatherData(data: WeatherDataEntity)

    fun getStoredWeatherData(): List<WeatherDataEntity>?
}