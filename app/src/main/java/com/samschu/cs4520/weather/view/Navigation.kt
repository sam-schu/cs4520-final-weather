package com.samschu.cs4520.weather.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.samschu.cs4520.weather.viewmodel.WeatherViewModel

enum class Screen {
    LOCATION,
    HOURLY_FORECAST
}

sealed class NavigationItem(val route: String) {
    data object Location : NavigationItem(Screen.LOCATION.name)
    data object HourlyForecast : NavigationItem(Screen.HOURLY_FORECAST.name)
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel,
    navController: NavHostController,
    startDestination: String = NavigationItem.HourlyForecast.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Location.route) {
            LocationScreen(viewModel)
        }
        composable(NavigationItem.HourlyForecast.route) {
            HourlyScreen(viewModel)
        }
    }
}