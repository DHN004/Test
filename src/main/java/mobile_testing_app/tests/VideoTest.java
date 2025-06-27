package mobile_testing_app.tests;

import static mobile_testing_app.utils.AppiumUtils.scrollUsingCoordinatesDown;
import static mobile_testing_app.utils.AppiumUtils.scrollUsingCoordinatesLeftCustom;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import mobile_testing_app.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VideoTest extends BaseTest {

    public VideoTest(AndroidDriver<AndroidElement> driver) {
        super(driver);
    }

    public void runMovieHubTests() {
        try {
            closeAd();
            goToHomePageMenu();
            closeAd();
            testVideo();
            Thread.sleep(7000);
            testMovieHubDetails();
            navigateBack();
        } catch (Exception e) {
            System.err.println("Error during MovieHub tests: " + e.getMessage());
        }
    }

    private void testVideo() {
        String videoXpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.cgv.cinema.vn:id/rcv_home']/android.view.ViewGroup[3]";
        boolean sectionFound = false;
        int maxDownSwipes = 5;
        int swipeDownCount = 0;

        while (!sectionFound && swipeDownCount < maxDownSwipes) {
            try {
                AndroidElement movieHubSection = (AndroidElement) wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(videoXpath)));
                if (movieHubSection.isDisplayed()) {
                    sectionFound = true;
                    System.out.println("Found MovieHub section.");
                }
            } catch (Exception e) {
                System.out.println("MovieHub section not found. Scrolling down...");
                scrollUsingCoordinatesDown(driver);
                swipeDownCount++;
            }
        }

        if (!sectionFound) {
            System.err.println("MovieHub section not found after " + maxDownSwipes + " swipes.");
            return;
        }

        String targetItemXpath = "(//androidx.recyclerview.widget.RecyclerView[@resource-id='com.cgv.cinema.vn:id/rcv'])[3]/android.view.ViewGroup[2]";
        boolean itemFound = false;
        int maxLeftSwipes = 5;
        int swipeLeftCount = 0;

        while (!itemFound && swipeLeftCount < maxLeftSwipes) {
            try {
                AndroidElement targetItem = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(By.xpath(targetItemXpath)));
                if (targetItem.isDisplayed()) {
                    targetItem.click();
                    itemFound = true;
                    System.out.println("Found and clicked target MovieHub item.");
                }
            } catch (Exception e) {
                System.out.println("Target item not found. Swiping left...");
                scrollUsingCoordinatesLeftCustom(driver);
                swipeLeftCount++;
            }
        }

        if (!itemFound) {
            System.err.println("Target MovieHub item not found after " + maxLeftSwipes + " swipes.");
        }
    }

    private void testMovieHubDetails() {
        try {
            AndroidElement movieHubItem = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//android.widget.LinearLayout[@content-desc='#MidnightStreetFood']")
                    )
            );

            movieHubItem.click();
            Thread.sleep(5000);

            AndroidElement movieHubItem1 = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//android.widget.LinearLayout[@content-desc='#Newme']")
                    )
            );
            movieHubItem1.click();
            Thread.sleep(5000);

            AndroidElement movieHubItem2 = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//android.widget.LinearLayout[@content-desc='#TheInternShip']")
                    )
            );
            movieHubItem2.click();
            Thread.sleep(5000);





        } catch (Exception e) {
            System.err.println("Error during MovieHub details test: " + e.getMessage());
        }
    }
}