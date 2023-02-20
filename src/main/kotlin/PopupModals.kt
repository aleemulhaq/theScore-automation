import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class PopupModals(driver: AndroidDriver?) : BaseActions(driver) {

    private val closeModalIcon = "dismiss_modal"
    private val splashScreen = "fullscreen_splash_ad"


    fun dismissPopupModal(): Boolean {
        val element : WebElement? = WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(AppiumBy.id(closeModalIcon)))
        return clickElement(element)
    }

    fun waitForSplashScreenEnd(): Boolean {
//        val element : WebElement? = WebDriverWait(driver, Duration.ofSeconds(30))
//            .until(ExpectedConditions.invisibilityOfElementLocated(AppiumBy.id(splashScreen)))
//        return clickElement(element)
        return WebDriverWait(driver, Duration.ofSeconds(30))
            .until(ExpectedConditions.invisibilityOfElementLocated(AppiumBy.id(splashScreen)))
    }
}