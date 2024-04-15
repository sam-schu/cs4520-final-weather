package com.samschu.cs4520.weather.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun DayForecastScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "X-Day Forecast",
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
        )
        LazyColumn {
            items(6) { day ->
                DayForecast(
                    date = "1/1/2020",
                    conditions = "Sunny",
                    temperature = 75,
                    weatherIconUrl = "https://openweathermap.org/img/wn/01d.png"
                )
                Divider(
                    modifier = Modifier.padding(top = 3.dp, bottom = 3.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DayForecast(date: String, conditions: String, temperature: Int, weatherIconUrl: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = date,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Conditions: $conditions",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Temperature: $temperature°F",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        GlideImage(
            model = weatherIconUrl,
            contentDescription = "Weather Icon",
            modifier = Modifier.size(50.dp)
        )
    }
}



@Preview(showBackground = true)
@Composable
fun DayForecastScreenPreview() {
    DayForecastScreen()
}

@Preview(showBackground = true)
@Composable
fun DayForecast() {
    DayForecast(
        date = "1/1/2020",
        conditions = "Sunny",
        temperature = 75,
        weatherIconUrl = "https://openweathermap.org/img/wn/01d.png"
    )
}
