package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class LeverJobsPage extends BasePage {

    private final By openLocationFilter = By.cssSelector("[aria-label*='Filter by Location:']");

    private final By departmentTitle = By.xpath("//div[contains(@class, 'posting-category-title') and contains(text(), 'Quality Assurance')]");
    private final By postingNames = By.cssSelector("[data-qa='posting-name']");
    private final By postingLocations = By.xpath("//span[contains(@class, 'posting-category') and contains(@class, 'location')]");
    private final By applyButtons = By.cssSelector("a.postings-btn.template-btn-submit");

    private final By postingLinks = By.cssSelector("a.posting-title");

    private final By applicationFormTitle = By.xpath("//*[contains(text(), 'Submit your application')]");

    public LeverJobsPage(WebDriver driver) {
        super(driver);
    }

    public LeverJobsPage openLocationFilter() {
        click(openLocationFilter);
        return this;
    }

    public LeverJobsPage selectLocationByText(String locationText) {
        By option = By.xpath(
                "//div[@class='filter-popup']//a[text()='" + locationText + "']"
        );
        click(option);
        return this;
    }

    public String getDepartmentTitle() {
        return getText(departmentTitle);
    }

    public List<String> getAllPositionNames() {
        List<WebElement> titles = driver.findElements(postingNames);
        List<String> names = new ArrayList<>();
        for (WebElement title : titles) {
            names.add(title.getText());
        }
        return names;
    }

    public List<String> getAllLocations() {
        List<WebElement> locs = driver.findElements(postingLocations);
        List<String> locations = new ArrayList<>();
        for (WebElement loc : locs) {
            locations.add(loc.getText());
        }
        return locations;
    }

    public boolean areAllLocationsContaining(String expectedLocation) {
        List<String> locations = getAllLocations();
        if (locations.isEmpty()) return false;
        for (String location : locations) {
            if (!location.toLowerCase().contains(expectedLocation.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    public boolean areAllPositionsContaining(String expectedText) {
        for (String position : getAllPositionNames()) {
            if (!position.toLowerCase().contains(expectedText.toLowerCase())) {
                return false;
            }
        }
        return !getAllPositionNames().isEmpty();
    }

    public void clickViewRole(int index) {
        clickElementByIndex(postingLinks, index);
    }

    public void clickApplyForJob(int index) {
        clickElementByIndex(applyButtons, index);
    }

    public boolean isApplicationFormDisplayed() {
        return isElementDisplayed(applicationFormTitle);
    }

    public boolean isOnApplicationFormPage() {
        return getCurrentUrl().contains("/apply");
    }
}
