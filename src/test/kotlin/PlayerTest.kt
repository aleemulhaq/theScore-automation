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
        val playerSearchName = "Cristiano Ronaldo"
        val searchView = SearchView(driver)
        assertTrue(searchView.sendKeysSearchBar(playerSearchName))
        assertTrue(searchView.tapSearchResult())
        val player = Player(driver)
        val playerPageName = player.getHeaderPlayerName()
        assertEquals(playerSearchName, playerPageName)
        assertTrue(player.tapInfoTab())
        assertTrue(player.verifyInfoTabSelected())
        assertTrue(player.verifyInfoBodyDisplayed())
        assertTrue(topNavBar.tapBackButton())
        assertTrue(searchView.verifySearchBarDisplayed())
    }
}