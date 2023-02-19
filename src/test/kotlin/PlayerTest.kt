import io.appium.java_client.service.local.AppiumServiceBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class PlayerTest: BaseTestSetup() {

    @Test()
    fun navigateToPlayerInfo() {
        Assertions.assertTrue(true)
    }
}