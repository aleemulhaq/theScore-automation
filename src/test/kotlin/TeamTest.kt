import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class TeamTest: BaseTestSetup() {
    @BeforeAll
    fun navigateThroughOnboarding(){
        val onboarding = Onboarding(driver)
        assertTrue(onboarding.clickOnboardingNext())
        assertTrue(onboarding.clickOnboardingNext())
        assertTrue(onboarding.clickOnboardingSportItemName())
        assertTrue(onboarding.clickOnboardingNext())
        assertTrue(onboarding.clickOnboardingNext())
        val popupModals = PopupModals(driver)
        assertTrue(popupModals.dismissPopupModal())
    }
    
    @Test()
    fun navigateToTeamStats() {
        val topNavBar = TopNavBar(driver)
        assertTrue(topNavBar.tapSearchBar())
        val teamSearchName = "Boston Celtics"
        val searchView = SearchView(driver)
        assertTrue(searchView.sendKeysSearchBar(teamSearchName))
        assertTrue(searchView.tapSearchResult())
        val team = Team(driver)
        val teamPageName = team.getHeaderText()
        assertEquals(teamSearchName, teamPageName)
        assertTrue(team.tapStatsTab())
        assertTrue(team.verifyStatsTabSelected())
        assertTrue(team.verifyStatsBodyDisplayed())
        assertTrue(topNavBar.tapBackButton())
        assertEquals(teamSearchName, searchView.getSearchBarText())
    }

    @AfterEach
    fun navigateBackToHomePage() {
        val topNavBar = TopNavBar(driver)
        assertTrue(topNavBar.tapBackButton())
    }
}