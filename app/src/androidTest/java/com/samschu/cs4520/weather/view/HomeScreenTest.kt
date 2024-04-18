package com.samschu.cs4520.weather.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
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
            HomeScreen(rememberNavController())
        }
    }

    @Test
    fun testMainWeatherComponentsDisplayed() {
        composeTestRule.onNodeWithText("Location", substring = true).assertExists()
        composeTestRule.onNodeWithText("Today's Weather", substring = true).assertExists()
        composeTestRule.onNodeWithText("Current Temperature", substring = true).assertExists()
        composeTestRule.onNodeWithText("Feels Like", substring = true).assertExists()
        composeTestRule.onNodeWithText("Min Temp", substring = true).assertExists()
        composeTestRule.onNodeWithText("Max Temp", substring = true).assertExists()
        composeTestRule.onNodeWithText("48-HR Forecast", substring = true).assertExists()
    }
}