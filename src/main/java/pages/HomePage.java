package pages;

import config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private static final String url = ConfigReader.get("base.url");

    private final By acceptCookie = By.cssSelector("#wt-cli-accept-all-btn, .wt-cli-accept-all-btn");

    private final By navigationBar = By.id("navigation");
    private final By PlatformButton = By.cssSelector("[data-text='Platform']");

    private final By industriesButton = By.cssSelector("[data-text='Industries']");
    private final By customersLinkButton = By.cssSelector("[data-text='Customers']");
    private final By resourcesButton = By.cssSelector("[data-text='Resources']");

    private final By heroSection = By.cssSelector("section.homepage-hero");
    private final By heroTitle = By.cssSelector(".homepage-hero-content-title h1");

    private final By partnersLogoReel = By.cssSelector(".homepage-hero-logo-reel");

    private final By footerLinks = By.cssSelector(".col-12.order-2 .footer-links");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(url);
        return this;
    }

    public void acceptCookies() {
        try {
            click(acceptCookie);
        } catch (TimeoutException e) {
        }
    }
    public boolean isNavigationBarDisplayed(){
        return isElementDisplayed(navigationBar);
    }
    public boolean isNavigationBarPlatformDisplayed(){
        return isElementDisplayed(PlatformButton);
    }
    public boolean isHeroSectionDisplayed() {
        return isElementDisplayed(heroSection);
    }
    public boolean isHeroTitleDisplayed() {
        return isElementDisplayed(heroTitle);
    }
    public boolean isPartnersDisplayed(){
        return isElementDisplayed(partnersLogoReel);
    }
    public boolean isFooterDisplayed(){
        scrollToBottom();
        return isElementDisplayed(footerLinks);
    }
}
