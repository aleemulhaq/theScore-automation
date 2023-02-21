import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

    @DisplayName("Verifies team-stats with API endpoints from the @ValueSource annotation")
class TeamStatsTest: BaseTestSetup() {
    @BeforeAll
    fun `navigate through onboarding`(){
        val onboarding = Onboarding(driver)
        assertTrue(onboarding.clickOnboardingNext())
        assertTrue(onboarding.clickOnboardingNext())
        assertTrue(onboarding.clickOnboardingSportItemName(), "Failed to tap on onoarding sport icon/item")
        assertTrue(onboarding.clickOnboardingNext())
        assertTrue(onboarding.clickOnboardingNext(), "Failed to tap on onboarding next")
        val popupModals = PopupModals(driver)
        assertTrue(popupModals.dismissPopupModal(), "Failed to dismiss theScore Bet popup modal")
    }

    @ParameterizedTest
    @DisplayName("Navigates to and verifies the data displayed on a Team's stats page")
    @ValueSource(strings = [SOCCER_TEAM_API_URL, BASKETBALL_TEAM_API_URL, HOCKEY_TEAM_API_URL, FOOTBALL_TEAM_API_URL])
    fun `verify team stats displayed`(apiUrl : String) {
        val topNavBar = TopNavBar(driver)
        assertTrue(topNavBar.tapSearchBar(), "Failed to tap on search bar")
        val teamData = TeamDataClasses(apiUrl)
        val teamSearchName = teamData.getApiTeamProfile()
        val searchView = SearchView(driver)
        assertTrue(searchView.sendKeysSearchBar(teamSearchName), "Unable to enter search text")
        assertTrue(searchView.tapSearchResult(), "Failed to tap on search result")
        val team = Team(driver)
        val teamPageName = team.getHeaderText()
        assertEquals(teamSearchName, teamPageName, "Searched team's name and team-page header text is different")
        assertTrue(team.tapStatsTab(), "Failed to tap on Stats tab")
        assertTrue(team.verifyStatsTabSelected(), "Failed to verify Stats tab selected")
        assertTrue(team.scrollAndVerifyTeamStats(teamData.getSetOfStatsList()),"Failed while scroll to verify team-stats data")
        assertTrue(topNavBar.tapBackButton(),"Failed to tap on Back button")
        assertEquals(teamSearchName, searchView.getSearchBarText(),"Search bar does not show text of previous search")
    }

    @AfterEach
    fun `navigate back to homepage`() {
        val topNavBar = TopNavBar(driver)
        assertTrue(topNavBar.tapBackButton(),"Failed to tap on Back button")
    }
}