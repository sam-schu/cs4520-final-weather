package com.samschu.cs4520.weather.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samschu.cs4520.weather.R
import com.samschu.cs4520.weather.viewmodel.WeatherViewModel

@Composable
fun LocationScreen(
    vm: WeatherViewModel = viewModel()
) {
    var newLocationIndex by remember { mutableIntStateOf(vm.currentLocationIndex.intValue) }

    val locationOptions = stringArrayResource(R.array.location_options)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Current Location:",
            fontSize = 26.sp
        )
        Text(
            text = locationOptions[vm.currentLocationIndex.intValue],
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .testTag("current location text")
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "New Location:",
            fontSize = 26.sp
        )
        LocationSelectionDropdown(
            selectedIndex = newLocationIndex,
            onSelectedIndexChange = { newLocationIndex = it },
            locationOptions = locationOptions
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            vm.updateCurrentLocation(newLocationIndex)
        }) {
            Text(
                text = "Update",
                fontSize = 22.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationSelectionDropdown(
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    locationOptions: Array<String>
) {
    var dropdownExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = dropdownExpanded,
        onExpandedChange = { dropdownExpanded = !dropdownExpanded },
        modifier = Modifier
            .testTag("new location dropdown")
    ) {
        OutlinedTextField(
            value = locationOptions[selectedIndex],
            onValueChange = {},
            readOnly = true,
            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded)
            },
            modifier = Modifier
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = dropdownExpanded,
            onDismissRequest = { dropdownExpanded = false }
        ) {
            locationOptions.forEachIndexed { index, locationName ->
                DropdownMenuItem(
                    text = { Text(text = locationName) },
                    onClick = {
                        dropdownExpanded = false
                        onSelectedIndexChange(index)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LocationScreenPreview() {
    LocationScreen()
}