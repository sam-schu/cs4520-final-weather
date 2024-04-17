package com.samschu.cs4520.weather.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
<<<<<<< HEAD
import androidx.navigation.compose.rememberNavController
import com.samschu.cs4520.weather.ui.HomeScreen
=======
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
>>>>>>> 6f05b885e570d353d8d4ac5daf0438ebdfd4f3f6

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
<<<<<<< HEAD
            // Create a NavController
            val navController = rememberNavController()

            // Pass the NavController to HomeScreen
            HomeScreen(navController = navController)
=======
            AppNavHost(
                modifier = Modifier.fillMaxSize(),
                viewModel(),
                navController = rememberNavController()
            )
>>>>>>> 6f05b885e570d353d8d4ac5daf0438ebdfd4f3f6
        }
    }
}
