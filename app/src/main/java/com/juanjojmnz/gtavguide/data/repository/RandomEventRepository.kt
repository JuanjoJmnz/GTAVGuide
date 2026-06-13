package com.juanjojmnz.gtavguide.data.repository

import com.juanjojmnz.gtavguide.data.db.RandomEventDao
import com.juanjojmnz.gtavguide.model.RandomEvent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RandomEventRepository @Inject constructor(
    private val dao: RandomEventDao
) {
    fun getAllEvents(): Flow<List<RandomEvent>> =
        dao.getAllEvents()

    fun getEventsByCharacter(character: String): Flow<List<RandomEvent>> =
        dao.getEventsByCharacter(character)

    suspend fun getEventById(id: Int): RandomEvent? =
        dao.getEventById(id)
}