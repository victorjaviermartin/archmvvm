package com.victormartin.archmvvm.data.database.dao

import androidx.room.*
import com.victormartin.archmvvm.data.database.entities.DataEntity
import com.victormartin.archmvvm.data.database.entities.MetaEntity
import com.victormartin.archmvvm.data.model.DataNetworkModel
import com.victormartin.archmvvm.data.model.MetaNetworkModel

@Dao
interface PlayersDao {

    @Query("SELECT * FROM data_table")
    suspend fun getAllPlayers():MutableList<DataEntity>

    @Query("SELECT * FROM meta_table LIMIT 1")
    suspend fun getMetaData():MetaEntity

    @Query("SELECT * FROM data_table WHERE first_name LIKE :search OR last_name LIKE :search")
    suspend fun getPlayersByName(search: String): MutableList<DataEntity>

    @Query("SELECT * FROM data_table WHERE id LIKE :idPlayer")
    suspend fun getPlayerById(idPlayer: String): DataEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(playersData:MutableList<DataEntity>, playersMeta: MetaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertByPart(playersData:MutableList<DataEntity>)

    // OPTIONALS

    @Delete
    fun deletePlayer(player: DataEntity)

    @Query("DELETE FROM data_table")
    suspend fun deleteAllData()

    @Query("DELETE FROM team_table")
    suspend fun deleteAllTeams()

    @Query("DELETE FROM meta_table")
    suspend fun deleteAllMeta()

}