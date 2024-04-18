package com.samschu.cs4520.weather.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import androidx.compose.foundation.Image
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.samschu.cs4520.weather.R
import com.samschu.cs4520.weather.viewmodel.WeatherDataResult
import com.samschu.cs4520.weather.viewmodel.WeatherViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: WeatherViewModel = viewModel()
) {
    val currentWeatherData = viewModel.currentWeatherData.value
    val dailyWeatherData = viewModel.dailyWeatherData.value
    val locationOptions = stringArrayResource(R.array.location_options)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Home Screen",
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )

        // Trigger navigation to the Location screen when LocationSection is clicked
        LocationSection(location = locationOptions[viewModel.currentLocationIndex.intValue]) {
            navController.navigate(NavigationItem.Location.route)
        }


        // Trigger navigation to the TodayWeatherDetailsScreen when TodayInfoBox is clicked
        TodayInfoBox {
            navController.navigate(NavigationItem.Details.route)
        }

        when {
            currentWeatherData is WeatherDataResult.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            currentWeatherData is WeatherDataResult.Error -> {
                Text(
                    text = "Error loading weather data.",
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
            currentWeatherData is WeatherDataResult.Success && dailyWeatherData is WeatherDataResult.Success -> {
                val currentWeather = currentWeatherData.data
                val dailyWeather = dailyWeatherData.data.firstOrNull()

                if (dailyWeather != null) {
                    WeatherBox(
                        conditions = currentWeather.weather[0].main,
                        currTemp = currentWeather.temp.toInt(),
                        maxTemp = dailyWeather.temp.max.toInt(),
                        minTemp = dailyWeather.temp.min.toInt(),
                        feelsLikeTemp = currentWeather.feelsLike.toInt(),
                        weatherIconUrl = "https://openweathermap.org/img/wn/${currentWeather.weather[0].icon}.png",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        // Trigger navigation to the HourlyScreen when ForecastBox is clicked
        ForecastBox {
            navController.navigate(NavigationItem.HourlyForecast.route)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TodayWeather(
    conditions: String,
    currTemp: Int,
    feelsLikeTemp: Int,
    minTemp: Int,
    maxTemp: Int,
    weatherIconUrl: String,
    modifier: Modifier = Modifier
) {
    if (conditions.isNotEmpty() && currTemp != 0 && feelsLikeTemp != 0 && minTemp != 0 && maxTemp != 0 && weatherIconUrl.isNotEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(16.dp)
                .background(color = Color.Blue.copy(alpha = 0.5f), shape = MaterialTheme.shapes.medium)
                .padding(16.dp)
        ) {
            // Display today's weather conditions
            Text(
                text = "Today's Weather: $conditions",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Display current temperature
            Text(
                text = "Current Temperature: $currTemp°F",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Display weather icon using GlideImage
            GlideImage(
                model = weatherIconUrl,
                contentDescription = "Weather Icon",
                modifier = Modifier.size(100.dp)
            )

            // Display "Feels Like" temperature
            Text(
                text = "Feels Like: $feelsLikeTemp°F",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )

            // Display minimum and maximum temperature
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = "Min Temp: $minTemp°F",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "Max Temp: $maxTemp°F",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    }
}



@Composable
fun WeatherBox(
    conditions: String,
    currTemp: Int,
    minTemp: Int,
    maxTemp: Int,
    feelsLikeTemp: Int,
    weatherIconUrl: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.medium
    ) {
        TodayWeather(
            conditions = conditions,
            currTemp = currTemp,
            minTemp = minTemp,
            maxTemp = maxTemp,
            feelsLikeTemp = feelsLikeTemp,
            weatherIconUrl = weatherIconUrl
        )
    }
}


@Composable
fun TodayInfoBox(onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Surface(
            color = Color(0xFFFFA500), // Orange color
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .size(50.dp) // Set the size of the box to match the image dimensions
                .clickable(onClick = onClick)
                .padding(16.dp)
                .testTag("info icon")
        ) {
            Image(
                painter = painterResource(id = R.drawable.info),
                contentDescription = null
            )
        }
    }
}






@Composable
fun LocationSection(location: String, onClick: () -> Unit) {
    Surface(
        color = Color(0xFF87CEEB), // Sky blue color
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 20.dp)
        ) {
            Text(
                text = "Location: $location",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.magnifying_glass),
                contentDescription = "Search",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}




@Composable
fun ForecastBox(onClick: () -> Unit) {
    Surface(
        color = Color.LightGray,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(
            text = "48-HR Forecast",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    // Create a NavHostController instance
    val navController = rememberNavController()

    // Pass the NavHostController to the HomeScreen composable
    HomeScreen(navController = navController)
}