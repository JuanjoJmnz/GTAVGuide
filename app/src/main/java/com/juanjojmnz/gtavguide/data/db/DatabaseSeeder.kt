package com.juanjojmnz.gtavguide.data.db

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.juanjojmnz.gtavguide.model.Mission
import com.juanjojmnz.gtavguide.model.Property
import com.juanjojmnz.gtavguide.model.RandomEvent

class DatabaseSeeder(private val context: Context) {

    private val gson = Gson()

    suspend fun seed(db: AppDatabase) {
        seedMissions(db)
        seedProperties(db)
        seedRandomEvents(db)
    }

    private suspend fun seedMissions(db: AppDatabase) {
        try {
            val json = context.assets.open("data/missions.json")
                .bufferedReader()
                .use { it.readText() }
            val type = object : TypeToken<List<Mission>>() {}.type
            val missions: List<Mission> = gson.fromJson(json, type)
            db.missionDao().insertAll(missions)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun seedRandomEvents(db: AppDatabase) {
        try {
            val json = context.assets.open("data/random_events.json")
                .bufferedReader()
                .use { it.readText() }
            val type = object : TypeToken<List<RandomEvent>>() {}.type
            val events: List<RandomEvent> = gson.fromJson(json, type)
            db.randomEventDao().insertAll(events)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun seedProperties(db: AppDatabase) {
        try {
            val json = context.assets.open("data/properties.json")
                .bufferedReader()
                .use { it.readText() }
            val type = object : TypeToken<List<Property>>() {}.type
            val properties: List<Property> = gson.fromJson(json, type)
            db.propertyDao().insertAll(properties)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}