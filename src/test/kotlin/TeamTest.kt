import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

    @DisplayName("Pass the method parameters provided by the @ValueSource annotation")
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

    @ParameterizedTest
    @DisplayName("Should pass a non-null message to our test method")
    @ValueSource(strings = [SOCCER_TEAM_API_URL, BASKETBALL_TEAM_API_URL, HOCKEY_TEAM_API_URL, FOOTBALL_TEAM_API_URL])
    fun `verify team stats displayed`(apiUrl : String) {
        val topNavBar = TopNavBar(driver)
        assertTrue(topNavBar.tapSearchBar())
        val teamDataClass = TeamDataClasses(apiUrl)
        val teamSearchName = teamDataClass.getApiTeamProfile()
        val searchView = SearchView(driver)
        assertTrue(searchView.sendKeysSearchBar(teamSearchName))
        assertTrue(searchView.tapSearchResult())
        val team = Team(driver)
        val teamPageName = team.getHeaderText()
        assertEquals(teamSearchName, teamPageName)
        assertTrue(team.tapStatsTab())
        assertTrue(team.verifyStatsTabSelected())
        assertTrue(team.verifyStatsBodyDisplayed(teamDataClass.getSetOfStatsList()))
        assertTrue(topNavBar.tapBackButton())
        assertEquals(teamSearchName, searchView.getSearchBarText())
    }

    @AfterEach
    fun navigateBackToHomePage() {
        val topNavBar = TopNavBar(driver)
        assertTrue(topNavBar.tapBackButton())
    }
}