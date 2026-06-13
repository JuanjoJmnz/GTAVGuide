package com.juanjojmnz.gtavguide.data.db

import androidx.room.*
import com.juanjojmnz.gtavguide.model.RandomEvent
import kotlinx.coroutines.flow.Flow

@Dao
interface RandomEventDao {

    @Query("SELECT * FROM random_events ORDER BY number ASC")
    fun getAllEvents(): Flow<List<RandomEvent>>

    @Query("SELECT * FROM random_events WHERE characters LIKE '%' || :character || '%' ORDER BY number ASC")
    fun getEventsByCharacter(character: String): Flow<List<RandomEvent>>

    @Query("SELECT * FROM random_events WHERE id = :id")
    suspend fun getEventById(id: Int): RandomEvent?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<RandomEvent>)
}