package com.samschu.cs4520.weather.view

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocationScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUpTestRule() {
        composeTestRule.setContent {
            LocationScreen()
        }
    }

    @Test
    fun testUpdatingLocation() {
        val currentLocationText = composeTestRule.onNodeWithTag("current location text")
        val newLocationDropdown = composeTestRule.onNodeWithTag("new location dropdown")
        val londonOption = composeTestRule.onNodeWithText("London, UK")
        val updateButton = composeTestRule.onNodeWithText("Update")

        currentLocationText.assertTextEquals("Boston, MA")

        // Ensures that the dropdown menu options (only) appear when the menu box is clicked
        londonOption.assertDoesNotExist()
        newLocationDropdown.assertExists()
        newLocationDropdown.performClick()
        londonOption.assertExists()

        // Ensures that, after a new location has been selected, the location is updated (only)
        // after the Update button is clicked
        londonOption.performClick()
        currentLocationText.assertTextEquals("Boston, MA")
        updateButton.performClick()
        currentLocationText.assertTextEquals("London, UK")
    }
}