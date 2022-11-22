package com.victormartin.archmvvm.domain

import com.victormartin.archmvvm.data.Repository
import com.victormartin.archmvvm.data.database.entities.toDatabase
import com.victormartin.archmvvm.domain.model.PlayersDomainModel
import javax.inject.Inject

class GetPlayersUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke():PlayersDomainModel{

        val players = repository.getAllPlayersFromNetwork()

        return if(players.data.isNotEmpty()){
            repository.clearPlayers()
            repository.insertAll()
            players
        }else{
            repository.getAllPlayersFromDatabase()
        }
    }
}