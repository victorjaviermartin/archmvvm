package com.victormartin.archmvvm.data

import com.victormartin.archmvvm.data.database.dao.PlayersDao
import com.victormartin.archmvvm.data.database.entities.DataEntity
import com.victormartin.archmvvm.data.database.entities.MetaEntity
import com.victormartin.archmvvm.data.database.entities.PlayersEntity
import com.victormartin.archmvvm.data.model.DataNetworkModel
import com.victormartin.archmvvm.data.model.PlayersNetworkModel
import com.victormartin.archmvvm.data.model.toDatabase
import com.victormartin.archmvvm.data.network.ApiService
import com.victormartin.archmvvm.domain.model.DataDomainModel
import com.victormartin.archmvvm.domain.model.PlayersDomainModel
import com.victormartin.archmvvm.domain.model.toDomain
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: ApiService,
    private val playersDao: PlayersDao
) {

    suspend fun getAllPlayersFromNetwork(): PlayersDomainModel {
        val response: PlayersNetworkModel = api.getAllPlayers()
        return response.toDomain()
    }

    suspend fun getAllPlayersFromNetwork(page: Int): PlayersDomainModel {
        val response: PlayersNetworkModel = api.getAllPlayers(page)
        return response.toDomain()
    }

    suspend fun getAllPlayersFromDatabase(): PlayersDomainModel {
        val responseData: MutableList<DataEntity> = playersDao.getAllPlayers()
        val responseMeta: MetaEntity = playersDao.getMetaData()
        val players = PlayersEntity(responseData, responseMeta)
        return players.toDomain()
    }

    suspend fun getPlayersByNameFromNetwork(search: String): PlayersDomainModel {
        val response: PlayersNetworkModel = api.getPlayersByName(search)
        return response.toDomain()
    }

    suspend fun getPlayersByNameFromDatabase(search: String): MutableList<DataDomainModel> {
        val responseData: MutableList<DataEntity> = playersDao.getPlayersByName(search)
        return responseData.map { it.toDomain() }.toMutableList()
    }

    suspend fun getPlayerByIdFromNetwork(idPlayer: String): DataDomainModel? {
        val response: DataNetworkModel? = api.getPlayerById(idPlayer)
        return response?.toDomain()
    }

    suspend fun getPlayerByIdFromDatabase(idPlayer: String): DataDomainModel {
        val response: DataEntity = playersDao.getPlayerById(idPlayer)
        return response.toDomain()
    }

    suspend fun insertAll(){
        val response: PlayersNetworkModel = api.getAllPlayers()
        playersDao.insertAll(response.data.map { it.toDatabase() }.toMutableList(), response.meta.toDatabase())
    }

    suspend fun insertByPart(data: MutableList<DataEntity>){
        playersDao.insertByPart(data)
    }

    suspend fun clearPlayers(){
        playersDao.deleteAllData()
        playersDao.deleteAllTeams()
        playersDao.deleteAllMeta()
    }
}