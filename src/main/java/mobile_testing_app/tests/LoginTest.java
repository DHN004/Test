package mobile_testing_app.tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import mobile_testing_app.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginTest extends BaseTest{

    public  LoginTest(AndroidDriver<AndroidElement> driver) {
        super(driver);
    }

    public void runLoginTests() {
        try {
            closeAd();
            openMenu();
            navigateToLogin1();
            navigateToLogin2();
            testLoginWrongFormat("tester", "password123");
            testLoginFailed("testuser@example.com", "password123");
            testLoginSuccess("0913417215", "Phuongdok16cntt2!");
        } catch (Exception e) {
            System.err.println("Error during login tests: " + e.getMessage());
        }
    }

    private void navigateToLogin1() {
        try {
            AndroidElement userName = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/user_name")));
            userName.click();
            System.out.println("Navigated to login screen.");
        } catch (Exception e) {
            System.err.println("Failed to navigate to login screen: " + e.getMessage());
        }
    }

    private void navigateToLogin2() {
        try {
            AndroidElement userName = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/btn_login")));
            userName.click();
            System.out.println("Navigated to login screen.");
        } catch (Exception e) {
            System.err.println("Failed to navigate to login screen: " + e.getMessage());
        }
    }

    private void testLoginWrongFormat(String username, String password) {
        try {
            AndroidElement emailField = (AndroidElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.cgv.cinema.vn:id/edt_email_phone")));
            emailField.sendKeys(username);

            AndroidElement passwordField = driver.findElement(By.id("com.cgv.cinema.vn:id/edt_password"));
            passwordField.sendKeys(password);

            AndroidElement loginButton = driver.findElement(By.id("com.cgv.cinema.vn:id/btn_login"));
            loginButton.click();
            System.out.println("Entered login credentials and clicked login.");

        } catch (Exception e) {
            System.err.println("Login failed: " + e.getMessage());
        }
    }

    private void testLoginFailed(String sdt, String password) {
        try {
            AndroidElement emailField = (AndroidElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.cgv.cinema.vn:id/edt_email_phone")));
            emailField.sendKeys(sdt);

            AndroidElement passwordField = driver.findElement(By.id("com.cgv.cinema.vn:id/edt_password"));
            passwordField.sendKeys(password);

            AndroidElement loginButton = driver.findElement(By.id("com.cgv.cinema.vn:id/btn_login"));
            loginButton.click();

            System.out.println("Entered login credentials and clicked login.");
        } catch (Exception e) {
            System.err.println("Login failed: " + e.getMessage());
        }
    }

    private void testLoginSuccess(String sdt, String password) {
        int retryCount = 0;
        boolean loginSuccessful = false;

        while (retryCount < 20 && !loginSuccessful) {
            try {

                AndroidElement emailField = (AndroidElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.cgv.cinema.vn:id/edt_email_phone")));
                emailField.clear();
                emailField.sendKeys(sdt);


                AndroidElement passwordField = driver.findElement(By.id("com.cgv.cinema.vn:id/edt_password"));
                passwordField.clear();
                passwordField.sendKeys(password);


                AndroidElement loginButton = driver.findElement(By.id("com.cgv.cinema.vn:id/btn_login"));
                loginButton.click();
                System.out.println("Entered login credentials and clicked login (Attempt " + (retryCount + 1) + ")");


                Thread.sleep(3000);
                String pageSource = driver.getPageSource();

                if (pageSource.contains("Unable to connect to server")) {
                    System.err.println("Lỗi: Không thể kết nối tới máy chủ. Thử lại...");
                } else if (!driver.findElements(By.id("com.cgv.cinema.vn:id/main_container")).isEmpty()) {
                    System.out.println("Login successful!");
                    loginSuccessful = true;
                } else {
                    System.err.println("Login failed, retrying...");
                }

            } catch (Exception e) {
                System.err.println("Login attempt " + (retryCount + 1) + " failed: " + e.getMessage());
            }

            retryCount++;
            if (!loginSuccessful) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!loginSuccessful) {
            System.err.println("Failed to login after " + retryCount + " attempts.");
        }
    }
}
