import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver

class SubTabBar(driver: AndroidDriver?) : BaseActions(driver) {

    private val teamsTab = "Teams"
    private val statsTab = "Team Stats"

    fun tapTeamsTab(): Boolean {
        logger.info("Tapping team teams tab")
        return clickElement(AppiumBy.accessibilityId(teamsTab))
    }

    fun tapStatsTab(): Boolean {
        logger.info("Tapping team stats tab")
        return clickElement(AppiumBy.accessibilityId(statsTab))
    }

    fun verifyStatsTabSelected(): Boolean {
        logger.info("Verifying team stats tab is selected")
        return isElementSelected(AppiumBy.accessibilityId(statsTab))
    }
}