import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver

class PopupModals(driver: AndroidDriver?) : BaseActions(driver) {

    private val closeModalIcon = "dismiss_modal"
    private val splashScreen = "fullscreen_splash_ad"
    private val serverErrorCloseButton = "Close" // shows when theScore is having server issues/"technical difficulties"


    fun dismissPopupModal(): Boolean {
        logger.info("Checking for unexpected theScore-server-down pop up")
        clickElement(AppiumBy.accessibilityId(serverErrorCloseButton)) // no need to assert this, as it's a rare occurrence
        logger.info("Dismissing pop up modal")
        return clickElement(AppiumBy.id(closeModalIcon))
    }

    fun waitForSplashScreenEnd(): Boolean {
        logger.info("Waiting for splash screen to disappear")
        return isElementInvisible(AppiumBy.id(splashScreen))
    }
}