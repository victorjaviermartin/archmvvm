package com.victormartin.archmvvm.domain

import com.victormartin.archmvvm.data.model.DataStatsSeasonAverageNetworkModel
import com.victormartin.archmvvm.data.network.ApiService
import javax.inject.Inject

class GetPlayerStatsUseCase @Inject constructor(private val api: ApiService) {
    suspend operator fun invoke(season: Int, player_ids: List<Int>): DataStatsSeasonAverageNetworkModel {
        return api.getStatsByPlayerId(season, player_ids)
    }
}