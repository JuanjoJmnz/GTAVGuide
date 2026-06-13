package com.juanjojmnz.gtavguide.data.repository

import com.juanjojmnz.gtavguide.data.db.PropertyDao
import com.juanjojmnz.gtavguide.model.Property
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PropertyRepository @Inject constructor(
    private val dao: PropertyDao
) {
    fun getAllProperties(): Flow<List<Property>> =
        dao.getAllProperties()

    fun getPropertiesByCharacter(character: String): Flow<List<Property>> =
        dao.getPropertiesByCharacter(character)

    fun getPropertiesByType(type: String): Flow<List<Property>> =
        dao.getPropertiesByType(type)

    suspend fun getPropertyById(id: Int): Property? =
        dao.getPropertyById(id)

    fun getAllTypes(): Flow<List<String>> =
        dao.getAllTypes()
}