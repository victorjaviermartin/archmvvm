package com.victormartin.archmvvm.domain

import com.victormartin.archmvvm.data.Repository
import com.victormartin.archmvvm.data.database.entities.toDatabase
import com.victormartin.archmvvm.domain.model.PlayersDomainModel
import javax.inject.Inject

class GetPlayersByPageUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(page: Int):PlayersDomainModel{
        return repository.getAllPlayersFromNetwork(page)

    }
}