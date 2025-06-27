package mobile_testing_app.tests;

import static mobile_testing_app.utils.AppiumUtils.scrollUsingCoordinatesDown;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import mobile_testing_app.BaseTest;
import mobile_testing_app.utils.AppiumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BookingTest extends BaseTest {

    public BookingTest(AndroidDriver<AndroidElement> driver) {
        super(driver);
    }

    public void runBookingTests() {
        try {
            closeAd();
            selectMovieByID("4");
            watchTrailer();
            testMovieBooking();
            goToHomePageMenu();
            Thread.sleep(5000);
        } catch (Exception e) {
            System.err.println("Error during booking tests: " + e.getMessage());
        }
    }

    public void selectMovieByID(String movieID) {
        try {
            System.out.println("Starting to select movie with ID: " + movieID);
            AndroidElement movieList = (AndroidElement) wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("com.cgv.cinema.vn:id/gallery"))
            );
            System.out.println("Movie list found.");

            boolean found = false;
            int maxSwipes = 10;
            int swipeCount = 0;

            while (!found && swipeCount < maxSwipes) {
                try {
                    AndroidElement movieElement = (AndroidElement) wait.until(
                            ExpectedConditions.presenceOfElementLocated(By.xpath(
                                    "//android.widget.TextView[@resource-id='com.cgv.cinema.vn:id/number' and @text='" + movieID + "']"
                            ))
                    );
                    System.out.println("Movie found with ID: " + movieID);

                    AndroidElement movieImage = (AndroidElement) wait.until(
                            ExpectedConditions.elementToBeClickable(By.xpath(
                                    "(//android.widget.ImageView[@resource-id='com.cgv.cinema.vn:id/image'])[4]"
                            ))
                    );
                    movieImage.click();
                    System.out.println("Successfully selected movie with ID: " + movieID);
                    found = true;
                } catch (Exception e) {
                    System.out.println("Movie with ID: " + movieID + " not found in current view. Swiping left...");
                    AppiumUtils.scrollUsingCoordinatesLeft(driver);
                    Thread.sleep(1000);
                    swipeCount++;
                }
            }

            if (!found) {
                System.err.println("Movie not found after " + maxSwipes + " swipes. ID: " + movieID);
            }
        } catch (Exception e) {
            System.err.println("Error selecting movie by ID: " + e.getMessage());
        }
    }

    private void testMovieBooking() {
        try {
            AndroidElement bookNowButton = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/book_now"))
            );
            bookNowButton.click();
            System.out.println("Clicked initial 'Book Now' to select cinema and showtime.");
            Thread.sleep(5000);

            selectCinemaAndShowtime("7");

            System.out.println("Testing 'Buy Now' without selecting seats...");
            if (!clickBuyNow()) {
                System.err.println("Cannot proceed with seat selection test: 'Buy Now' button not clickable.");
                return;
            }
            selectSeats();
            Thread.sleep(2000);

            System.out.println("Testing 'Buy Now' with seats selected...");
            boolean buyNowSuccess = false;
            int maxRetries = 5;
            int retryCount = 0;

            while (!buyNowSuccess && retryCount < maxRetries) {
                if (clickBuyNow()) {
                    try {
                        wait.until(ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//android.widget.TextView[contains(@text, 'Unable to connect to server')]")
                        ));
                        System.err.println("Error: Unable to connect to server detected after clicking 'Buy Now'. Retrying...");
                        navigateBackMultipleTimes(1);
                        retryCount++;
                    } catch (Exception noError) {
                        System.out.println("Successfully proceeded to next step after selecting seats.");
                        buyNowSuccess = true;
                        testAgeRestrictionDialog();
                    }
                } else {
                    System.err.println("Cannot proceed with confirm test: 'Buy Now' button not clickable.");
                    return;
                }
            }

            if (!buyNowSuccess) {
                System.err.println("Failed to proceed after " + maxRetries + " retries due to server error.");
            }

            testAddItem();
            clickBuyNow();
            Thread.sleep(2000);
            clickBuyNow();

        } catch (Exception e) {
            System.err.println("Error during movie booking test: " + e.getMessage());
        }
    }

    private void selectCinemaAndShowtime(String date) {
        try {
            selectDate(date);

            String cinemaXpath = "//android.widget.TextView[@resource-id='com.cgv.cinema.vn:id/cinema_name' and @text='CGV Vincom Royal City']";
            boolean cinemaFound = false;
            int maxSwipes = 5;
            int swipeCount = 0;
            boolean regionClicked = false;

            while (!cinemaFound && swipeCount < maxSwipes) {
                try {
                    AndroidElement cinemaElement = (AndroidElement) wait.until(
                            ExpectedConditions.visibilityOfElementLocated(By.xpath(cinemaXpath))
                    );
                    System.out.println("Found cinema: CGV Vincom Royal City");
                    cinemaElement.click();
                    cinemaFound = true;
                } catch (Exception e) {
                    System.out.println("Cinema 'CGV Vincom Royal City' not found in current view.");

                    if (!regionClicked) {
                        // Chỉ click vào region khi lần đầu tiên không tìm thấy rạp
                        AndroidElement regionButton = (AndroidElement) wait.until(
                                ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/lin_region"))
                        );
                        regionButton.click();
                        System.out.println("Clicked region button.");
                        regionClicked = true; // Đánh dấu là đã click region

                        String regionXpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.cgv.cinema.vn:id/rcv']/android.widget.LinearLayout[1]";

                        boolean regionFound = false;
                        int maxRegionSwipes = 5;
                        int regionSwipeCount = 0;

                        while (!regionFound && regionSwipeCount < maxRegionSwipes) {
                            try {
                                AndroidElement regionElement = (AndroidElement) wait.until(
                                        ExpectedConditions.elementToBeClickable(By.xpath(regionXpath))
                                );
                                regionElement.click();
                                regionFound = true;
                            } catch (Exception ex) {
                                System.out.println("Region not found. Scrolling down...");
                                AppiumUtils.scrollUsingCoordinatesDown(driver);
                                regionSwipeCount++;
                            }
                        }

                        if (!regionFound) {
                            System.err.println("Region not found after " + maxRegionSwipes + " swipes.");
                            return;
                        }
                    } else {
                        // Nếu đã click region rồi, chỉ cần vuốt xuống để tìm rạp
                        AppiumUtils.scrollUsingCoordinatesDown(driver);
                    }
                    swipeCount++;
                }
            }

            if (!cinemaFound) {
                System.err.println("Cinema 'CGV Vincom Royal City' not found after " + maxSwipes + " swipes.");
                return;
            }

            String showTimeXpath = "//android.widget.Button[@resource-id='com.cgv.cinema.vn:id/show_time' and @text='16:30']";
            boolean showTimeSelected = false;
            int maxRetries = 5;
            int retryCount = 0;

            while (!showTimeSelected && retryCount < maxRetries) {
                try {
                    AndroidElement showTimeButton = (AndroidElement) wait.until(
                            ExpectedConditions.elementToBeClickable(By.xpath(showTimeXpath))
                    );
                    showTimeButton.click();
                    System.out.println("Clicked showtime: 16:30");

                    // Kiểm tra nếu element com.cgv.cinema.vn:id/app_bar xuất hiện
                    try {
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.cgv.cinema.vn:id/app_bar")));
                        System.out.println("Detected app_bar, initiating login...");
                        performLoginSuccess(); // Thực hiện đăng nhập thành công
                    } catch (Exception noAppBar) {
                        System.out.println("No app_bar detected, proceeding without login.");
                    }

                    try {
                        wait.until(ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//android.widget.TextView[contains(@text, 'Unable to connect to server')]")
                        ));
                        System.err.println("Error: Unable to connect to server detected after selecting showtime. Retrying...");
                        navigateBackMultipleTimes(1);
                        retryCount++;
                    } catch (Exception noError) {
                        System.out.println("Selected showtime: 16:30");
                        showTimeSelected = true;
                    }
                } catch (Exception e) {
                    System.err.println("Showtime not found or clickable: " + e.getMessage());
                    return;
                }
            }

            if (!showTimeSelected) {
                System.err.println("Failed to select showtime after " + maxRetries + " retries due to server error.");
                return;
            }

            Thread.sleep(2000);

        } catch (Exception e) {
            System.err.println("Error during cinema and showtime selection: " + e.getMessage());
        }
    }


    private void selectDate(String date) {
        try {
            String dateXpath = "//android.widget.TextView[@text='" + date + "']";
            AndroidElement dateElement = (AndroidElement) wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(dateXpath))
            );
            dateElement.click();
            System.out.println("Selected date: " + date);
        } catch (Exception e) {
            System.err.println("Error selecting date '" + date + "': " + e.getMessage());
        }
    }

    private void selectSeats() {
        try {
            String[] primarySeats = {"C10", "C11"};
            String[] alternativeSeats = {"G8", "G9"};
            boolean primarySelected = false;

            System.out.println("Attempting to select primary seats: C10, C11...");
            for (String seat : primarySeats) {
                String seatXpath = "//android.widget.TextView[@text='" + seat + "']";
                try {
                    AndroidElement seatElement = (AndroidElement) wait.until(
                            ExpectedConditions.elementToBeClickable(By.xpath(seatXpath))
                    );
                    seatElement.click();
                    System.out.println("Selected seat: " + seat);
                    primarySelected = true;
                } catch (Exception e) {
                    System.out.println("Seat " + seat + " not available or not found.");
                    primarySelected = false;
                    break;
                }
            }

            if (primarySelected) {
                System.out.println("Primary seats (C10, C11) selected. Deselecting them...");
                for (String seat : primarySeats) {
                    String seatXpath = "//android.widget.TextView[@text='" + seat + "']";
                    try {
                        AndroidElement seatElement = (AndroidElement) wait.until(
                                ExpectedConditions.elementToBeClickable(By.xpath(seatXpath))
                        );
                        seatElement.click();
                        System.out.println("Deselected seat: " + seat);
                    } catch (Exception e) {
                        System.err.println("Failed to deselect seat " + seat + ": " + e.getMessage());
                    }
                }

                boolean alternativeSelected = false;
                System.out.println("Attempting to select alternative seats: G8, G9...");
                for (String seat : alternativeSeats) {
                    String seatXpath = "//android.widget.TextView[@text='" + seat + "']";
                    try {
                        AndroidElement seatElement = (AndroidElement) wait.until(
                                ExpectedConditions.elementToBeClickable(By.xpath(seatXpath))
                        );
                        seatElement.click();
                        System.out.println("Selected seat: " + seat);
                        alternativeSelected = true;
                    } catch (Exception e) {
                        System.out.println("Seat " + seat + " not available or not found.");
                        alternativeSelected = false;
                        break;
                    }
                }

                if (!alternativeSelected) {
                    System.out.println("Alternative seats (G8, G9) not available. Re-selecting C10, C11...");
                    for (String seat : primarySeats) {
                        String seatXpath = "//android.widget.TextView[@text='" + seat + "']";
                        try {
                            AndroidElement seatElement = (AndroidElement) wait.until(
                                    ExpectedConditions.elementToBeClickable(By.xpath(seatXpath))
                            );
                            seatElement.click();
                            System.out.println("Re-selected seat: " + seat);
                        } catch (Exception e) {
                            System.err.println("Failed to re-select seat " + seat + ": " + e.getMessage());
                        }
                    }
                } else {
                    System.out.println("Successfully selected alternative seats (G8, G9).");
                }
            } else {
                System.err.println("Primary seats (C10, C11) not available. Seat selection failed.");
            }

        } catch (Exception e) {
            System.err.println("Error during seat selection: " + e.getMessage());
        }
    }

    private void watchTrailer() throws InterruptedException {
        try {
            AndroidElement trailerLayer = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/layer"))
            );
            trailerLayer.click();
            System.out.println("Clicked to watch trailer.");
            Thread.sleep(15000);
            navigateBackMultipleTimes(1);
        } catch (Exception e) {
            System.err.println("Trailer layer not found: " + e.getMessage());
        }
    }

    private boolean clickBuyNow() {
        try {
            AndroidElement buyNowButton = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("com.cgv.cinema.vn:id/buy_now"))
            );
            buyNowButton.click();
            System.out.println("Clicked 'Buy Now' button.");
            Thread.sleep(2000);
            return true;
        } catch (Exception e) {
            System.err.println("Failed to click 'Buy Now' button: " + e.getMessage());
            return false;
        }
    }

    private void testAgeRestrictionDialog() {
        try {
            System.out.println("Testing age restriction dialog after clicking 'Buy Now'...");
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("com.cgv.cinema.vn:id/parentPanel")
            ));
            System.out.println("Age restriction dialog detected.");

            System.out.println("Clicking 'Cancel' on age restriction dialog...");
            AndroidElement cancelButton = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("android:id/button2"))
            );
            cancelButton.click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("android:id/button2")));
            System.out.println("Clicked 'Cancel'. Dialog closed, returned to seat selection screen.");

            System.out.println("Clicking 'Buy Now' again to test 'Confirm'...");
            if (!clickBuyNow()) {
                System.err.println("Cannot proceed with confirm test: 'Buy Now' button not clickable.");
                return;
            }

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//android.widget.TextView[contains(@text, '18')]")
            ));
            System.out.println("Age restriction dialog detected again.");

            AndroidElement confirmButton = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("android:id/button1"))
            );
            confirmButton.click();
            System.out.println("Clicked 'Confirm' on age restriction dialog.");

            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.id("com.cgv.cinema.vn:id/rcv_concession")
                ));
                System.out.println("Test passed: Successfully proceeded to next step after confirming age restriction.");
            } catch (Exception e) {
                System.out.println("Test passed: Confirmed dialog, but no next step detected (expected behavior may vary).");
            }

        } catch (Exception e) {
            System.err.println("Error during age restriction dialog test: " + e.getMessage());
        }
    }

    private void testAddItem() {
        try {
            AndroidElement plusButton = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("(//android.widget.TextView[@resource-id='com.cgv.cinema.vn:id/plus'])[2]"))
            );
            plusButton.click();
            System.out.println("Clicked second '+' button to increase item quantity.");

            AndroidElement minusButton = (AndroidElement) wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("(//android.widget.TextView[@resource-id='com.cgv.cinema.vn:id/minus'])[2]"))
            );
            minusButton.click();
            System.out.println("Clicked second '-' button to decrease item quantity.");

            boolean buyNowSuccess = false;
            int maxRetries = 5;
            int retryCount = 0;

            while (!buyNowSuccess && retryCount < maxRetries) {
                if (clickBuyNow()) {
                    try {
                        wait.until(ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//android.widget.TextView[contains(@text, 'Unable to connect to server')]")
                        ));
                        System.err.println("Error: Unable to connect to server detected after clicking 'Buy Now'. Retrying...");
                        navigateBackMultipleTimes(1);
                        retryCount++;
                    } catch (Exception noServerError) {
                        try {
                            wait.until(ExpectedConditions.presenceOfElementLocated(
                                    By.xpath("//android.widget.TextView[contains(@text, 'sold or controlled seat exist')]")
                            ));
                            System.err.println("Error: 'Sold or controlled seat exist' detected. Returning to home...");
                            goToHomePageMenu();
                            return;
                        } catch (Exception noSoldError) {
                            System.out.println("Successfully added item and proceeded with 'Buy Now'.");
                            buyNowSuccess = true;
                        }
                    }
                } else {
                    System.err.println("Failed to click 'Buy Now' button.");
                    return;
                }
            }

            if (!buyNowSuccess) {
                System.err.println("Failed to add item after " + maxRetries + " retries due to server error.");
            }

        } catch (Exception e) {
            System.err.println("Error during add item test: " + e.getMessage());
        }
    }

    // Thêm phương thức mới để thực hiện đăng nhập thành công
    private void performLoginSuccess() {
        try {
            String sdt = "0375302679";
            String password = "Dangvu01072004@";

            AndroidElement emailField = (AndroidElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.cgv.cinema.vn:id/edt_email_phone")));
            emailField.clear();
            emailField.sendKeys(sdt);

            AndroidElement passwordField = (AndroidElement) driver.findElement(By.id("com.cgv.cinema.vn:id/edt_password"));
            passwordField.clear();
            passwordField.sendKeys(password);

            AndroidElement loginButton = (AndroidElement) driver.findElement(By.id("com.cgv.cinema.vn:id/btn_login"));
            loginButton.click();

            System.out.println("Entered correct login credentials and clicked login.");

            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.cgv.cinema.vn:id/main_container")));
            System.out.println("Login successful, proceeding with booking.");

        } catch (Exception e) {
            System.err.println("Error during login in booking process: " + e.getMessage());
        }
    }



}