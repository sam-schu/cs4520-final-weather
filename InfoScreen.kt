package com.samschu.cs4520.weather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.samschu.cs4520.weather.viewmodel.WeatherDataResult
import com.samschu.cs4520.weather.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TodayWeatherDetailsScreen(vm: WeatherViewModel = viewModel()) {
    val currentWeatherData = vm.currentWeatherData.value
    val dailyWeatherData = vm.dailyWeatherData.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Today's In-Depth Weather Details",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
        )

        when {
            currentWeatherData is WeatherDataResult.Loading || dailyWeatherData is WeatherDataResult.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            currentWeatherData is WeatherDataResult.Error || dailyWeatherData is WeatherDataResult.Error -> {
                Text(
                    text = "There was an error loading weather data.",
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp
                )
            }
            currentWeatherData is WeatherDataResult.Success && dailyWeatherData is WeatherDataResult.Success -> {
                val currentWeather = currentWeatherData.data
                val dailyWeather = dailyWeatherData.data.firstOrNull()

                if (dailyWeather != null) {
                    WeatherDetailsBox(
                        temperature = currentWeather.temp,
                        feelsLikeTemperature = currentWeather.feelsLike,
                        humidity = currentWeather.humidity,
                        uvIndex = currentWeather.uvi,
                        windSpeed = currentWeather.windSpeed,
                        sunriseTime = formatTime(dailyWeather.sunrise),
                        sunsetTime = formatTime(dailyWeather.sunset),
                        maxTemperature = dailyWeather.temp.max,
                        minTemperature = dailyWeather.temp.min,
                        weatherIconUrl = "https://openweathermap.org/img/wn/${currentWeather.weather[0].icon}@2x.png",
                        weatherDescription = currentWeather.weather[0].description // Pass weather description
                    )
                } else {
                    Text(
                        text = "No daily weather data available.",
                        textAlign = TextAlign.Center,
                        fontSize = 22.sp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WeatherDetailsBox(
    temperature: Double,
    feelsLikeTemperature: Double,
    humidity: Int,
    uvIndex: Double,
    windSpeed: Double,
    sunriseTime: String,
    sunsetTime: String,
    maxTemperature: Double,
    minTemperature: Double,
    weatherIconUrl: String,
    weatherDescription: String // Add weather description parameter
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(color = Color.LightGray)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                model = weatherIconUrl,
                contentDescription = "Weather icon",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(16.dp)) // Add spacing between image and text
            Column {
                Text(
                    text = "Temperature: $temperature °F",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Feels Like: $feelsLikeTemperature °F",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Humidity: $humidity%",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "UV Index: $uvIndex",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Wind Speed: $windSpeed m/s",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Sunrise Time: $sunriseTime",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Sunset Time: $sunsetTime",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Max Temperature: $maxTemperature °F",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Min Temperature: $minTemperature °F",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Description: $weatherDescription", // Display weather description
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}

fun formatTime(unixTimestamp: Int): String {
    val date = Date(unixTimestamp * 1000L)
    val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
    return sdf.format(date)
}

@Preview(showBackground = true)
@Composable
fun TodayWeatherDetailsScreenPreview() {
    TodayWeatherDetailsScreen()
}

@Preview(showBackground = true)
@Composable
fun WeatherDetailsBoxPreview() {
    WeatherDetailsBox(
        temperature = 75.0,
        feelsLikeTemperature = 78.5,
        humidity = 70,
        uvIndex = 5.8,
        windSpeed = 10.2,
        sunriseTime = "6:30 AM",
        sunsetTime = "7:45 PM",
        maxTemperature = 80.0,
        minTemperature = 60.0,
        weatherIconUrl = "https://openweathermap.org/img/wn/01d.png",
        weatherDescription = "Clear Sky"
    )
}
