import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration


class TopNavBar(driver: AndroidDriver?) : BaseActions(driver) {

    private val searchBar = "search_bar_text_view"
    private val backButton = "Navigate up"


    fun tapSearchBar(): Boolean {
        val element : WebElement? = WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(AppiumBy.id(searchBar)))
        return clickElement(element)
    }

    fun tapBackButton(): Boolean {
        val element : WebElement? = WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId(backButton)))
        return clickElement(element)
    }
}