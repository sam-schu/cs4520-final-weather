package com.samschu.cs4520.weather.view

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InfoScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUpTestRule() {
        composeTestRule.setContent {
            TodayWeatherDetailsScreen()
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testDetailedWeatherComponentsDisplayed() {
        composeTestRule.onNodeWithTag("loading indicator").assertExists()

        composeTestRule.waitUntilDoesNotExist(
            hasTestTag("loading indicator"),
            5000L
        )

        composeTestRule.onAllNodesWithText("Temperature:", substring = true).onFirst()
            .assertExists()
        composeTestRule.onNodeWithText("Feels Like:", substring = true).assertExists()
        composeTestRule.onNodeWithText("Humidity:", substring = true).assertExists()
        composeTestRule.onNodeWithText("UV Index:", substring = true).assertExists()
        composeTestRule.onNodeWithText("Wind Speed:", substring = true).assertExists()
        composeTestRule.onNodeWithText("Sunrise Time:", substring = true).assertExists()
        composeTestRule.onNodeWithText("Sunset Time:", substring = true).assertExists()
        composeTestRule.onNodeWithText("Max Temperature:", substring = true).assertExists()
        composeTestRule.onNodeWithText("Min Temperature:", substring = true).assertExists()
        composeTestRule.onNodeWithText("Description:", substring = true).assertExists()
    }
}