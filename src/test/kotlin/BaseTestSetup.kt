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
    //    protected var driver: AndroidDriver<MobileElement>? = null
    protected var driver: AndroidDriver? = null
    private val service: AppiumDriverLocalService = AppiumDriverLocalService.buildDefaultService()
//    protected val options: UiAutomator2Options? = null

    @BeforeAll
    fun setup() {
        setUpLogging() // logger setup here as this is entry points of test run
        logger.trace("Initiating tests")
        val directory = File("");
        val appPath = directory.absolutePath + "/theScore.apk"
//        service = AppiumDriverLocalService.buildDefaultService()
        logger.info("Starting appium service")
        service.start()
        try {
            val options = UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setAvd("Pixel_6_API_33")
                .setApp(appPath)
                .setAutoGrantPermissions(true)

            val serviceUrl = "http://127.0.0.1:4723"
            logger.info("Initializing appium driver at: $serviceUrl")
            driver = AndroidDriver( URL(serviceUrl), options)

            val popupModals = PopupModals(driver)
            assertTrue(popupModals.waitForSplashScreenEnd())
        } finally {
//            service.stop()
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
}