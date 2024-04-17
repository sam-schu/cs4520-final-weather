package com.samschu.cs4520.weather.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
<<<<<<< HEAD
import com.samschu.cs4520.weather.ui.HomeScreen
import com.samschu.cs4520.weather.ui.TodayWeatherDetailsScreen
import com.samschu.cs4520.weather.view.Destination


object Destination {
    const val HOME = "home"
    const val LOCATION = "location"
    const val HOURLY_FORECAST = "hourly_forecast"
    const val INFO = "info"
}


@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Destination.HOME) {
        composable(Destination.HOME) {
            HomeScreen(navController)
        }
        composable(Destination.LOCATION) {
            LocationScreen()
        }
        composable(Destination.HOURLY_FORECAST) {
            HourlyScreen()
        }
        composable(Destination.INFO) {
            TodayWeatherDetailsScreen()
        }
    }
}

=======
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
>>>>>>> 6f05b885e570d353d8d4ac5daf0438ebdfd4f3f6
