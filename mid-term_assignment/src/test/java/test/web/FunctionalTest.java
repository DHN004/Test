package test.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import static org.junit.Assert.*;

public class FunctionalTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://torano-clone-bluet52hz.web.app/";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver"); // Đường dẫn tới ChromeDriver
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // Bỏ comment nếu muốn chạy không giao diện
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testNavigation() {
        driver.get(BASE_URL);
        
        // Test navigation to Shop page
        WebElement shopLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("SHOP")));
        shopLink.click();
        assertTrue(driver.getCurrentUrl().contains("/shop"));
        
        // Test navigation to About page
        WebElement aboutLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("ABOUT")));
        aboutLink.click();
        assertTrue(driver.getCurrentUrl().contains("/about"));
    }

    @Test
    public void testProductSearch() {
        driver.get(BASE_URL + "shop");
        
        // Test search functionality
        WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='search']")));
        searchInput.sendKeys("shirt");
        
        // Wait for search results
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product-item")));
        
        // Verify search results
        assertTrue(driver.findElements(By.cssSelector(".product-item")).size() > 0);
    }

    @Test
    public void testShoppingCart() {
        driver.get(BASE_URL + "shop");
        
        // Add product to cart
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector(".product-item button")));
        addToCartButton.click();
        
        // Navigate to cart
        WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector(".cart-icon")));
        cartIcon.click();
        
        // Verify product is in cart
        assertTrue(driver.findElements(By.cssSelector(".cart-item")).size() > 0);
    }

    @Test
    public void testHomePageDisplayAndResponsive() {
        driver.get(BASE_URL);
        // Kiểm tra header, footer hiển thị
        assertTrue(driver.findElement(By.tagName("header")).isDisplayed());
        assertTrue(driver.findElement(By.tagName("footer")).isDisplayed());
        // Responsive: mobile
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(375, 667));
        assertTrue(driver.findElement(By.cssSelector(".mobile-menu")).isDisplayed());
        // Responsive: tablet
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(768, 1024));
        assertTrue(driver.findElement(By.tagName("nav")).isDisplayed());
        // Desktop
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
        assertTrue(driver.findElement(By.tagName("nav")).isDisplayed());
    }

    @Test
    public void testMenuTabs() {
        driver.get(BASE_URL);
        String[] tabs = {"HOME", "SHOP", "ABOUT", "CONTACT"};
        for (String tab : tabs) {
            WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(tab)));
            link.click();
            assertTrue(driver.getCurrentUrl().toLowerCase().contains(tab.toLowerCase().replace("home", "")));
            driver.get(BASE_URL);
        }
    }

    @Test
    public void testProductDetailDisplay() {
        driver.get(BASE_URL + "shop");
        WebElement firstProduct = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".product-item")));
        String productName = firstProduct.findElement(By.cssSelector(".product-title")).getText();
        firstProduct.click();
        WebElement detailTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-detail-title")));
        assertEquals(productName, detailTitle.getText());
    }

    @Test
    public void testRegisterAndLoginFlow() {
        driver.get(BASE_URL + "login");
        // Đăng ký
        WebElement registerTab = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Register")));
        registerTab.click();
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']")));
        WebElement passInput = driver.findElement(By.cssSelector("input[type='password']"));
        String email = "testuser" + System.currentTimeMillis() + "@mail.com";
        emailInput.sendKeys(email);
        passInput.sendKeys("Test1234!");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        // Đăng xuất nếu có
        try {
            WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Logout")));
            logoutBtn.click();
        } catch (Exception ignored) {}
        // Đăng nhập
        driver.get(BASE_URL + "login");
        emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']")));
        passInput = driver.findElement(By.cssSelector("input[type='password']"));
        emailInput.sendKeys(email);
        passInput.sendKeys("Test1234!");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        // Kiểm tra đăng nhập thành công
        assertTrue(driver.findElement(By.linkText("Logout")).isDisplayed());
    }

    @Test
    public void testCartAndCheckout() {
        driver.get(BASE_URL + "shop");
        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".product-item button")));
        addToCartBtn.click();
        WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".cart-icon")));
        cartIcon.click();
        assertTrue(driver.findElements(By.cssSelector(".cart-item")).size() > 0);
        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".checkout-btn")));
        checkoutBtn.click();
        assertTrue(driver.getCurrentUrl().contains("checkout"));
    }

    @Test
    public void testFormValidationErrors() {
        driver.get(BASE_URL + "login");
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']")));
        WebElement passInput = driver.findElement(By.cssSelector("input[type='password']"));
        // Để trống
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        assertTrue(driver.findElement(By.cssSelector(".error-message")).isDisplayed());
        // Nhập sai email
        emailInput.sendKeys("saiemail");
        passInput.sendKeys("123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        assertTrue(driver.findElement(By.cssSelector(".error-message")).isDisplayed());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
