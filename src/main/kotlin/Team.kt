import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver

class Team(driver: AndroidDriver?) : BaseActions(driver) {

    private val teamHeader = "team_name"
    private val statsTab = "Team Stats"
    private val statTitle = "text_category_name"
    private val statValue = "text_value"
    private val statRank = "text_formatted_rank"

    fun getHeaderText(): String {
        return getElementText(AppiumBy.id(teamHeader))
    }

    fun tapStatsTab(): Boolean {
        return clickElement(AppiumBy.accessibilityId(statsTab))
    }

    fun verifyStatsTabSelected(): Boolean {
        return isElementSelected(AppiumBy.accessibilityId(statsTab))
    }

    // remove leading and trailing parenthesis to match api
    // On theScore android, rank values are wrapped by parenthesis, e.g (3rd)
    // we need to remove parenthesis to match with API value
    private fun removeParenthesis(rank : String) : String{
        val trimRank = rank.trim()
        return if (trimRank[0].toString() == "(" && trimRank[trimRank.length - 1].toString() ==")") {
            trimRank.substring(1, rank.length - 1)
        } else {
            trimRank
        }
    }

    fun verifyStatsBodyDisplayed(teamData : TeamDataClasses): Boolean {
        val apiStatsSet = teamData.getSetOfStatsList()

        val listOfStatTitles = findListOfElement(AppiumBy.id(statTitle))
        val listOfStatValues = findListOfElement(AppiumBy.id(statValue))
        val listOfStatRanks = findListOfElement(AppiumBy.id(statRank))

        for (i in listOfStatTitles.indices) {
            val stat = TeamDataClasses.Stats(
                getElementText(listOfStatTitles[i]),
                getElementText(listOfStatValues[i]).toDouble(),
                removeParenthesis((getElementText(listOfStatRanks[i])))
            )
            if (!apiStatsSet.contains(stat)) {
                return false
            }
        }
        return true
    }
}