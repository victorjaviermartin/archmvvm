package com.victormartin.archmvvm.domain.model

import coil.request.ImageResult
import com.victormartin.archmvvm.data.database.entities.DataEntity
import com.victormartin.archmvvm.data.database.entities.MetaEntity
import com.victormartin.archmvvm.data.database.entities.PlayersEntity
import com.victormartin.archmvvm.data.model.DataNetworkModel
import com.victormartin.archmvvm.data.model.MetaNetworkModel
import com.victormartin.archmvvm.data.model.PlayersNetworkModel

data class PlayersDomainModel(

    var data: MutableList<DataDomainModel> = mutableListOf(),
    var meta: MetaDomainModel = MetaDomainModel()

)

data class DataDomainModel(

    var id: Int = -1,
    var firstName: String = "",
    var heightFeet: String = "",
    var heightInches: String = "",
    var lastName: String = "",
    var position: String = "",
    var team: TeamDomainModel = TeamDomainModel(),
    var weightPounds: String = ""

){
    constructor() : this(-1, "", "", "", "", "", TeamDomainModel(), "")
}

data class TeamDomainModel(

    var id: Int = -1,
    var abbreviation: String = "",
    var city: String = "",
    var conference: String = "",
    var division: String = "",
    var fullName: String = "",
    var name: String = ""

)

data class MetaDomainModel(

    var totalPages: Int = 0,
    var currentPage: Int = 0,
    var nextPage: Int = 0,
    var perPage: Int = 0,
    var totalCount: Int = 0

){
    constructor() : this(0, 0, 0, 0, 0)
}

/**
 * Transformations
 */

fun PlayersNetworkModel.toDomain(): PlayersDomainModel {
    val mapMeta : MetaDomainModel
    meta.let {
        mapMeta = MetaDomainModel(meta.totalPages, meta.currentPage, meta.nextPage, meta.perPage, meta.totalCount)
    }

    val mapData : MutableList<DataDomainModel> = mutableListOf()
    data.let {
        for(item in data){
            val mapTeam : TeamDomainModel = TeamDomainModel(item.team.id?: -1, item.team.abbreviation?: "", item.team.city?: "", item.team.conference?: "", item.team.division?: "", item.team.fullName?: "", item.team.name?: "")
            mapData.add(DataDomainModel(item.id ?: -1, item.firstName?: "", item.heightFeet?: "", item.heightInches ?: "", item.lastName?: "", item.position?: "", mapTeam, item.weightPounds?: ""))
        }
    }

    return PlayersDomainModel(mapData, mapMeta)
}

fun PlayersEntity.toDomain(): PlayersDomainModel {
    val mapMeta : MetaDomainModel
    meta.let {
        mapMeta = MetaDomainModel(meta.totalPages, meta.currentPage, meta.nextPage, meta.perPage, meta.totalCount)
    }

    val mapData : MutableList<DataDomainModel> = mutableListOf()
    data.let {
        for(item in data){
            val mapTeam : TeamDomainModel = TeamDomainModel(item.team.id, item.team.abbreviation, item.team.city, item.team.conference, item.team.division, item.team.fullName, item.team.name)
            mapData.add(DataDomainModel(item.id, item.firstName, item.heightFeet, item.heightInches, item.lastName, item.position, mapTeam, item.weightPounds))
        }
    }

    return PlayersDomainModel(mapData, mapMeta)
}

fun DataEntity.toDomain() : DataDomainModel {

    val mapTeam : TeamDomainModel = TeamDomainModel(team.id, team.abbreviation, team.city, team.conference, team.division, team.fullName, team.name)
    return DataDomainModel(id, firstName, heightFeet, heightInches, lastName, position, mapTeam, weightPounds)

}

fun DataNetworkModel.toDomain() : DataDomainModel {

    val mapTeam : TeamDomainModel = TeamDomainModel(team.id, team.abbreviation, team.city, team.conference, team.division, team.fullName, team.name)
    return DataDomainModel(id, firstName, heightFeet, heightInches, lastName, position, mapTeam, weightPounds)

}

fun MetaEntity.toDomain(): MetaDomainModel {
    return MetaDomainModel(totalPages, currentPage, nextPage, perPage, totalCount)
}

fun MetaNetworkModel.toDomain(): MetaDomainModel {
    return MetaDomainModel(totalPages, currentPage, nextPage, perPage, totalCount)
}