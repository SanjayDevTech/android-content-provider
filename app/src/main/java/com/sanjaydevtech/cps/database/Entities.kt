package com.sanjaydevtech.cps.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Domain(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
)