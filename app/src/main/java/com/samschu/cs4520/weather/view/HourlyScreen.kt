package com.samschu.cs4520.weather.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun HourlyScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Hourly Weather for Today",
            fontSize = 30.sp,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
        )
        LazyColumn {
            items(30) {
                HourWeather()
                Divider(
                    modifier = Modifier
                        .padding(top = 3.dp, bottom = 3.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HourWeather() {
    val time = "2 PM"
    val conditions = "rainy"
    val precipChance = 0.6
    val temp = 61
    val fontSize = 22.sp
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = time,
            textAlign = TextAlign.Center,
            fontSize = fontSize,
            modifier = Modifier
                .weight(1f)
        )
        GlideImage(
            model = "https://openweathermap.org/img/wn/09d@2x.png",
            contentDescription = "sunny icon",
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
        )
        Text(
            text = if (precipChance > 0.0) {
                "(${(precipChance * 100).toInt()}%)"
            } else {
                ""
            },
            textAlign = TextAlign.Center,
            fontSize = fontSize,
            modifier = Modifier
                .weight(1f)
        )
        Text(
            text = "$temp°",
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
    HourWeather()
}
