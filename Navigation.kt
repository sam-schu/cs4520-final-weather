package com.samschu.cs4520.weather.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.samschu.cs4520.weather.viewmodel.WeatherViewModel

enum class Screen {
    HOME,
    DETAILS,
    LOCATION,
    HOURLY_FORECAST
}

sealed class NavigationItem(val route: String) {
    data object Home : NavigationItem(Screen.HOME.name)
    data object Details : NavigationItem(Screen.DETAILS.name)
    data object Location : NavigationItem(Screen.LOCATION.name)
    data object HourlyForecast : NavigationItem(Screen.HOURLY_FORECAST.name)
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel,
    navController: NavHostController,
    startDestination: String = NavigationItem.Home.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Home.route) {
            HomeScreen(navController, viewModel)
        }
        composable(NavigationItem.Details.route) {
            TodayWeatherDetailsScreen(viewModel)
        }
        composable(NavigationItem.Location.route) {
            LocationScreen(viewModel)
        }
        composable(NavigationItem.HourlyForecast.route) {
            HourlyScreen(viewModel)
        }
    }
}