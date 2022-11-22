package com.victormartin.archmvvm.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.victormartin.archmvvm.data.database.dao.PlayersDao
import com.victormartin.archmvvm.data.database.entities.*

@Database(entities = [DataEntity::class, TeamEntity::class, MetaEntity::class], version = 1)
@TypeConverters(DataEntityTypeConverter::class)
abstract class PlayersDatabase: RoomDatabase() {
    abstract fun getPlayersDao():PlayersDao
}