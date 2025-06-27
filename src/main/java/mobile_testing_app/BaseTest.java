package mobile_testing_app;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseTest {
    protected AndroidDriver<AndroidElement> driver;
    protected WebDriverWait wait;

    public BaseTest(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10); // Timeout 10s
    }

    // Các hàm tiện ích chung có thể thêm ở đây
    protected void closeAd() {
        try {
            AndroidElement closeAd = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/close"))
            );
            closeAd.click();
            System.out.println("Ad closed.");
        } catch (Exception e) {
            System.out.println("No ad appeared.");
        }
    }

    protected void openMenu() {
        try {
            AndroidElement menuButton = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/btn_right_menu"))
            );
            menuButton.click();
            System.out.println("Menu opened.");
        } catch (Exception e) {
            System.err.println("Failed to open menu: " + e.getMessage());
        }
    }

    protected void goToHomePage(int times) {
        System.err.println("Returning to home page due to missing cinema/showtime.");
        navigateBackMultipleTimes(times);

        if (isOnHomePage()) {
            System.out.println("Quay lại trang chủ thành công.");
        } else {
            System.err.println("Chưa về được trang chủ.");
        }
    }

    protected void navigateBack() {
        try {
            AndroidElement backButton = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/btn_top_bar_left"))
            );
            backButton.click();
            System.out.println("Returned to movie page.");
        } catch (Exception e) {
            System.err.println("Back button not found: " + e.getMessage());
        }
    }

    protected void navigateBackMultipleTimes(int times) {
        for (int i = 0; i < times; i++) {
            try {
                navigateBack();
                Thread.sleep(1000);
            } catch (Exception e) {
                System.err.println("Back button not found, stopping.");
                break;
            }
        }
    }

    protected boolean isOnHomePage() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.cgv.cinema.vn:id/home_top_bar")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected void goToHomePageMenu() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.cgv.cinema.vn:id/home_top_bar")));
            System.out.println("Already on home page.");
        } catch (Exception e) {
            System.out.println("Not on home page, navigating to home...");
            openMenu();
            AndroidElement homeButton = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/menu_home"))
            );
            homeButton.click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.cgv.cinema.vn:id/home_top_bar")));
            System.out.println("Navigated to home page.");
        }
    }
}