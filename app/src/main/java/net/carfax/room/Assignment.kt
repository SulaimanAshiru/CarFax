package net.carfax.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import net.carfax.api.model.AssignmentModel

@Entity(tableName = "assignment")
class Assignment(@PrimaryKey(autoGenerate = true) var id: Int,
                 @ColumnInfo(name = "assignmentsData")
                @TypeConverters(Converters::class)
                var assignments: AssignmentModel?=null)