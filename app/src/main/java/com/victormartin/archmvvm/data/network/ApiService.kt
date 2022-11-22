package com.victormartin.archmvvm.data.network

import android.util.Log
import com.victormartin.archmvvm.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject

class ApiService @Inject constructor(private val api: ApiEndpoint) {

    suspend fun getAllPlayers(): PlayersNetworkModel {

        return withContext(Dispatchers.IO) {

            val initMetaInfoResponse = api.getAllPlayers()

            var data: MutableList<DataNetworkModel> = mutableListOf()
            var meta : MetaNetworkModel = initMetaInfoResponse.body()?.meta!!

            while(Integer.valueOf(meta.currentPage) < Integer.valueOf(meta.totalPages) && meta.nextPage != null){
                val recursiveResponse = api.getAllPlayers(meta.nextPage)
                recursiveResponse.body()?.data?.let { results -> data.addAll(results) }
                meta = recursiveResponse.body()?.meta!!
                Log.e("NET", " CURPAG: $meta.currentPage TOTAL: $meta.totalPages NEXT: $meta.nextPage")
            }
            PlayersNetworkModel(data, meta)
        }
    }

    suspend fun getAllPlayers(page: Int): PlayersNetworkModel {

        return withContext(Dispatchers.IO) {

            val response = api.getAllPlayers(page)

            if(response.isSuccessful){
                if(response.body() != null){
                    return@withContext response.body()!!
                }else{
                    return@withContext PlayersNetworkModel()
                }
            }else{
                return@withContext PlayersNetworkModel()
            }

        }
    }

    suspend fun getPlayersByName(name: String) : PlayersNetworkModel {
        return withContext(Dispatchers.IO) {
            val response = api.getPlayersByName(name)
            response.body() ?: PlayersNetworkModel()    // If there is no data, we return an empty constructor
        }
    }

    suspend fun getPlayerById(id: String) : DataNetworkModel? {
        return withContext(Dispatchers.IO) {
            val response = api.getPlayerById(id)
            response.body()
        }
    }

    suspend fun getStatsByPlayerId(season: Int, player_ids: List<Int>): DataStatsSeasonAverageNetworkModel {
        return withContext(Dispatchers.IO) {
            val response = api.getStatsByPlayerId(season, player_ids)
            response.body()!!
        }
    }

}