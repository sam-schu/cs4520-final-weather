package com.samschu.cs4520.weather.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val serializedString: String?
)