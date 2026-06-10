package com.juanjojmnz.gtavguide.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>?): String =
        gson.toJson(value ?: emptyList<String>())

    @TypeConverter
    fun toStringList(value: String): List<String> =
        gson.fromJson(value, object : TypeToken<List<String>>() {}.type) ?: emptyList()
}

enum class Character(val displayName: String) {
    MICHAEL("Michael"),
    FRANKLIN("Franklin"),
    TREVOR("Trevor"),
    ALL("Todos"),
    OTHER("Otro")
}

@Entity(tableName = "missions")
@TypeConverters(Converters::class)
data class Mission(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val characters: List<String>,
    val goldRequirements: List<String>,
    val rewards: List<String>,
    val notes: String = "",
    val unlockCondition: String = "",
    val missionType: String = "MAIN_STORY",
    val orderIndex: Int = 0,
    val approach: String? = null,
    val approachName: String? = null,
    val approachGroup: String? = null
)