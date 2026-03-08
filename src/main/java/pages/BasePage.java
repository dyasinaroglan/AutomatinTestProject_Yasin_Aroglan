package pages;

import config.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;

    private static final Duration defaultWait = Duration.ofSeconds(ConfigReader.getInt("default.timeout"));

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, defaultWait);
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickability(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected boolean waitForUrl(String urlFragment) {
        return wait.until(ExpectedConditions.urlContains(urlFragment));
    }

    protected void click(By locator) {
        waitForClickability(locator).click();
    }

    protected String getText(By locator) {
        return waitForVisibility(locator).getText().trim();
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            return waitForVisibility(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected void clickWithJS(By locator) {
        WebElement element = waitForVisibility(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript(
                "window.scrollTo(0, document.body.scrollHeight);"
        );
    }

    protected void scrollToElement(By locator) {
        WebElement element = waitForVisibility(locator);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                element
        );
    }
    public void acceptCookies() {
        By cookieAcceptButton = By.cssSelector("#wt-cli-accept-all-btn");
        try {
            clickWithJS(cookieAcceptButton);
        } catch (Exception e) {
        }
    }
    protected void clickElementByIndex(By locator, int index) {
        List<WebElement> elements = driver.findElements(locator);
        if (elements.isEmpty()) {
            throw new RuntimeException("Element bulunamadı: " + locator);
        }
        elements.get(index).click();
    }
}
