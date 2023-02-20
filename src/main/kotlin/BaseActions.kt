import io.appium.java_client.android.AndroidDriver
import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

open class BaseActions(val driver:AndroidDriver?) {

    private fun waitForExpectedCondition(durSeconds : Long, expectedCondition : ExpectedCondition<WebElement>) : WebElement{
        return WebDriverWait(driver, Duration.ofSeconds(durSeconds))
            .until(expectedCondition)
    }

    fun getElementText(locator: By): String {
        val element = findElement(locator) ?: return ""
        return element.text
    }

    fun isElementSelected(locator: By): Boolean {
        val element = findElement(locator) ?: return false
        return element.isSelected
    }

    fun findElement(locator: By): WebElement?{
        var element : WebElement? = try {
            WebDriverWait(driver, Duration.ofSeconds(10))
                .until { driver: WebDriver ->
                    driver.findElement(locator)
                }
        } catch (e : NoSuchElementException) {
            null
        } catch (e : TimeoutException) {
    //            "Timed out while trying to find element"
            null
        }
        return element
    }

    fun clickElement(locator: By): Boolean {
        try {
            waitForExpectedCondition(10, ExpectedConditions.elementToBeClickable(locator)).click()
        } catch (e : NoSuchElementException) {
            return false
        } catch (e : TimeoutException) {
            //            "Timed out while trying to find element"
            return false
        }
        return true
    }

    fun sendKeysToElement(locator: By, keys : String): Boolean {
        try {
            waitForExpectedCondition(10, ExpectedConditions.elementToBeClickable(locator)).sendKeys(keys)
        } catch (e : NoSuchElementException) {
            return false
        } catch (e : TimeoutException) {
            //            "Timed out while trying to find element"
            return false
        }
        return true
    }

    fun isElementInvisible(locator: By): Boolean {
        try {
            WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.invisibilityOfElementLocated(locator))
        }
        catch (e : TimeoutException) {
            //            "Timed out while waiting for splash screen to disappear"
            return false
        }
        return true
    }
}