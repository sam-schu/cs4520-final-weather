package com.samschu.cs4520.weather.model

class Repo : WeatherRepo {
    override fun replaceStoredWeatherData(data: WeatherDataEntity) {
        getDao()?.replaceStoredWeatherData(data)
    }

    override fun getStoredWeatherData(): List<WeatherDataEntity>? {
        return getDao()?.getAllWeatherData()
    }

    private fun getDao(): WeatherDao? =
        WeatherDatabaseProvider
            .getDatabase()
            ?.getWeatherDao()
}