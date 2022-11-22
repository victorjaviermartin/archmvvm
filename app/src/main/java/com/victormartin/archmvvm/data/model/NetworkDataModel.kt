package com.victormartin.archmvvm.data.model

import com.google.gson.annotations.SerializedName
import com.victormartin.archmvvm.data.database.entities.DataEntity
import com.victormartin.archmvvm.data.database.entities.MetaEntity
import com.victormartin.archmvvm.data.database.entities.TeamEntity
import com.victormartin.archmvvm.domain.model.DataDomainModel
import com.victormartin.archmvvm.domain.model.MetaDomainModel
import com.victormartin.archmvvm.domain.model.TeamDomainModel

data class PlayersNetworkModel(

    @SerializedName("data") var data: MutableList<DataNetworkModel> = mutableListOf(),
    @SerializedName("meta") var meta: MetaNetworkModel = MetaNetworkModel()

) {
    constructor() : this(mutableListOf(), MetaNetworkModel())
}

data class DataNetworkModel(

    @SerializedName("id") var id: Int = -1,
    @SerializedName("first_name") var firstName: String = "",
    @SerializedName("height_feet") var heightFeet: String = "",
    @SerializedName("height_inches") var heightInches: String = "",
    @SerializedName("last_name") var lastName: String = "",
    @SerializedName("position") var position: String = "",
    @SerializedName("team") var team: TeamNetworkModel = TeamNetworkModel(),
    @SerializedName("team_id") var teamId: Int = -1,
    @SerializedName("weight_pounds") var weightPounds: String = ""

)

data class TeamNetworkModel(

    @SerializedName("id") var id: Int = -1,
    @SerializedName("abbreviation") var abbreviation: String = "",
    @SerializedName("city") var city: String = "",
    @SerializedName("conference") var conference: String = "",
    @SerializedName("division") var division: String = "",
    @SerializedName("full_name") var fullName: String = "",
    @SerializedName("name") var name: String = ""

)

data class MetaNetworkModel(

    @SerializedName("total_pages") var totalPages: Int = 0,
    @SerializedName("current_page") var currentPage: Int = 0,
    @SerializedName("next_page") var nextPage: Int = 0,
    @SerializedName("per_page") var perPage: Int = 0,
    @SerializedName("total_count") var totalCount: Int = 0

) {
    constructor() : this(0, 0, 0, 0, 0)
}

data class GameNetworkModel(

    @SerializedName("id") var id: Int = -1,
    @SerializedName("date") var date: String = "",
    @SerializedName("home_team_id") var homeTeamId: Int = -1,
    @SerializedName("home_team_score") var homeTeamScore: Int = -1,
    @SerializedName("period") var period: Int = -1,
    @SerializedName("postseason") var postseason: Boolean = false,
    @SerializedName("season") var season: Int = -1,
    @SerializedName("status") var status: String = "",
    @SerializedName("time") var time: String = "",
    @SerializedName("visitor_team_id") var visitorTeamId: Int = -1,
    @SerializedName("visitor_team_score") var visitorTeamScore: Int = -1

)

data class DataStatsSeasonAverageNetworkModel(

    @SerializedName("games_played") var gamesPlayed: Int = -1,
    @SerializedName("player_id") var playerId: Int = -1,
    @SerializedName("season") var season: Int = -1,
    @SerializedName("min") var min: String = "",
    @SerializedName("fgm") var fgm: Double = -1.0,
    @SerializedName("fga") var fga: Double = -1.0,
    @SerializedName("fg3m") var fg3m: Double = -1.0,
    @SerializedName("fg3a") var fg3a: Double = -1.0,
    @SerializedName("ftm") var ftm: Double = -1.0,
    @SerializedName("fta") var fta: Double = -1.0,
    @SerializedName("oreb") var oreb: Double = -1.0,
    @SerializedName("dreb") var dreb: Double = -1.0,
    @SerializedName("reb") var reb: Double = -1.0,
    @SerializedName("ast") var ast: Double = -1.0,
    @SerializedName("stl") var stl: Double = -1.0,
    @SerializedName("blk") var blk: Double = -1.0,
    @SerializedName("turnover") var turnover: Double = -1.0,
    @SerializedName("pf") var pf: Double = -1.0,
    @SerializedName("pts") var pts: Double = -1.0,
    @SerializedName("fg_pct") var fgPct: Double = -1.0,
    @SerializedName("fg3_pct") var fg3Pct: Double = -1.0,
    @SerializedName("ft_pct") var ftPct: Double = -1.0

) {
    override fun toString(): String {
        return "DataStatsSeasonAverageNetworkModel(gamesPlayed=$gamesPlayed, playerId=$playerId, season=$season, min=$min, fgm=$fgm, fga=$fga, fg3m=$fg3m, fg3a=$fg3a, ftm=$ftm, fta=$fta, oreb=$oreb, dreb=$dreb, reb=$reb, ast=$ast, stl=$stl, blk=$blk, turnover=$turnover, pf=$pf, pts=$pts, fgPct=$fgPct, fg3Pct=$fg3Pct, ftPct=$ftPct)"
    }
}

fun DataDomainModel.toDatabase(): DataEntity {

    val mapTeam: TeamEntity = TeamEntity(
        team.id,
        team.abbreviation,
        team.city,
        team.conference,
        team.division,
        team.fullName,
        team.name
    )
    return DataEntity(
        id,
        firstName,
        heightFeet,
        heightInches,
        lastName,
        position,
        mapTeam,
        weightPounds
    )

}

fun DataNetworkModel.toDatabase(): DataEntity {

    val mapTeam: TeamEntity = TeamEntity(
        team.id,
        team.abbreviation,
        team.city,
        team.conference,
        team.division,
        team.fullName,
        team.name
    )
    return DataEntity(
        id,
        firstName,
        heightFeet,
        heightInches,
        lastName,
        position,
        mapTeam,
        weightPounds
    )

}

fun MetaDomainModel.toDatabase(): MetaEntity {
    return MetaEntity(totalPages, currentPage, nextPage, perPage, totalCount)
}

fun MetaNetworkModel.toDatabase(): MetaEntity {
    return MetaEntity(totalPages, currentPage, nextPage, perPage, totalCount)
}