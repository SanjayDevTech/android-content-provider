package com.sanjaydevtech.cps.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Domain::class], version = 1)
abstract class MainDatabase : RoomDatabase() {
    abstract fun domainDao(): DomainDao

    companion object {
        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getInstance(context: Context): MainDatabase {
            return INSTANCE ?: synchronized(this) {
                var instance =
                    Room.databaseBuilder(context, MainDatabase::class.java, "main")
                        .build()

                INSTANCE = instance
                instance
            }
        }
    }
}