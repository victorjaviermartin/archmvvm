package com.victormartin.archmvvm.data.database.entities

import androidx.room.*
import com.victormartin.archmvvm.domain.model.DataDomainModel
import com.victormartin.archmvvm.domain.model.MetaDomainModel
import com.victormartin.archmvvm.domain.model.PlayersDomainModel

data class PlayersEntity(

    var data: MutableList<DataEntity> = mutableListOf(),
    var meta: MetaEntity = MetaEntity()

)

@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
@Entity(tableName = "data_table")
data class DataEntity(

    @PrimaryKey
    @ColumnInfo(name = "id") var id: Int = -1,
    @ColumnInfo(name = "first_name") var firstName: String = "",
    @ColumnInfo(name = "height_feet") var heightFeet: String = "",
    @ColumnInfo(name = "height_inches") var heightInches: String = "",
    @ColumnInfo(name = "last_name") var lastName: String = "",
    @ColumnInfo(name = "position") var position: String = "",
    @Embedded(prefix = "parent_data_") var team: TeamEntity = TeamEntity(),
    @ColumnInfo(name = "weight_pounds") var weightPounds: String = ""

)

@Entity(tableName = "team_table")
data class TeamEntity(

    @PrimaryKey
    @ColumnInfo(name = "id") var id: Int = -1,
    @ColumnInfo(name = "abbreviation") var abbreviation: String = "",
    @ColumnInfo(name = "city") var city: String = "",
    @ColumnInfo(name = "conference") var conference: String = "",
    @ColumnInfo(name = "division") var division: String = "",
    @ColumnInfo(name = "full_name") var fullName: String = "",
    @ColumnInfo(name = "name") var name: String = ""

)

@Entity(tableName = "meta_table")
data class MetaEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "total_pages") var totalPages: Int = 0,
    @ColumnInfo(name = "current_page") var currentPage: Int = 0,
    @ColumnInfo(name = "next_page") var nextPage: Int = 0,
    @ColumnInfo(name = "per_page") var perPage: Int = 0,
    @ColumnInfo(name = "total_count") var totalCount: Int = 0

){
    constructor() : this(0, 0, 0, 0, 0)

    constructor(totalPages: Int, currentPage: Int, nextPage: Int, perPage: Int, totalCount: Int) : this(0, totalPages, currentPage, nextPage, perPage, totalCount)
}

fun PlayersDomainModel.toDatabase(): PlayersEntity {
    val mapMeta : MetaEntity
    meta.let {
        mapMeta = MetaEntity(meta.totalPages)
    }

    val mapData : MutableList<DataEntity> = mutableListOf()
    data.let {
        for(item in data){
            val mapTeam : TeamEntity = TeamEntity(item.team.id, item.team.abbreviation, item.team.city, item.team.conference, item.team.division, item.team.fullName, item.team.name)
            mapData.add(DataEntity(item.id, item.firstName, item.heightFeet, item.heightInches, item.lastName, item.position, mapTeam, item.weightPounds))
        }
    }

    return PlayersEntity(mapData, mapMeta)
}

fun DataDomainModel.toDatabase(): DataEntity {
    val mapTeam : TeamEntity = TeamEntity(team.id, team.abbreviation, team.city, team.conference, team.division, team.fullName, team.name)

    return DataEntity(id, firstName, heightFeet, heightInches, lastName, position, mapTeam, weightPounds)
}

fun MetaDomainModel.toDatabase(): MetaEntity {
    return MetaEntity(totalPages, currentPage, nextPage, perPage, totalCount)
}