package com.example.wastracheck.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MotifDao {
    @Query("SELECT * FROM motifs")
    fun getAllMotifs(): Flow<List<WastraMotif>>

    @Query("SELECT * FROM motifs WHERE region = :region")
    fun getMotifsByRegion(region: String): Flow<List<WastraMotif>>

    @Query("SELECT * FROM motifs WHERE name LIKE '%' || :searchQuery || '%' OR origin LIKE '%' || :searchQuery || '%'")
    fun searchMotifs(searchQuery: String): Flow<List<WastraMotif>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMotifs(motifs: List<WastraMotif>)
}
