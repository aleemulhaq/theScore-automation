import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import io.appium.java_client.service.local.AppiumDriverLocalService
import org.apache.logging.log4j.kotlin.Logging
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import java.io.File
import java.net.URL

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class BaseTestSetup : Logging {
    protected var driver: AndroidDriver? = null
    private val service: AppiumDriverLocalService = AppiumDriverLocalService.buildDefaultService()

    @BeforeAll
    fun setup() {
        setUpLogging() // logger setup here as this is entry points of test run
        appiumServiceStart() // start appium 2.0 service

        logger.trace("Initiating tests")
        try {
            // we can add conditional here for optional iphone testing setup
            driver = appiumAndroidSetup()
            val popupModals = PopupModals(driver)
            assertTrue(popupModals.waitForSplashScreenEnd())
        } catch (e: Exception) {
            logger.fatal("Exception thrown while initializing appium service and driver")
        }
    }

    @AfterAll
    fun tearDown() {
        logger.info("Quitting appium driver")
        this.driver?.quit() ?: throw Exception("Driver instance was unable to quit.")
        logger.info("Quitting appium service")
        service.stop()
        logger.trace("Concluded all tests")
    }

    // appium server 2.0 setup
    private fun appiumServiceStart() {
        logger.info("Starting appium service")
        service.start()
    }

    // android driver setup
    private fun appiumAndroidSetup() : AndroidDriver? {

        val directory = File("");
        val appPath = directory.absolutePath + "/theScore.apk"
        val options = UiAutomator2Options()
            .setPlatformName("Android")
            .setAutomationName("UiAutomator2")
            .setAvd("Pixel_6_API_33") // we can eventually pass device udid's through command line properties/args
            .setApp(appPath)
            .setAutoGrantPermissions(true)

        val serviceUrl = "http://127.0.0.1:4723"
        logger.info("Initializing Android Uiautomator2 driver at: $serviceUrl")
        return AndroidDriver( URL(serviceUrl), options)
    }
}