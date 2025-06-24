package test.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import static org.junit.Assert.*;

public class UITest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://torano-clone-bluet52hz.web.app/";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testResponsiveDesign() {
        driver.get(BASE_URL);
        
        // Test desktop view
        Dimension desktopSize = new Dimension(1920, 1080);
        driver.manage().window().setSize(desktopSize);
        assertTrue(isNavigationVisible());
        
        // Test tablet view
        Dimension tabletSize = new Dimension(768, 1024);
        driver.manage().window().setSize(tabletSize);
        assertTrue(isNavigationVisible());
        
        // Test mobile view
        Dimension mobileSize = new Dimension(375, 667);
        driver.manage().window().setSize(mobileSize);
        assertTrue(isMobileMenuVisible());
    }

    @Test
    public void testImageLoading() {
        driver.get(BASE_URL);
        
        List<WebElement> images = driver.findElements(By.tagName("img"));
        for (WebElement img : images) {
            assertTrue("Image should be loaded", 
                ((JavascriptExecutor) driver).executeScript(
                    "return arguments[0].complete && " +
                    "typeof arguments[0].naturalWidth != 'undefined' && " +
                    "arguments[0].naturalWidth > 0", img).equals(true));
        }
    }

    @Test
    public void testLayoutConsistency() {
        driver.get(BASE_URL);
        
        // Check header position
        WebElement header = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("header")));
        assertTrue(header.getLocation().getY() == 0);
        
        // Check footer position
        WebElement footer = driver.findElement(By.tagName("footer"));
        assertTrue(footer.getLocation().getY() > 0);
    }

    private boolean isNavigationVisible() {
        try {
            return driver.findElement(By.cssSelector("nav")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isMobileMenuVisible() {
        try {
            return driver.findElement(By.cssSelector(".mobile-menu")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
