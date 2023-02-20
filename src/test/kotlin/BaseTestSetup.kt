import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import io.appium.java_client.service.local.AppiumDriverLocalService
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import java.net.URL


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class BaseTestSetup {
    //    protected var driver: AndroidDriver<MobileElement>? = null
    protected var driver: AndroidDriver? = null
    private val service: AppiumDriverLocalService = AppiumDriverLocalService.buildDefaultService()
//    protected val options: UiAutomator2Options? = null

    @BeforeAll
    fun setUp() {
//        service = AppiumDriverLocalService.buildDefaultService()
        service.start()
        try {
            // do stuff with drivers
            val options = UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setAvd("Pixel_6_API_33")
                .setApp("/Users/aleemhaq/Documents/theScoreChallenge/theScore.apk")
                .setAutoGrantPermissions(true)
//                .eventTimings()

            this.driver = AndroidDriver( URL("http://127.0.0.1:4723"), options)
        } finally {
//            service.stop()
        }
    }

    @AfterAll
    fun tearDown() {
        this.driver?.quit() ?: throw Exception("Driver instance was unable to quit.")
        service.stop()
    }
}