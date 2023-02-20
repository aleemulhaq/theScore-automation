import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PlayerTest: BaseTestSetup() {

    @Test()
    fun navigateToPlayerInfo() {
        val onboarding = Onboarding(driver)
        assertTrue(onboarding.clickOnboardingNext())
        assertTrue(onboarding.clickOnboardingNext())
        assertTrue(onboarding.clickOnboardingSportItemName())
        assertTrue(onboarding.clickOnboardingNext())
        assertTrue(onboarding.clickOnboardingNext())
        val popupModals = PopupModals(driver)
        assertTrue(popupModals.dismissPopupModal())
        val topNavBar = TopNavBar(driver)
        assertTrue(topNavBar.tapSearchBar())
        val playerName = "Cristiano Ronaldo"
        val searchView = SearchView(driver)
        assertTrue(searchView.sendKeysSearchBar(playerName))
        assertTrue(searchView.tapSearchResult())
        val player = Player(driver)
        assertEquals(playerName, player.getHeaderPlayerName())
        assertTrue(player.tapInfoTab())
        assertTrue(player.verifyInfoTabSelected())
        assertTrue(player.verifyInfoBodyDisplayed())
    }
}