package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CareersPage;
import pages.HomePage;
import pages.LeverJobsPage;

public class InsiderTest extends BaseTest {

    @Test(description = "Verify Insider home page is opened and all main blocks are loaded")
    public void testHomePageLoaded(){
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.acceptCookies();

        Assert.assertTrue(homePage.isNavigationBarDisplayed(), "Navigation bar görünmüyor!");
        Assert.assertTrue(homePage.isHeroSectionDisplayed(),"Hero section görünmüyor!");
        Assert.assertTrue(homePage.isPartnersDisplayed(), "Partners logo reel görünmüyor!");
        Assert.assertTrue(homePage.isFooterDisplayed(),"Footer görünmüyor!");
    }
    @Test(description = "Verify QA jobs are listed and filtered correctly on Lever page")
    public void testQAJobsFilterAndValidation(){
        CareersPage careersPage = new CareersPage(getDriver());
        LeverJobsPage leverJobsPage = new LeverJobsPage(getDriver());

        careersPage.open();
        careersPage.acceptCookies();
        careersPage.clickSeeAllTeams();
        Assert.assertTrue(careersPage.isOpenPositionButtonDisplayed(),"Quality Assurance kartı görünmüyor!");
        careersPage.clickQAOpenPositions();


        leverJobsPage.openLocationFilter();
        leverJobsPage.selectLocationByText("Istanbul, Turkiye");

        Assert.assertEquals(leverJobsPage.getDepartmentTitle().toUpperCase(),"QUALITY ASSURANCE","Department başlığı yanlış!");
        Assert.assertTrue(leverJobsPage.areAllLocationsContaining("Istanbul, Turkiye"), "Bazı ilanların lokasyonu Istanbul değil!");
        Assert.assertTrue(leverJobsPage.areAllPositionsContaining("Quality Assurance"), "Bazı ilanların pozisyonu Quality Assurance içermiyor!");
    }
}
