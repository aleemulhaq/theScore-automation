import com.google.gson.Gson
import org.apache.logging.log4j.kotlin.Logging

class TeamDataClasses (apiUrl: String) : Logging {

    private val teamProfileRes = makeApiRequest(apiUrl)

    // init obj here so it is cached in class instance
    private val teamProfileObj: TeamProfile = Gson().fromJson(teamProfileRes, TeamProfile::class.java)

    fun getApiTeamProfile(): String {
        logger.info("Getting team's FULL NAME from theScore API")
        return teamProfileObj.team.full_name
    }

    // lots of nested legaues with seasons over the years that still show stats on theScore app
    private fun getStatsList(): List<Stats> {
        logger.info("Getting team-stats from theScore API")
        var allStatsList = mutableListOf<Stats>()

        val iterator = teamProfileObj.leagues_team_stats.iterator()
        iterator.forEach {
            val t = it.seasons_team_stats
            val t2 = t.iterator()
            t2.forEach {
                val sg = it.stats_groups
                val t3 = sg.iterator()
                t3.forEach {
                    allStatsList = (allStatsList + it.stats) as MutableList<Stats>
                }
            }


        }
        return allStatsList
    }

    // for easy O(1) constant time item access
    fun getSetOfStatsList(): Set<Stats> {
        return getStatsList().toSet()
    }

    // "STATS", "RANK" etc
    fun getTeamStatsSubHeaderText() : String {
        return teamProfileObj.leagues_team_stats[0].seasons_team_stats[0].stats_groups[0].name
    }

    fun getTeamStatsSubHeaderValue() : String {
        return teamProfileObj.leagues_team_stats[0].seasons_team_stats[0].stats_groups[0].value_name
    }

    data class TeamProfile(
        val team: TeamInfo,
        val leagues_team_stats: List<TeamLeagueStats>
    )

    data class TeamInfo(
        val full_name: String
    )

    data class TeamLeagueStats(
        val seasons_team_stats: List<TeamSeasonStats>
    )

    data class TeamSeasonStats(
        val stats_groups: List<StatGroups>
    )

    data class StatGroups(
        val name: String,
        val value_name: String,
        val stats: List<Stats>
    )

    data class Stats(
        val name: String,
        val value: Double,
        val rank: String
    )
}