package com.victormartin.archmvvm.data.database.entities

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataEntityTypeConverter {

    @TypeConverter
    fun toJson(value: MutableList<DataEntity>): String {
        val gson = Gson()
        val type = object : TypeToken<MutableList<DataEntity>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun fromJson(value: String): MutableList<DataEntity> {
        val gson = Gson()
        val type = object : TypeToken<MutableList<DataEntity>>() {}.type
        return gson.fromJson(value, type)
    }

}