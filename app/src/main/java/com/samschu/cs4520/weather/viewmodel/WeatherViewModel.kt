package com.samschu.cs4520.weather.viewmodel

import androidx.compose.runtime.IntState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.samschu.cs4520.weather.model.ApiService
import com.samschu.cs4520.weather.model.CurrentWeatherData
import com.samschu.cs4520.weather.model.DailyWeatherData
import com.samschu.cs4520.weather.model.HourlyWeatherData
import com.samschu.cs4520.weather.model.Repo
import com.samschu.cs4520.weather.model.RetrofitBuilder
import com.samschu.cs4520.weather.model.WeatherData
import com.samschu.cs4520.weather.model.WeatherDataEntity
import com.samschu.cs4520.weather.model.WeatherRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

data class FormattedHourlyWeatherData(
    val time: String,
    val conditionsIconId: String,
    val precipChance: Double,
    val temp: Double
)

sealed interface WeatherDataResult<out T> {
    data object Loading : WeatherDataResult<Nothing>
    data class Success<out T>(val data: T) : WeatherDataResult<T>
    data object Error : WeatherDataResult<Nothing>
}

class WeatherViewModel(private val repo: WeatherRepo = Repo()) : ViewModel() {
    private val _currentLocationIndex = mutableIntStateOf(0)
    private val _currentWeatherData = mutableStateOf<WeatherDataResult<CurrentWeatherData>>(
        WeatherDataResult.Loading
    )
    private val _hourlyWeatherData =
        mutableStateOf<WeatherDataResult<List<FormattedHourlyWeatherData>>>(
            WeatherDataResult.Loading
        )
    private val _dailyWeatherData = mutableStateOf<WeatherDataResult<List<DailyWeatherData>>>(
        WeatherDataResult.Loading
    )

    val currentLocationIndex: IntState = _currentLocationIndex
    val currentWeatherData: State<WeatherDataResult<CurrentWeatherData>> =
        _currentWeatherData
    val hourlyWeatherData: State<WeatherDataResult<List<FormattedHourlyWeatherData>>> =
        _hourlyWeatherData
    val dailyWeatherData: State<WeatherDataResult<List<DailyWeatherData>>> =
        _dailyWeatherData

    // Add a property to hold WeatherData
    private val _weatherData = mutableStateOf<WeatherData?>(null)
    val weatherData: State<WeatherData?> = _weatherData

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
                val (lat, lon) = apiService.getCoordinates(locationSpecifier).first()
                val weatherData = apiService.getWeatherData(lat, lon)

                withContext(Dispatchers.Main) {
                    _weatherData.value = weatherData
                    _currentWeatherData.value = WeatherDataResult.Success(weatherData.current)
                    _hourlyWeatherData.value = WeatherDataResult.Success(
                        weatherData.hourly.map {
                            formatHourlyWeatherData(it)
                        }
                    )
                    _dailyWeatherData.value = WeatherDataResult.Success(weatherData.daily)
                }
                repo.replaceStoredWeatherData(WeatherDataEntity(
                    serializedString = Gson().toJson(weatherData)
                ))
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    updateAllWeatherData(WeatherDataResult.Error)
                }
            } catch (e: UnknownHostException) {
                // Handle device offline
                val weatherDataEntity = repo.getStoredWeatherData()
                if (weatherDataEntity.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        updateAllWeatherData(WeatherDataResult.Error)
                    }
                } else {
                    val weatherData = weatherDataEntity.first().serializedString?.let {
                        Gson().fromJson(it, WeatherData::class.java)
                    }
                    if (weatherData == null) {
                        withContext(Dispatchers.Main) {
                            updateAllWeatherData(WeatherDataResult.Error)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            _weatherData.value = weatherData
                            _currentWeatherData.value = WeatherDataResult.Success(weatherData.current)
                            _hourlyWeatherData.value = WeatherDataResult.Success(
                                weatherData.hourly.map {
                                    formatHourlyWeatherData(it)
                                }
                            )
                            _dailyWeatherData.value = WeatherDataResult.Success(weatherData.daily)
                        }
                    }
                }
            }
        }
    }

    private fun formatHourlyWeatherData(
        wd: HourlyWeatherData
    ): FormattedHourlyWeatherData {
        // Access timezoneOffset from WeatherData
        val timezoneOffset = _weatherData.value?.timezoneOffset ?: 0

        return FormattedHourlyWeatherData(
            getFormattedTime(wd.dt + timezoneOffset),
            wd.weather.first().icon,
            wd.pop,
            wd.temp
        )
    }

    // Returns a string in the device locale in the form of "12 AM"
    // or "1 PM" representing the given UNIX timestamp (in seconds),
    // without performing any time zone conversions.
    private fun getFormattedTime(unixTimestampSeconds: Int): String =
        // Note: The time zone is not set to UTC because we want the
        // time to be displayed in UTC, but because this will disable
        // automatic time zone conversion (since the Date class
        // constructor takes a timestamp assumed to be in UTC). This
        // way, the time zone of the time provided to the method is
        // preserved.
        Date(unixTimestampSeconds * 1000L).let {
            SimpleDateFormat("h a", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }.format(it)
        }

    // same as getFormatterTime but we provide a more detailed time for purpose of the in-depth
    // details for the info screen
    fun detailedFormatTime(unixTimestampSeconds: Int): String =
        Date(unixTimestampSeconds * 1000L).let {
            SimpleDateFormat("h:mm:ss a", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }.format(it)
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
            "Boston", "New York", "San Francisco", "Sao Paulo",
            "London", "Paris", "Rome", "Cape Town", "Tokyo", "Sydney"
        )
    }
}
