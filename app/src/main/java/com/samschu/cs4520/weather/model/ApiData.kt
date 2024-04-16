package com.samschu.cs4520.weather.model

import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("timezone_offset") val timezoneOffset: Int,
    val current: CurrentWeatherData,
    val hourly: List<HourlyWeatherData>,
    val daily: List<DailyWeatherData>
)

data class WeatherDescription(
    val main: String,
    val description: String,
    val icon: String
)

data class CurrentWeatherData(
    val dt: Int,
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    val humidity: Int,
    val uvi: Double,
    @SerializedName("wind_speed") val windSpeed: Double,
    val weather: List<WeatherDescription>
)

data class HourlyWeatherData(
    val dt: Int,
    val temp: Double,
    val weather: List<WeatherDescription>,
    val pop: Double
)

data class DailyWeatherData(
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: DailyTemperatures,
    val weather: List<WeatherDescription>
)

data class DailyTemperatures(
    val min: Double,
    val max: Double
)

data class LocationCoordinates(
    val lat: Double,
    val lon: Double
)