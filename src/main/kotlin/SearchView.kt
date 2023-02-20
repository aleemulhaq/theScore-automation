import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class SearchView(driver: AndroidDriver?) : BaseActions(driver) {

    private val searchBar = "search_src_text"
    private val searchItemName = "txt_name"

    fun tapSearchResult(): Boolean {
        val element : WebElement? = WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(AppiumBy.id(searchItemName)))
        return clickElement(element)
    }

    fun sendKeysSearchBar(keys : String): Boolean {
        val element : WebElement? = WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(AppiumBy.id(searchBar)))
        return sendKeysToElement(element, keys)
    }

    fun getSearchBarText(): String {
        val element = WebDriverWait(driver, Duration.ofSeconds(10))
            .until { driver: WebDriver ->
                driver.findElement(AppiumBy.id(searchBar))
            }
//            wait.until(ExpectedConditions.visibilityOf(element))
        return getElementText(element)
    }
}