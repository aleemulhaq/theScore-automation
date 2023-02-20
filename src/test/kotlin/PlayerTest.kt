import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PlayerTest: BaseTestSetup() {

    @Test()
    fun navigateToPlayerInfo() {
        val onboarding = Onboarding(driver)
        Assertions.assertTrue(onboarding.clickOnboardingNext())
        Assertions.assertTrue(onboarding.clickOnboardingNext())
        Assertions.assertTrue(onboarding.clickOnboardingSportItemName())
        Assertions.assertTrue(onboarding.clickOnboardingNext())
        Assertions.assertTrue(onboarding.clickOnboardingNext())
    }
}