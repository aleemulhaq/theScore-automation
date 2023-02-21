import BaseActions.W3cActions.doSwipe
import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import org.apache.logging.log4j.kotlin.Logging
import org.openqa.selenium.*
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.interactions.Pause
import org.openqa.selenium.interactions.PointerInput
import org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT
import org.openqa.selenium.interactions.PointerInput.Origin.viewport
import org.openqa.selenium.interactions.Sequence
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.time.Duration.ofMillis
import java.util.*


open class BaseActions(val driver:AndroidDriver?) : Logging {

    private fun waitForExpectedCondition(durSeconds : Long, expectedCondition : ExpectedCondition<WebElement>) : WebElement{
        logger.info("Waiting for expected conditions of WebElement")
        return WebDriverWait(driver, Duration.ofSeconds(durSeconds))
            .until(expectedCondition)
    }

    fun getElementText(locator: By): String {
        val element = findElement(locator)
        return getElementText(element)
    }

    fun getElementText(element: WebElement?): String {
        if (element != null) {
            val txt = element.text
            logger.info("Got element text: $txt")
            return txt
        }
        logger.error("Element is null, cannot get WebElement.text property. Returning empty text")
        return ""
    }

    fun isElementSelected(locator: By): Boolean {
        val element = findElement(locator)
        if (element != null) {
            logger.info("Verifying if element is selected")
            return element.isSelected
        }
        logger.error("Element is null, cannot read WebElement.isSelected property")
        return false
    }

    private fun findElement(locator: By): WebElement?{
        logger.info("Waiting to find element")
        var element : WebElement? = try {
            WebDriverWait(driver, Duration.ofSeconds(10))
                .until { driver: WebDriver ->
                    driver.findElement(locator)
                }
        } catch (e : TimeoutException) {
            logger.fatal("Timed out while trying to find element, returning null")
            logger.fatal(e.stackTrace)
            null
        }
        logger.info("Returning found element")
        return element
    }

    fun findListOfElement(locator: By): List<WebElement>{
        logger.info("Waiting to find a list of elements")
        var elementList : List<WebElement> = try {
            WebDriverWait(driver, Duration.ofSeconds(10))
                .until { driver: WebDriver ->
                    driver.findElements(locator)
                }
        } catch (e : TimeoutException) {
            logger.fatal("Timed out while trying to find elements, returning empty list")
            logger.fatal(e.stackTrace)
            return emptyList()
        }
        logger.info("Returning found elements list")
        return elementList
    }

    fun clickElement(locator: By): Boolean {
        logger.info("Waiting for element to be clickable and then click")
        try {
            waitForExpectedCondition(10, ExpectedConditions.elementToBeClickable(locator)).click()
        } catch (e : NoSuchElementException) {
            return false
        } catch (e : TimeoutException) {
            logger.fatal("Timed out while waiting to for element to be clickable")
            logger.fatal(e.stackTrace)
            return false
        }
        logger.info("Clicked element")
        return true
    }

    fun sendKeysToElement(locator: By, keys : String): Boolean {
        logger.info("Waiting for element to be clickable and send keys")
        try {
            waitForExpectedCondition(10, ExpectedConditions.elementToBeClickable(locator)).sendKeys(keys)
        } catch (e : TimeoutException) {
            logger.fatal("Timed out while waiting to for element to be clickable")
            logger.fatal(e.stackTrace)
            return false
        }
        logger.info("Sent keys to text field")
        return true
    }

    fun isElementInvisible(locator: By): Boolean {
        logger.info("Waiting for element to disappear")
        try {
            WebDriverWait(driver, Duration.ofSeconds(35))
                .until(ExpectedConditions.invisibilityOfElementLocated(locator))
        }
        catch (e : TimeoutException) {
            logger.fatal("Timed out while waiting for element to disappear")
            logger.fatal(e.stackTrace)
            return false
        }
        logger.info("Element disappeared and is now invisible")
        return true
    }

    fun w3cTap(element : WebElement) {
        val dimension: Dimension = driver!!.manage().window().size
        val forTap = Point((dimension.width * 0.5) as Int, (dimension.height * 0.9) as Int)
        W3cActions.doTap(driver, forTap, 200) //with duration 200ms
    }

    fun w3cScroll() {
        val dimension = driver!!.manage().window().size
        var start = Point((dimension.width * 0.5).toInt(), (dimension.height * 0.9).toInt())
        var end = Point((dimension.width * 0.2).toInt(), (dimension.height * 0.1).toInt())
        doSwipe(driver!!, start!!, end!!, 1000) //with duration 1s


        Thread.sleep(3000)

        start = Point((dimension.width * 0.2).toInt(), (dimension.height * 0.2).toInt())
        end = Point((dimension.width * 0.5).toInt(), (dimension.height * 0.8).toInt())
        doSwipe(driver!!, start, end, 1000) //with duration 1s

    }

    object W3cActions {
        private val FINGER = PointerInput(PointerInput.Kind.TOUCH, "finger")
        fun doSwipe(driver: AppiumDriver, start: Point, end: Point, duration: Int) {
            val swipe: Sequence = Sequence(FINGER, 1)
                .addAction(FINGER.createPointerMove(ofMillis(0), viewport(), start.getX(), start.getY()))
                .addAction(FINGER.createPointerDown(LEFT.asArg()))
                .addAction(FINGER.createPointerMove(ofMillis(duration.toLong()), viewport(), end.getX(), end.getY()))
                .addAction(FINGER.createPointerUp(LEFT.asArg()))
            driver.perform(Arrays.asList(swipe))
        }

        fun doTap(driver: AppiumDriver, point: Point, duration: Int) {
            val tap: Sequence = Sequence(FINGER, 1)
                .addAction(FINGER.createPointerMove(ofMillis(0), viewport(), point.getX(), point.getY()))
                .addAction(FINGER.createPointerDown(LEFT.asArg()))
                .addAction(Pause(FINGER, ofMillis(duration.toLong())))
                .addAction(FINGER.createPointerUp(LEFT.asArg()))
            driver.perform(Arrays.asList(tap))
        }
    }


    fun t(source : WebElement, target: WebElement) {
        val source: Point = source.location
        val target: Point = target.location
        val finger = PointerInput(PointerInput.Kind.TOUCH, "finger")
        val dragNDrop = org.openqa.selenium.interactions.Sequence(finger, 1)
    }
}