package com.samschu.cs4520.weather.model

import com.samschu.cs4520.weather.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object Api {
    private const val API_KEY = BuildConfig.OPEN_WEATHER_API_KEY
    const val BASE_URL = "https://api.openweathermap.org/"
    const val WEATHER_DATA_ENDPOINT = "data/3.0/onecall?units=imperial&exclude=minutely,alerts&appid=$API_KEY"
    const val GEOCODING_ENDPOINT = "geo/1.0/direct?limit=1&appid=$API_KEY"
}

/**
 * Provides access to a single Retrofit instance.
 */
object RetrofitBuilder {
    private lateinit var retrofit: Retrofit

    /**
     * Gets the Retrofit instance.
     *
     * If the Retrofit instance has not yet been created, initializes and returns it. Otherwise,
     * returns the previously created Retrofit instance.
     */
    fun getRetrofit(): Retrofit {
        if (!this::retrofit.isInitialized) {
            retrofit = Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}

/**
 * Defines the operations that can be performed to interact with the server API.
 */
interface ApiService {
    @GET(Api.WEATHER_DATA_ENDPOINT)
    suspend fun getWeatherData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): WeatherData

    @GET(Api.GEOCODING_ENDPOINT)
    suspend fun getCoordinates(
        @Query("q") locSpecifier: String
    ): List<LocationCoordinates>
}