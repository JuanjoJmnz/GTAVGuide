package com.juanjojmnz.gtavguide.data.repository

import com.juanjojmnz.gtavguide.data.db.MissionDao
import com.juanjojmnz.gtavguide.model.Mission
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MissionRepository @Inject constructor(
    private val dao: MissionDao
) {
    fun getAllMissions(): Flow<List<Mission>> =
        dao.getAllMissions()

    fun getMissionsByType(type: String): Flow<List<Mission>> =
        dao.getMissionsByType(type)

    fun getMissionsByCharacter(character: String): Flow<List<Mission>> =
        dao.getMissionsByCharacter(character)

    suspend fun getMissionById(id: Int): Mission? =
        dao.getMissionById(id)

    fun getMissionsByCharacterAndType(character: String, type: String): Flow<List<Mission>> =
        dao.getMissionsByCharacterAndType(character, type)
}