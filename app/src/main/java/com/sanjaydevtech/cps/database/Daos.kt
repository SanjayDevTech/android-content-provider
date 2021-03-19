package com.sanjaydevtech.cps.database

import android.database.Cursor
import androidx.room.*

@Dao
interface DomainDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(domain: Domain): Long

    @Update
    fun update(domain: Domain): Int

    @Delete
    fun delete(domain: Domain): Int

    @Query(value = "DELETE FROM domain WHERE id=:id")
    fun deleteById(id: Int): Int

    @Query(value = """SELECT * FROM domain ORDER BY CASE :order WHEN 1 THEN 'id ASC' ELSE 'id DESC' END""")
    fun selectAll(order: Boolean = true): Cursor

    @Query(value = "SELECT * FROM domain WHERE id=:id")
    fun selectById(id: Int): Cursor

}