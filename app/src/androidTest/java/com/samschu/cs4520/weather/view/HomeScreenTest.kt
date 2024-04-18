package com.samschu.cs4520.weather.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUpTestRule() {
        composeTestRule.setContent {
            AppNavHost(
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel(),
                navController = rememberNavController()
            )
        }
    }

    @Test
    fun testLocationButton() {
        composeTestRule.onNodeWithText("Home Screen").assertExists()
        composeTestRule.onNodeWithText("Current Location:").assertDoesNotExist()
        composeTestRule.onNodeWithText("Location", substring = true).performClick()
        composeTestRule.onNodeWithText("Home Screen").assertDoesNotExist()
        composeTestRule.onNodeWithText("Current Location:").assertExists()
    }

    @Test
    fun testDetailsButton() {
        composeTestRule.onNodeWithText("Home Screen").assertExists()
        composeTestRule.onNodeWithText("Today's In-Depth Weather Details").assertDoesNotExist()
        composeTestRule.onNodeWithTag("info icon").performClick()
        composeTestRule.onNodeWithText("Home Screen").assertDoesNotExist()
        composeTestRule.onNodeWithText("Today's In-Depth Weather Details").assertExists()
    }

    @Test
    fun testHourlyForecastButton() {
        composeTestRule.onNodeWithText("Home Screen").assertExists()
        composeTestRule.onNodeWithText("48-Hour Forecast").assertDoesNotExist()
        composeTestRule.onNodeWithText("48-HR Forecast", substring = true).performClick()
        composeTestRule.onNodeWithText("Home Screen").assertDoesNotExist()
        composeTestRule.onNodeWithText("48-Hour Forecast").assertExists()
    }
}