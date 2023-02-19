import io.appium.java_client.android.AndroidDriver
import org.openqa.selenium.WebElement

open class BaseActions(val driver:AndroidDriver?) {

    fun clickElement(element: WebElement?): Boolean {
            element?.click();
        return true;
    }
}