package com.juanjojmnz.gtavguide.data.db

import androidx.room.*
import com.juanjojmnz.gtavguide.model.Mission
import kotlinx.coroutines.flow.Flow

@Dao
interface MissionDao {

    @Query("SELECT * FROM missions ORDER BY orderIndex ASC")
    fun getAllMissions(): Flow<List<Mission>>

    @Query("SELECT * FROM missions WHERE missionType = :type ORDER BY orderIndex ASC")
    fun getMissionsByType(type: String): Flow<List<Mission>>

    @Query("SELECT * FROM missions WHERE characters LIKE '%' || :character || '%' ORDER BY orderIndex ASC")
    fun getMissionsByCharacter(character: String): Flow<List<Mission>>

    @Query("SELECT * FROM missions WHERE id = :id")
    suspend fun getMissionById(id: Int): Mission?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(missions: List<Mission>)

    @Query("SELECT COUNT(*) FROM missions WHERE missionType = :type")
    fun getCountByType(type: String): Flow<Int>

    @Query("SELECT * FROM missions WHERE characters LIKE '%' || :character || '%' AND missionType = :type ORDER BY orderIndex ASC")
    fun getMissionsByCharacterAndType(character: String, type: String): Flow<List<Mission>>
}