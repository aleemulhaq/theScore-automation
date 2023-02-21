import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver

class Team(driver: AndroidDriver?) : BaseActions(driver) {

    private val teamHeader = "team_name"
    private val statsTab = "Team Stats"
    private val statTitle = "text_category_name"
    private val statValue = "text_value"
    private val statRank = "text_formatted_rank"

    fun getHeaderText(): String {
        logger.info("Getting text of team header")
        return getElementText(AppiumBy.id(teamHeader))
    }

    fun tapStatsTab(): Boolean {
        logger.info("Tapping team stats tab")
        return clickElement(AppiumBy.accessibilityId(statsTab))
    }

    fun verifyStatsTabSelected(): Boolean {
        logger.info("Verifying team stats tab is selected")
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

    fun verifyStatsCorrectlyDisplayed(apiStatsSet : Set<TeamDataClasses.Stats>): Boolean {
        logger.info("Verifying team stats are correctly displayed")
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
                logger.error("Stat: {$stat} does not match with any stat from theScore API data")
                return false
            }
        }
        logger.info("All team stats are displayed correctly and match with theScore API data")
        return true
    }
}