package mobile_testing_app.tests;

import static mobile_testing_app.utils.AppiumUtils.scrollUsingCoordinatesDown;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import mobile_testing_app.BaseTest;

public class SearchTest extends BaseTest {

    public SearchTest(AndroidDriver<AndroidElement> driver) {
        super(driver);
    }

    public void runSearchTests() {
        try{
            closeAd();
            goToHomePageMenu();
            closeAd();
            openMenu();
            testSearch1();
            Thread.sleep(5000);
            openMenu();
            testSearch2();
            Thread.sleep(5000);
            goToHomePageMenu();
        } catch (Exception e) {
            System.err.println("Error during Search tests: " + e.getMessage());
        }
    }

    private void testSearch2() {
        try{
            AndroidElement searchMovieButton = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/booking_by_theater"))
            );
            searchMovieButton.click();

            for (int i = 0; i < 4; i++) {
                scrollUsingCoordinatesDown(driver);
            }



            AndroidElement location = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.ExpandableListView[@resource-id='com.cgv.cinema.vn:id/exp_lv']/android.widget.LinearLayout[3]"))
            );
            location.click();


            AndroidElement theater = (AndroidElement) wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath("(//android.widget.LinearLayout[@resource-id='com.cgv.cinema.vn:id/container'])[2]"))
            );
            theater.click();
            Thread.sleep(3000);


        } catch (Exception e) {
            System.err.println("Search button not found: " + e.getMessage());
        }
    }

    private void testSearch1() {
        try{
            AndroidElement searchMovieButton = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/booking_by_movies"))
            );
            searchMovieButton.click();

            for (int i = 0; i < 3; i++) {
                scrollUsingCoordinatesDown(driver);
            }

            AndroidElement showingBtn = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/lin_movie_type"))
            );
            showingBtn.click();
            Thread.sleep(3000);

            AndroidElement movie = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("(//android.widget.ImageView[@resource-id='com.cgv.cinema.vn:id/movie_image'])[4]"))
            );
            movie.click();


        } catch (Exception e) {
            System.err.println("Search button not found: " + e.getMessage());
        }
    }
}
