import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver

class Onboarding(driver: AndroidDriver?) : BaseActions(driver) {

    private val primaryNextButton = "btn_primary"
    private val sportItemName = "txt_name"


    fun clickOnboardingNext(): Boolean {
        logger.info("Click on onboarding next button")
        return clickElement(AppiumBy.id(primaryNextButton))
    }

    fun clickOnboardingSportItemName(): Boolean {
        logger.info("Click on onboarding sport item/chip")
        return clickElement(AppiumBy.id(sportItemName))
    }
}