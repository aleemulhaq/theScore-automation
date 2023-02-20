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

    fun verifyStatsBodyDisplayed(): Boolean {
        return findElement(AppiumBy.id(statTitle)) != null && findElement(AppiumBy.id(statValue)) != null
    }
}