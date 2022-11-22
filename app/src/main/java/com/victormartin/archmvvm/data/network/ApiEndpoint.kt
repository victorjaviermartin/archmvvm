package com.victormartin.archmvvm.data.network

import com.victormartin.archmvvm.data.model.DataNetworkModel
import com.victormartin.archmvvm.data.model.DataStatsSeasonAverageNetworkModel
import com.victormartin.archmvvm.data.model.PlayersNetworkModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndpoint {

    @GET("players")
    suspend fun getAllPlayers(@Query("page") page: Int = 0): Response<PlayersNetworkModel>

    @GET("players")
    suspend fun getPlayersByName(@Query("search") playerName: String): Response<PlayersNetworkModel>

    @GET("players/{id}")
    suspend fun getPlayerById(@Path("id") playerId: String): Response<DataNetworkModel>

    @GET("season_averages")
    suspend fun getStatsByPlayerId(@Query("season") season: Int, @Query("player_ids[]") player_ids: List<Int>): Response<DataStatsSeasonAverageNetworkModel>

}