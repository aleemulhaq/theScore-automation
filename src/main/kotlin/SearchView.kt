import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver

class SearchView(driver: AndroidDriver?) : BaseActions(driver) {

    private val searchBar = "search_src_text"
    private val searchItemName = "txt_name"

    fun tapSearchResult(): Boolean {
        return clickElement(AppiumBy.id(searchItemName))
    }

    fun sendKeysSearchBar(keys : String): Boolean {
        return sendKeysToElement(AppiumBy.id(searchBar), keys)
    }

    fun getSearchBarText(): String {
        return getElementText(AppiumBy.id(searchBar))
    }
}