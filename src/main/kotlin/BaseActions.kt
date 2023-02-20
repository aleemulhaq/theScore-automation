import io.appium.java_client.android.AndroidDriver
import org.openqa.selenium.WebElement

open class BaseActions(val driver:AndroidDriver?) {

    fun clickElement(element: WebElement?): Boolean {
        if (element == null) {
            return false
        }
        element.click()
        return true
    }

    fun sendKeysToElement(element : WebElement?, keys : String): Boolean {
        if (element == null) {
            return false
        }
        element.sendKeys(keys)
        return true
    }

    fun getElementText(element: WebElement?): String {
        if (element == null) {
            return ""
        }
        return element.text
    }

    fun isElementSelected(element: WebElement?) :Boolean {
        if (element == null) {
            return false
        }
        return element.isSelected
    }
}