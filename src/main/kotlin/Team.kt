import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class Team(driver: AndroidDriver?) : BaseActions(driver) {

    private val teamHeader = "team_name"
    private val statsTab = "Team Stats"
    private val infoTitle = "title"
    private val infoValue = "value"

    fun getHeaderText(): String {
        val element = WebDriverWait(driver, Duration.ofSeconds(10))
            .until { driver: WebDriver ->
                driver.findElement(AppiumBy.id(teamHeader))
            }
        return getElementText(element)
    }

    fun tapStatsTab(): Boolean {
//        val element : WebElement? = WebDriverWait(driver, Duration.ofSeconds(10))
//            .until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId(infoTab)))
        val element = WebDriverWait(driver, Duration.ofSeconds(10))
            .until { driver: WebDriver ->
                driver.findElement(AppiumBy.accessibilityId(statsTab))
            }
        return clickElement(element)
    }

    fun verifyStatsTabSelected(): Boolean {
        val element = WebDriverWait(driver, Duration.ofSeconds(10))
            .until { driver: WebDriver ->
                driver.findElement(AppiumBy.accessibilityId(statsTab))
            }
        return isElementSelected(element)
    }

    fun verifyStatsBodyDisplayed(): Boolean {
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