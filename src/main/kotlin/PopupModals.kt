import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver

class PopupModals(driver: AndroidDriver?) : BaseActions(driver) {

    private val closeModalIcon = "dismiss_modal"
    private val splashScreen = "fullscreen_splash_ad"


    fun dismissPopupModal(): Boolean {
        logger.info("Dismissing pop up modal")
        return clickElement(AppiumBy.id(closeModalIcon))
    }

    fun waitForSplashScreenEnd(): Boolean {
        logger.info("Waiting for splash screen to disappear")
        return isElementInvisible(AppiumBy.id(splashScreen))
    }
}