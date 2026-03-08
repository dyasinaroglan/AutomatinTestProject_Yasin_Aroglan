package pages;

import config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CareersPage extends BasePage {

    private static final String careersUrl = ConfigReader.get("careers.url");

    private final By seeAllTeamsButton = By.cssSelector("a.inso-btn.see-more");
    private final By openPositionButton = By.xpath("//a[contains(@href, 'team=Quality%20Assurance') and contains(@class, 'insiderone-icon-cards-grid-item-btn')]"
    );

    public CareersPage(WebDriver driver) {
        super(driver);
    }

    public CareersPage open() {
        driver.get(careersUrl);
        return this;
    }

    public CareersPage clickSeeAllTeams() {
        scrollToElement(seeAllTeamsButton);
        clickWithJS(seeAllTeamsButton);
        return this;
    }

    public LeverJobsPage clickQAOpenPositions() {
        scrollToElement(openPositionButton);
        click(openPositionButton);
        return new LeverJobsPage(driver);
    }

    public boolean isOpenPositionButtonDisplayed() {
        return isElementDisplayed(openPositionButton);
    }
}
