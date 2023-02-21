import com.google.gson.Gson

class TeamDataClasses {

    private val teamProfileRes = makeApiRequest("https://api.thescore.com/soccer/teams/62/profile")

    private val teamProfileObj: TeamProfile = Gson().fromJson(teamProfileRes, TeamProfile::class.java)

    fun getApiTeamProfile(): String {
        return teamProfileObj.team.full_name
    }

    fun getStatsList(): List<Stats> {
        return teamProfileObj.leagues_team_stats[0].seasons_team_stats[0].stats_groups[0].stats
    }

    fun getSetOfStatsList(): Set<Stats> {
        return getStatsList().toSet()
    }

    data class TeamProfile(
        val team: TeamInfo,
        val leagues_team_stats: List<TeamLeagueStats>
    ) {
    }

    data class TeamInfo(
        val full_name: String
    ) {
    }

    data class TeamLeagueStats(
        val seasons_team_stats: List<TeamSeasonStats>
    ) {
    }

    data class TeamSeasonStats(
        val stats_groups: List<StatGroups>
    ){
    }

    data class StatGroups(
        val stats: List<Stats>
    ){
    }

    data class Stats(
        val name: String,
        val value: Double,
        val rank: String
    ){
    }
}