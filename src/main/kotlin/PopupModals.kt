import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver

class PopupModals(driver: AndroidDriver?) : BaseActions(driver) {

    private val closeModalIcon = "dismiss_modal"
    private val splashScreen = "fullscreen_splash_ad"


    fun dismissPopupModal(): Boolean {
        return clickElement(AppiumBy.id(closeModalIcon))
    }

    fun waitForSplashScreenEnd(): Boolean {
        return isElementInvisible(AppiumBy.id(splashScreen))
    }
}