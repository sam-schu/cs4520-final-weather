package com.samschu.cs4520.weather.viewmodel

import androidx.compose.runtime.IntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samschu.cs4520.weather.model.ApiService
import com.samschu.cs4520.weather.model.CurrentWeatherData
import com.samschu.cs4520.weather.model.DailyWeatherData
import com.samschu.cs4520.weather.model.HourlyWeatherData
import com.samschu.cs4520.weather.model.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

sealed interface WeatherDataResult<out T> {
    data object Loading : WeatherDataResult<Nothing>
    data class Success<out T>(val data: T) : WeatherDataResult<T>
    data object Error : WeatherDataResult<Nothing>
}

class WeatherViewModel: ViewModel() {
    private val _currentLocationIndex = mutableIntStateOf(0)
    private val _currentWeatherData = mutableStateOf<WeatherDataResult<CurrentWeatherData>>(
        WeatherDataResult.Loading
    )
    private val _hourlyWeatherData = mutableStateOf<WeatherDataResult<List<HourlyWeatherData>>>(
        WeatherDataResult.Loading
    )
    private val _dailyWeatherData = mutableStateOf<WeatherDataResult<List<DailyWeatherData>>>(
        WeatherDataResult.Loading
    )

    val currentLocationIndex: IntState = _currentLocationIndex
    val currentWeatherData: MutableState<WeatherDataResult<CurrentWeatherData>> =
        _currentWeatherData
    val hourlyWeatherData: MutableState<WeatherDataResult<List<HourlyWeatherData>>> =
        _hourlyWeatherData
    val dailyWeatherData: MutableState<WeatherDataResult<List<DailyWeatherData>>> =
        _dailyWeatherData

    init {
        loadWeatherData()
    }

    fun updateCurrentLocation(newLocationIndex: Int) {
        _currentLocationIndex.intValue = newLocationIndex
        loadWeatherData()
    }

    fun loadWeatherData() {
        updateAllWeatherData(WeatherDataResult.Loading)
        val apiService = RetrofitBuilder.getRetrofit().create(ApiService::class.java)
        val locationSpecifier = LOCATION_SPECIFIERS[_currentLocationIndex.intValue]
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val (lat, lon) = apiService.getCoordinates(
                    locationSpecifier
                )[0]
                val weatherData = apiService.getWeatherData(lat, lon)
                withContext(Dispatchers.Main) {
                    _currentWeatherData.value = WeatherDataResult.Success(weatherData.current)
                    _hourlyWeatherData.value = WeatherDataResult.Success(weatherData.hourly)
                    _dailyWeatherData.value = WeatherDataResult.Success(weatherData.daily)
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    updateAllWeatherData(WeatherDataResult.Error)
                }
            } catch (e: UnknownHostException) {
                // TODO: handle device offline using database
                withContext(Dispatchers.Main) {
                    updateAllWeatherData(WeatherDataResult.Error)
                }
            }
        }
    }

    private fun updateAllWeatherData(value: WeatherDataResult<Nothing>) {
        _currentWeatherData.value = value
        _hourlyWeatherData.value = value
        _dailyWeatherData.value = value
    }

    companion object {
        // Must correspond with the options for selecting a new location
        // (in the same order)
        val LOCATION_SPECIFIERS = arrayOf(
            "Boston", "New York", "London"
        )
    }
}