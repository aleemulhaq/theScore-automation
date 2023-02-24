import BaseActions.W3cActions.doSwipe
import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver
import org.openqa.selenium.Point
import java.util.*
import kotlin.math.min

class Team(driver: AndroidDriver?) : BaseActions(driver) {
    // element raw identifiers
    private val teamHeader = "team_name"
    private val statTitle = "text_category_name"
    private val statValue = "text_value"
    private val statRank = "text_formatted_rank"
    private val statSubHeaderVal = "header_secondary_text"
    private val statSubHeaderText = "header_text"

    fun getHeaderText(): String {
        logger.info("Getting text of team header")
        return getElementText(AppiumBy.id(teamHeader))
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

    fun scrollAndVerifyTeamStats(apiStatsSet : Set<TeamDataClasses.Stats>): Boolean {
        val dimension = driver!!.manage().window().size
        val start = Point((dimension.width * 0.5).toInt(), (dimension.height * 0.8).toInt())
        val end = Point((dimension.width * 0.5).toInt(), (dimension.height * 0.2).toInt())

        logger.info("Verifying team stats are correctly displayed")

        val teamStatsSet : MutableSet<TeamDataClasses.Stats> = mutableSetOf() // start with empty set
        var swipeNumber = 0         // track swipe numbers to have upper limit of 4 swipes max
        try {
            while(swipeNumber < 5) {
                val teamStatSetSize = teamStatsSet.size
                val listOfStatTitles = findListOfElement(AppiumBy.id(statTitle))
                val listOfStatValues = findListOfElement(AppiumBy.id(statValue))
                val listOfStatRanks = findListOfElement(AppiumBy.id(statRank))
                val minLen = min(listOfStatTitles.size,min(listOfStatValues.size, listOfStatRanks.size))

                for (i in 0 until minLen) {
                    val stat = TeamDataClasses.Stats(
                        getElementText(listOfStatTitles[i]),
                        getElementText(listOfStatValues[i]).toDouble(),
                        removeParenthesis((getElementText(listOfStatRanks[i])))
                    )
                    teamStatsSet.add(stat)
                    if (!apiStatsSet.contains(stat)) {
                        logger.error("Stat: {$stat} does not match with any stat from theScore API data")
                        return false
                    }
                }
                if (teamStatSetSize == teamStatsSet.size) {
                    logger.info("Reached end of team-stats tab") ; break
                }
                swipeNumber += 1
                logger.info("Performing swipe number {$swipeNumber}")
                doSwipe(driver, start, end, 1000) //with duration 1s
            }
            logger.info("Swiped down {$swipeNumber} times to reach end of team-stats page")
            logger.info("Verified all UI team stats match stats from theScore API")
            return true
        }
        catch (e:IndexOutOfBoundsException) {
            logger.fatal("Index out of bounds")
            return false
        }
    }

    fun verifyStatSubHeaderDisplayed(name: String): Boolean {
        val cleanText : String = getElementText(AppiumBy.id(statSubHeaderText)).lowercase(Locale.getDefault()).trim()
        logger.info("Verifying stats sub header text {$cleanText} matches: {$name}")
        return cleanText.contains(name.lowercase(Locale.getDefault()).trim())    }

    fun verifyStatSubHeaderValueDisplayed(value :String): Boolean {
        val cleanVal : String = getElementText(AppiumBy.id(statSubHeaderVal)).lowercase(Locale.getDefault()).trim()
        logger.info("Verifying stats sub header value {$cleanVal} matches: {$value}")
        return cleanVal.contains(value.lowercase(Locale.getDefault()).trim())    }
}