import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import io.appium.java_client.service.local.AppiumDriverLocalService
import io.appium.java_client.service.local.AppiumServiceBuilder
import org.apache.logging.log4j.kotlin.Logging
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.openqa.selenium.SessionNotCreatedException
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class BaseTestSetup : Logging {
    protected var driver: AndroidDriver? = null
    private val service: AppiumDriverLocalService = buildService()
    private var firstAndroidAdbDeviceID: String = ""

    @BeforeAll
    fun setup() {
        try {
            setUpLogging() // logger setup here as this is entry points of test run
            appiumServiceStart() // start appium 2.0 service

            logger.trace("Initiating tests")
            logger.info("Initiating tests")

            // we can add conditional here for optional iphone testing setup
            driver = appiumAndroidSetup()
            if (!service.isRunning) logger.fatal("Appium driver NULL")
            if (driver == null) logger.fatal("Appium driver NULL")
            logger.info("Appium service initialized: {${service.isRunning}")
            logger.info("Appium driver context: {${driver?.context}")
            logger.info("Appium driver capabilities: {${driver?.capabilities}")

            //wait for splash screen (assuming noReset = false. (false by default)
            val popupModals = PopupModals(driver)
            assertTrue(popupModals.waitForSplashScreenEnd())
        } catch (e: IllegalArgumentException) {
            logger.fatal("Exception thrown while initializing appium service and driver." +
                    "Server port/address is likely already in use. Please kill appium server process and try again.")
            tearDown()

        }
        catch (e: SessionNotCreatedException) {
            logger.fatal("Appium server session not created exception thrown!")
            tearDown()
        }
        catch (e: NullPointerException) {
            logger.fatal("Null pointer exception thrown while initializing appium service.")
            tearDown()
        }
    }

    @AfterAll
    fun tearDown() {
        logger.info("Terminating test run")
        logger.info("Quitting appium driver")
        driver?.quit() ?: throw Exception("Driver instance was unable to quit.")
        logger.info("Quitting appium service")
        service.stop()
        logger.trace("Concluded all tests")
    }

    private fun buildService() :  AppiumDriverLocalService {
        logger.info("Building appium service")
        val serviceBuilder = AppiumServiceBuilder()
        serviceBuilder.usingAnyFreePort()
        return AppiumDriverLocalService.buildService(serviceBuilder)
    }

    // appium server 2.0 setup
    private fun appiumServiceStart() {
        buildService()
        logger.info("Starting appium service")
        service.start()
    }

    // android driver setup
    private fun appiumAndroidSetup() : AndroidDriver {
        getListOfConnectedAndroidDevices()
        val options = setUIAutomator2Options()
        // start service
        val serviceUrl = service.url
        logger.info("Initializing Android Uiautomator2 driver at: $serviceUrl")
        return AndroidDriver(serviceUrl, options)
    }

    private fun setUIAutomator2Options() : UiAutomator2Options{
        val directory = File("")
        val appPath = directory.absolutePath + "/theScore.apk"
        val options = UiAutomator2Options()
            .setPlatformName("Android")
            .setAutomationName("UiAutomator2")
            .setApp(appPath)
            .setAutoGrantPermissions(true)
            .setAppWaitForLaunch(true)

        // then it is a hardware or connected emulator device
        // if no connected devices, then try to launch Pixel_6_API_33 through android sdk
        // default to pixel 6 if adb devices list failed to retrieve hardware device
        if (firstAndroidAdbDeviceID != "") options.setUdid(firstAndroidAdbDeviceID) else options.setAvd("Pixel_6_API_33")
        return options
    }

    // run adb command and parse result to get a list of devices
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
                var deviceIds = output.lines()?.mapNotNull {
                    val matches = """^([a-zA-Z0-9\-]+)(\s+)(device)""".toRegex().find(it)
                    matches?.groupValues?.get(1)
                }
                if (deviceIds != null && deviceIds.isNotEmpty()) {
                    logger.info("Successfully grabbed list of devices: {$deviceIds}")
                    logger.info("Returning first available device id: {$deviceIds[0]}")
                    firstAndroidAdbDeviceID = deviceIds[0]
                }
            } else {
                logger.fatal("Commandline process to fetch adb device list failed to terminate!.")
                firstAndroidAdbDeviceID = ""
            }
        } catch (e: IOException) {
            logger.fatal("Commandline process to fetch adb device list failed when reading process stream.")
        } catch (e: InterruptedException) {
            logger.fatal("Commandline process to fetch adb device list failed. Process was interrupted.")
        }
        catch (e: IllegalArgumentException) {
            logger.fatal("Commandline process to fetch adb device list failed. Process was interrupted.")
        }
    }
}