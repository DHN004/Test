package mobile_testing_app.tests;

import static mobile_testing_app.utils.AppiumUtils.scrollUsingCoordinatesDown;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import mobile_testing_app.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class ProfileTest extends BaseTest {

    public ProfileTest(AndroidDriver<AndroidElement> driver) {
        super(driver);
    }

    public void runProfileTests() {
        try {
            goToHomePageMenu();
            navigateToProfile();
            gotoAccountInfo("Vudang01072004@");
            updateProfileInfo("Dang Vu");

            // Test đổi mật khẩu sai
            gotoChangePass("WrongPassword", "Vudang01072004");
            System.out.println("Tested incorrect password change.");

            navigateBackToProfile();

            // Test đổi mật khẩu đúng
            gotoChangePass("Vudang01072004@", "Dangvu01072004@");
            System.out.println("Tested correct password change.");

        } catch (Exception e) {
            System.err.println("Error during profile tests: " + e.getMessage());
        }
    }

    private void navigateToProfile() {
        AndroidElement profileButton = (AndroidElement) wait.until(
                ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/btn_top_bar_left"))
        );
        profileButton.click();
        System.out.println("Navigated to profile management page.");
    }

    private void gotoAccountInfo(String password) {
        AndroidElement accountInfoButton = (AndroidElement) wait.until(
                ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/lin_account_information"))
        );
        accountInfoButton.click();
        System.out.println("Clicked account information button.");

        AndroidElement passwordField = (AndroidElement) wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("com.cgv.cinema.vn:id/edt_old_password"))
        );
        passwordField.sendKeys(password);

        AndroidElement confirmButton = (AndroidElement) wait.until(
                ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/confirm"))
        );
        confirmButton.click();
        System.out.println("Entered password and confirmed to access account info.");
    }

    private void updateProfileInfo(String name) {
        try {
            // Cập nhật tên
            AndroidElement nameField = (AndroidElement) wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("com.cgv.cinema.vn:id/edt_name"))
            );
            nameField.clear();
            nameField.sendKeys(name);
            System.out.println("Updated name field with: " + name);

            // Nhấn vào trường địa chỉ để mở danh sách
            AndroidElement districtField = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/auto_district"))
            );
            districtField.click();
            System.out.println("Clicked district field to open list.");

            // Đợi ListView xuất hiện
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.cgv.cinema.vn:id/select_dialog_listview")));
            System.out.println("ListView 'select_dialog_listview' is present.");

            // Cuộn đến "Other" và chọn
            String districtText = "Other";
            scrollToDistrict(districtText);

            // Nhấn vào "Other"
            String districtXpath = "//android.widget.CheckedTextView[@resource-id='com.cgv.cinema.vn:id/text1' and @text='" + districtText + "']";
            AndroidElement districtOption = (AndroidElement) wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(districtXpath))
            );
            districtOption.click();
            System.out.println("Selected district: " + districtText);

            // Nhấn nút lưu
            AndroidElement saveButton = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/btn_sign_up"))
            );
            saveButton.click();
            System.out.println("Updated profile info: Name=" + name + ", District=" + districtText);

        } catch (Exception e) {
            System.err.println("Error updating profile info: " + e.getMessage());
            System.out.println("Current page source: " + driver.getPageSource());
        }
    }

    private void gotoChangePass(String oldPassword, String newPassword) {
        try {
            AndroidElement changePasswordBtn = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/lin_change_password"))
            );
            changePasswordBtn.click();
            System.out.println("Clicked change password button.");

            AndroidElement oldPassField = (AndroidElement) wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("com.cgv.cinema.vn:id/edt_old_password"))
            );
            oldPassField.sendKeys(oldPassword);

            AndroidElement newPassField = (AndroidElement) wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("com.cgv.cinema.vn:id/edt_new_password"))
            );
            newPassField.sendKeys(newPassword);

            AndroidElement confirmPassField = (AndroidElement) wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("com.cgv.cinema.vn:id/edt_verify_new_password"))
            );
            confirmPassField.sendKeys(newPassword);

            AndroidElement saveButton = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/confirm"))
            );
            saveButton.click();
            System.out.println("Attempted to update password from '" + oldPassword + "' to '" + newPassword + "'");

        } catch (Exception e) {
            System.err.println("Error during password change: " + e.getMessage());
            System.out.println("Current page source: " + driver.getPageSource());
        }
    }

    private void scrollToDistrict(String districtText) {
        try {
            driver.findElement(MobileBy.AndroidUIAutomator(
                    "new UiScrollable(new UiSelector().resourceId(\"com.cgv.cinema.vn:id/select_dialog_listview\")).scrollIntoView(" +
                            "new UiSelector().text(\"" + districtText + "\"))"));
            System.out.println("Scrolled to district: " + districtText);
        } catch (Exception e) {
            System.err.println("Error scrolling to district '" + districtText + "': " + e.getMessage());
        }
    }

    private void navigateBackToProfile() {
        try {
            AndroidElement backButton = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/btn_top_bar_left"))
            );
            backButton.click();
            System.out.println("Navigated back from change password screen.");

        } catch (Exception e) {
            System.err.println("Error navigating back to profile: " + e.getMessage());
        }
    }


}