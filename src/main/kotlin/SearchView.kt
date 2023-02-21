import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver

class SearchView(driver: AndroidDriver?) : BaseActions(driver) {

    private val searchBar = "search_src_text"
    private val searchItemName = "txt_name"

    fun tapSearchResult(): Boolean {
        logger.info("Click on search result")
        return clickElement(AppiumBy.id(searchItemName))
    }

    fun sendKeysSearchBar(keys : String): Boolean {
        logger.info("Sending keys to search bar")
        return sendKeysToElement(AppiumBy.id(searchBar), keys)
    }

    fun getSearchBarText(): String {
        logger.info("Get current text of search bar")
        return getElementText(AppiumBy.id(searchBar))
    }
}