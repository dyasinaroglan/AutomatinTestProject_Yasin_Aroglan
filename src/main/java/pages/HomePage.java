package pages;

import config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private static final String url = ConfigReader.get("base.url");

    private final By navigationBar = By.id("navigation");
    private final By platformButton = By.cssSelector("[data-text='Platform']");

    private final By heroSection = By.cssSelector("section.homepage-hero");

    private final By partnersLogoReel = By.cssSelector(".homepage-hero-logo-reel");

    private final By footerLinks = By.cssSelector(".col-12.order-2 .footer-links");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(url);
        return this;
    }
    public boolean isNavigationBarDisplayed(){
        return isElementDisplayed(navigationBar);
    }
    public boolean isNavigationBarPlatformDisplayed(){
        return isElementDisplayed(platformButton);
    }
    public boolean isHeroSectionDisplayed() {
        return isElementDisplayed(heroSection);
    }
    public boolean isPartnersDisplayed(){
        return isElementDisplayed(partnersLogoReel);
    }
    public boolean isFooterDisplayed(){
        scrollToBottom();
        return isElementDisplayed(footerLinks);
    }
}
