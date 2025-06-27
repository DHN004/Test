package mobile_testing_app.tests;

import static mobile_testing_app.utils.AppiumUtils.scrollUsingCoordinatesDown;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import mobile_testing_app.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class GiftTest extends BaseTest {

    public GiftTest(AndroidDriver<AndroidElement> driver) {
        super(driver);
    }

    public void runGiftTests() {
        try {
            closeAd();
            goToHomePageMenu();
            closeAd();
            testGiftSection();
        } catch (Exception e) {
            System.err.println("Error during Gift tests: " + e.getMessage());
        }
    }

    private void testGiftSection() {
        String giftSectionXpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.cgv.cinema.vn:id/rcv_home']/android.view.ViewGroup[2]";
        boolean sectionFound = false;
        int maxDownSwipes = 5;
        int swipeDownCount = 0;

        while (!sectionFound && swipeDownCount < maxDownSwipes) {
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(giftSectionXpath)));
                sectionFound = true;
                System.out.println("Found Gift section.");
            } catch (Exception e) {
                System.out.println("Gift section not found. Scrolling down...");
                scrollUsingCoordinatesDown(driver);
                swipeDownCount++;
            }
        }

        if (!sectionFound) {
            System.err.println("Gift section not found after " + maxDownSwipes + " swipes.");
            return;
        }

        // 2. Tìm và nhấn item, xử lý lỗi "Unable to connect to server"
        String targetItemXpath = "(//androidx.recyclerview.widget.RecyclerView[@resource-id='com.cgv.cinema.vn:id/rcv'])[2]/android.view.ViewGroup[1]";
        boolean itemProcessed = false;
        int maxRetries = 5;
        int retryCount = 0;

        while (!itemProcessed && retryCount < maxRetries) {
            try {
                AndroidElement targetItem = (AndroidElement) wait.until(
                        ExpectedConditions.elementToBeClickable(By.xpath(targetItemXpath))
                );
                targetItem.click();
                System.out.println("Clicked target Gift item.");

                // Kiểm tra lỗi "Unable to connect to server"
                try {
                    wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//android.widget.TextView[contains(@text, 'Unable to connect to server')]")
                    ));
                    System.err.println("Error: Unable to connect to server detected in Gift item. Retrying...");
                    navigateBack();
                    retryCount++;
                    continue;
                } catch (Exception noError) {
                    Thread.sleep(5000);
                    navigateBack();
                    System.out.println("Viewed Gift item for 5s and exited.");
                    itemProcessed = true;
                }
            } catch (Exception e) {
                System.err.println("Error finding or clicking Gift item: " + e.getMessage());
                return;
            }
        }

        if (!itemProcessed) {
            System.err.println("Failed to process Gift item after " + maxRetries + " retries due to server error.");
            return;
        }

        // 3. Nhấn "View All" button
        try {
            AndroidElement viewAllButton = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("(//android.widget.Button[@resource-id='com.cgv.cinema.vn:id/btn_all'])[2]"))
            );
            viewAllButton.click();
            System.out.println("Clicked 'View All' button in Gift section.");
        } catch (Exception e) {
            System.err.println("Error clicking 'View All' button: " + e.getMessage());
        }

        // 4 & 5. Test collection và xử lý collection item
        testCollection();
    }

    private void testCollection() {
        try {
            // Kiểm tra và nhấn nút Collection
            AndroidElement collectionButton = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/btn_collection"))
            );
            collectionButton.click();
            System.out.println("Clicked 'Collection' button.");

            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.cgv.cinema.vn:id/empty_text")));
                System.out.println("No collection found (empty text displayed).");
            } catch (Exception e) {
                System.out.println("Collection items are available.");
            }

            navigateBack(); // Quay lại để tìm collection item
            System.out.println("Navigated back from collection check.");

            // Tìm và xử lý collection item
            String collectionItemXpath = "(//androidx.recyclerview.widget.RecyclerView[@resource-id='com.cgv.cinema.vn:id/rcv'])[4]/android.view.ViewGroup[1]";
            boolean collectionItemProcessed = false;
            int maxDownSwipesCollection = 5;
            int swipeDownCountCollection = 0;
            int maxRetriesCollection = 5;
            int retryCountCollection = 0;

            while (!collectionItemProcessed && swipeDownCountCollection < maxDownSwipesCollection) {
                try {
                    AndroidElement collectionItem = (AndroidElement) wait.until(
                            ExpectedConditions.elementToBeClickable(By.xpath(collectionItemXpath))
                    );
                    collectionItem.click();
                    System.out.println("Clicked collection item.");

                    // Xử lý lỗi "Unable to connect to server"
                    while (!collectionItemProcessed && retryCountCollection < maxRetriesCollection) {
                        try {
                            wait.until(ExpectedConditions.presenceOfElementLocated(
                                    By.xpath("//android.widget.TextView[contains(@text, 'Unable to connect to server')]")
                            ));
                            System.err.println("Error: Unable to connect to server detected in collection item. Retrying...");
                            navigateBack();
                            collectionItem.click();
                            retryCountCollection++;
                        } catch (Exception noError) {
                            Thread.sleep(3000);
                            navigateBack();
                            Thread.sleep(2000);
                            navigateBack();
                            System.out.println("Viewed collection item for 3s and exited with 2 back presses.");
                            collectionItemProcessed = true;
                        }
                    }

                    if (!collectionItemProcessed) {
                        System.err.println("Failed to process collection item after " + maxRetriesCollection + " retries due to server error.");
                        return;
                    }

                } catch (Exception e) {
                    System.out.println("Collection item not found. Scrolling down...");
                    scrollUsingCoordinatesDown(driver);
                    swipeDownCountCollection++;
                }
            }

            if (!collectionItemProcessed) {
                System.err.println("Collection item not found after " + maxDownSwipesCollection + " swipes.");
            }

        } catch (Exception e) {
            System.err.println("Error during collection test: " + e.getMessage());
        }
    }


}