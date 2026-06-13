package com.juanjojmnz.gtavguide.data.db

import androidx.room.*
import com.juanjojmnz.gtavguide.model.Property
import kotlinx.coroutines.flow.Flow

@Dao
interface PropertyDao {

    @Query("SELECT * FROM properties ORDER BY orderIndex ASC")
    fun getAllProperties(): Flow<List<Property>>

    @Query("SELECT * FROM properties WHERE availableTo LIKE '%' || :character || '%' ORDER BY orderIndex ASC")
    fun getPropertiesByCharacter(character: String): Flow<List<Property>>

    @Query("SELECT * FROM properties WHERE propertyType = :type ORDER BY orderIndex ASC")
    fun getPropertiesByType(type: String): Flow<List<Property>>

    @Query("SELECT * FROM properties WHERE id = :id")
    suspend fun getPropertyById(id: Int): Property?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(properties: List<Property>)

    @Query("SELECT DISTINCT propertyType FROM properties ORDER BY propertyType ASC")
    fun getAllTypes(): Flow<List<String>>
}