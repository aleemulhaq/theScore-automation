import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import io.appium.java_client.service.local.AppiumDriverLocalService
import org.apache.logging.log4j.kotlin.Logging
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class BaseTestSetup : Logging {
    protected var driver: AndroidDriver? = null
    private val service: AppiumDriverLocalService = AppiumDriverLocalService.buildDefaultService()
    private var firstAndroidAdbDeviceID: String? = null

    @BeforeAll
    fun setup() {
        try {
            setUpLogging() // logger setup here as this is entry points of test run
            appiumServiceStart() // start appium 2.0 service
            logger.trace("Initiating tests")
            // we can add conditional here for optional iphone testing setup
            driver = appiumAndroidSetup()
            logger.info("Appium Driver capabilities: {${driver?.capabilities}")
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
        getListOfConnectedAndroidDevices()

        val directory = File("");
        val appPath = directory.absolutePath + "/theScore.apk"
        val options = UiAutomator2Options()
            .setPlatformName("Android")
            .setAutomationName("UiAutomator2")
            .setApp(appPath)
            .setAutoGrantPermissions(true)

        if (firstAndroidAdbDeviceID == "" || firstAndroidAdbDeviceID == null) {
            options.setAvd("Pixel_6_API_33") // default to pixel 6 if adb devices list failed
        }
        else if (firstAndroidAdbDeviceID!!.contains("emulator")) {
            options.setUdid(firstAndroidAdbDeviceID) // if it is some other emulator
        }
        else{
            options.setUdid(firstAndroidAdbDeviceID) // else, its a hardware device
        }

        val serviceUrl = "http://127.0.0.1:4723"
        logger.info("Initializing Android Uiautomator2 driver at: $serviceUrl")
        return AndroidDriver( URL(serviceUrl), options)
    }

    private fun getListOfConnectedAndroidDevices() {
        val cmd = "\$ANDROID_HOME/platform-tools/adb devices -l"
        val processBuilder = ProcessBuilder()
        processBuilder.command("bash", "-c", cmd)
        try {
            val process = processBuilder.start()
            val output = StringBuilder()
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            var line: String = reader.readText()
            output.append(line)
            val exitVal = process.waitFor()
            if (exitVal == 0) {
                logger.info("Successfully grabbed list of devices")
                var deviceIds = output.lines()?.mapNotNull {
                    val matches = """^([a-zA-Z0-9\-]+)(\s+)(device)""".toRegex().find(it)
                    matches?.groupValues?.get(1)
                }
                if (deviceIds != null) {
                    firstAndroidAdbDeviceID = deviceIds[0]
                }
//                exitProcess(0)
            } else {
                logger.fatal("Command line process to fetch adb device list, failed to exit 0.")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}