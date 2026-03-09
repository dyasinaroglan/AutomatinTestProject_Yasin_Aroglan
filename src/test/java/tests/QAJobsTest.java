package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CareersPage;
import pages.LeverJobsPage;

public class QAJobsTest extends BaseTest {

    @Test(priority = 2, groups = {"smoke", "regression"},
            description = "Verify QA jobs are listed and filtered correctly on Lever page")
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
        Assert.assertTrue(leverJobsPage.areAllPositionsContaining("Quality"), "Bazı ilanların pozisyonu Quality Assurance içermiyor!");

        // Step 4
        leverJobsPage.clickViewRole(1);
        leverJobsPage.clickApplyForJob(0);

        Assert.assertTrue(leverJobsPage.isApplicationFormDisplayed(), "Form başlığı görünmedi.");
        Assert.assertTrue(leverJobsPage.isOnApplicationFormPage(), "Application form sayfasına yönlendirilmedi! URL: " + leverJobsPage.getCurrentUrl());
    }
}
