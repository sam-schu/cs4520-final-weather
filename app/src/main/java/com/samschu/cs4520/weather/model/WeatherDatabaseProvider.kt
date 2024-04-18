package com.samschu.cs4520.weather.model

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room

class WeatherDatabaseProvider {
    companion object {
        private var db: WeatherDatabase? = null

        @SuppressLint("StaticFieldLeak")
        private var databaseContext: Context? = null

        fun getDatabase(): WeatherDatabase? {
            if (db == null) {
                databaseContext?.let {
                    db = Room.databaseBuilder(
                        it,
                        WeatherDatabase::class.java,
                        "WeatherDatabase"
                    ).build()
                }
            }
            return db
        }

        fun setContext(context: Context) {
            databaseContext = context
        }
    }
}