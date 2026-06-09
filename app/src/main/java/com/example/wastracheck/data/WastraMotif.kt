package com.example.wastracheck.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "motifs")
data class WastraMotif(
    @PrimaryKey val id: String,
    val name: String,
    val origin: String,
    val description: String,
    val philosophy: String = "",
    val imageRes: Int? = null,
    val region: String = "Jawa Tengah"
)
