package mobile_testing_app.tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import mobile_testing_app.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MovieListTest extends BaseTest {

    public MovieListTest(AndroidDriver<AndroidElement> driver) {
        super(driver);
    }

    public void runMovieListTests() {
        try {
            closeAd();
            testSpecialMovies();
            Thread.sleep(5000);
            testComingSoonMovies();
        } catch (Exception e) {
            System.err.println("Error during movie list tests: " + e.getMessage());
        }
    }

    private void testSpecialMovies() {
        try {
            System.out.println("Testing navigation to Special Movies...");
            AndroidElement specialButton = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/txt_special"))
            );
            specialButton.click();
            System.out.println("Clicked 'Special' button.");

            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.id("com.cgv.cinema.vn:id/gallery")
            ));
            System.out.println("Special Movies list loaded successfully.");



        } catch (Exception e) {
            System.err.println("Error during Special Movies test: " + e.getMessage());
        }
    }

    private void testComingSoonMovies() {
        try {
            System.out.println("Testing navigation to Coming Soon Movies...");
            AndroidElement comingSoonButton = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/txt_coming_soon"))
            );
            comingSoonButton.click();
            System.out.println("Clicked 'Coming Soon' button.");

            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.id("com.cgv.cinema.vn:id/gallery")
            ));
            System.out.println("Coming Soon Movies list loaded successfully.");


        } catch (Exception e) {
            System.err.println("Error during Coming Soon Movies test: " + e.getMessage());
        }
    }


}