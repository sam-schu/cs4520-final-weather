package com.samschu.cs4520.weather.view

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HourlyScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUpTestRule() {
        composeTestRule.setContent {
            HourlyScreen()
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testAllHourValuesDisplayed() {
        composeTestRule.onNodeWithTag("loading indicator").assertExists()

        composeTestRule.waitUntilDoesNotExist(
            hasTestTag("loading indicator"),
            5000L
        )

        // Check that the hours 1 through 12 are all displayed in the hourly forecast
        for (i in 1..12) {
            composeTestRule.onAllNodesWithText(i.toString(), substring = true).onFirst()
                .assertExists()
        }
    }
}