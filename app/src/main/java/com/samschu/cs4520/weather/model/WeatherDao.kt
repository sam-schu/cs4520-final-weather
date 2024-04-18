package com.samschu.cs4520.weather.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface WeatherDao {
    @Insert
    fun addWeatherData(dataEntity: WeatherDataEntity)

    @Query("DELETE FROM WeatherDataEntity")
    fun clearWeatherData()

    @Transaction
    fun replaceStoredWeatherData(data: WeatherDataEntity) {
        clearWeatherData()
        addWeatherData(data)
    }

    @Query("SELECT * FROM WeatherDataEntity")
    fun getAllWeatherData(): List<WeatherDataEntity>
}