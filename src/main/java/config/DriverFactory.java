package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class DriverFactory {

    private DriverFactory() {}

    public static WebDriver createDriver() {
        String browser = ConfigReader.get("browser");
        boolean headless = Boolean.parseBoolean(ConfigReader.get("headless"));

        switch (browser.toLowerCase()) {
            case "chrome":
                return createChromeDriver(headless);
            case "firefox":
                return createFirefoxDriver(headless);
            case "edge":
                return createEdgeDriver();
            default:
                throw new RuntimeException("Desteklenmeyen browser: " + browser);
        }
    }

    private static WebDriver createChromeDriver(boolean headless){
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        if (headless){
            chromeOptions.addArguments("--headless=new");
        }
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--disable-popup-blocking");
        chromeOptions.addArguments("--no-sandbox");
        return new ChromeDriver(chromeOptions);
    }
    private static WebDriver createFirefoxDriver(boolean headless){
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        if(headless){
            firefoxOptions.addArguments("headless");

        }
        return new FirefoxDriver(firefoxOptions);
    }
    private static WebDriver createEdgeDriver(){
        WebDriverManager.edgedriver().setup();
        return new EdgeDriver();
    }
}
