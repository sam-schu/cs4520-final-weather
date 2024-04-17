package com.samschu.cs4520.weather.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.samschu.cs4520.weather.viewmodel.FormattedHourlyWeatherData
import com.samschu.cs4520.weather.viewmodel.WeatherDataResult
import com.samschu.cs4520.weather.viewmodel.WeatherViewModel
import kotlin.math.roundToInt

@Composable
fun HourlyScreen(
    vm: WeatherViewModel = viewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "48-Hour Forecast",
            fontSize = 30.sp,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
        )
        when (val hourlyData = vm.hourlyWeatherData.value) {
            is WeatherDataResult.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            is WeatherDataResult.Error -> {
                Text(
                    text = "There was an error loading forecast data.",
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp
                )
            }
            is WeatherDataResult.Success -> {
                LazyColumn {
                    items(hourlyData.data) { hourData ->
                        HourWeather(hourData)
                        Divider(
                            modifier = Modifier
                                .padding(top = 3.dp, bottom = 3.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HourWeather(hourData: FormattedHourlyWeatherData) {
    val fontSize = 22.sp
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = hourData.time,
            textAlign = TextAlign.Center,
            fontSize = fontSize,
            modifier = Modifier
                .weight(1f)
        )
        GlideImage(
            model = "https://openweathermap.org/img/wn/${hourData.conditionsIconId}@2x.png",
            contentDescription = "sunny icon",
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
        )
        Text(
            text = "${(hourData.precipChance * 100).toInt()}%",
            textAlign = TextAlign.Center,
            fontSize = fontSize,
            modifier = Modifier
                .weight(1f)
        )
        Text(
            text = "${hourData.temp.roundToInt()}°",
            textAlign = TextAlign.Center,
            fontSize = fontSize,
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HourlyScreenPreview() {
    HourlyScreen()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HourWeatherPreview() {
    HourWeather(
        FormattedHourlyWeatherData(
        "2 PM",
        "10d",
        0.6,
        61.0
    ))
}
