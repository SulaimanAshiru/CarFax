package net.carfax.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.carfax.api.model.AssignmentModel

class Converters {

    @TypeConverter
    fun fromAssignmentModel(value: AssignmentModel): String {
        val gson = Gson()
        val type = object : TypeToken<AssignmentModel>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toAssignmentModel(value: String): AssignmentModel {
        val gson = Gson()
        val type = object : TypeToken<AssignmentModel>() {}.type
        return gson.fromJson(value, type)
    }


}