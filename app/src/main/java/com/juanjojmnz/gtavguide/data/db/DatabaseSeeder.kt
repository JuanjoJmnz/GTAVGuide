package com.juanjojmnz.gtavguide.data.db

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.juanjojmnz.gtavguide.model.Mission

class DatabaseSeeder(private val context: Context) {

    private val gson = Gson()

    suspend fun seed(db: AppDatabase) {
        seedMissions(db)
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
}