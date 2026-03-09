package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class HomePageTest extends BaseTest {

    @Test(priority = 1, groups = {"smoke"}, description = "Verify Insider home page is opened and all main blocks are loaded")
    public void testHomePageLoaded() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.acceptCookies();

        Assert.assertTrue(homePage.isNavigationBarDisplayed(), "Navigation bar görünmüyor!");
        Assert.assertTrue(homePage.isHeroSectionDisplayed(), "Hero section görünmüyor!");
        Assert.assertTrue(homePage.isPartnersDisplayed(), "Partners logo reel görünmüyor!");
        Assert.assertTrue(homePage.isFooterDisplayed(), "Footer görünmüyor!");
    }
}
