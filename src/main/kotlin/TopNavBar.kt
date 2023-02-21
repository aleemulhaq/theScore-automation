import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver


class TopNavBar(driver: AndroidDriver?) : BaseActions(driver) {

    private val searchBar = "search_bar_text_view"
    private val backButton = "Navigate up"

    fun tapSearchBar(): Boolean {
        logger.info("Clicking on search bar")
        return clickElement(AppiumBy.id(searchBar))
    }

    fun tapBackButton(): Boolean {
        logger.info("Clicking on Back button")
        return clickElement(AppiumBy.accessibilityId(backButton))
    }
}