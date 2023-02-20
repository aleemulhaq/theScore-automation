import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class Onboarding(driver: AndroidDriver?) : BaseActions(driver) {

    private val primaryNextButton = "btn_primary"
    private val sportItemName = "txt_name"


    fun clickOnboardingNext(): Boolean {
        val element : WebElement? = WebDriverWait(driver, Duration.ofSeconds(20))
            .until(ExpectedConditions.elementToBeClickable(AppiumBy.id(primaryNextButton)))
        return clickElement(element)
    }

    fun clickOnboardingSportItemName(): Boolean {
        val element : WebElement? = WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(AppiumBy.id(sportItemName)))
        return clickElement(element)
    }
}