package net.carfax.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AssignmentDao {

    @Query("SELECT * FROM assignment")
    fun getAssignment(): Assignment

    @Insert
    fun saveAssignment(assignment: Assignment)

}