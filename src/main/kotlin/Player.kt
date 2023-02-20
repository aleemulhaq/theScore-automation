import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class Player(driver: AndroidDriver?) : BaseActions(driver) {

    private val headerPlayerName = "txt_player_name"
    private val infoTab = "Info"
    private val infoTitle = "title"
    private val infoValue = "value"

    fun getHeaderPlayerName(): String {
        val element = WebDriverWait(driver, Duration.ofSeconds(10))
            .until { driver: WebDriver ->
                driver.findElement(AppiumBy.id(headerPlayerName))
            }
        return getElementText(element)
    }

    fun tapInfoTab(): Boolean {
//        val element : WebElement? = WebDriverWait(driver, Duration.ofSeconds(10))
//            .until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId(infoTab)))
        val element = WebDriverWait(driver, Duration.ofSeconds(10))
            .until { driver: WebDriver ->
                driver.findElement(AppiumBy.accessibilityId(infoTab))
            }
        return clickElement(element)
    }

    fun verifyInfoTabSelected(): Boolean {
        val element = WebDriverWait(driver, Duration.ofSeconds(10))
            .until { driver: WebDriver ->
                driver.findElement(AppiumBy.accessibilityId(infoTab))
            }
        return isElementSelected(element)
    }

    fun verifyInfoBodyDisplayed(): Boolean {
        val titleEl = WebDriverWait(driver, Duration.ofSeconds(10))
            .until { driver: WebDriver ->
                driver.findElement(AppiumBy.id(infoTitle))
            }
        val valueEl = WebDriverWait(driver, Duration.ofSeconds(10))
            .until { driver: WebDriver ->
                driver.findElement(AppiumBy.id(infoValue))
            }
        return titleEl != null && valueEl != null

    }
}