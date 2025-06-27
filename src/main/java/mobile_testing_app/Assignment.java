package mobile_testing_app;

import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import mobile_testing_app.tests.BookingTest;
import mobile_testing_app.tests.GiftTest;
import mobile_testing_app.tests.LoginTest;
import mobile_testing_app.tests.LogoutTest;
import mobile_testing_app.tests.MovieListTest;
import mobile_testing_app.tests.ProfileTest;
import mobile_testing_app.tests.SearchTest;
import mobile_testing_app.tests.TicketTest;
import mobile_testing_app.tests.VideoTest;

public class Assignment {
	private final AndroidDriver<AndroidElement> driver;
	private final WebDriverWait wait;

	public Assignment(AndroidDriver<AndroidElement> driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 10); // Timeout 10s
	}

	public void runTest() {
		try {
			// Run test Register
//			RegisterTest registerTest = new RegisterTest(driver);
//			registerTest.runRegisterTests();
//			System.out.println("Register tests completed.");

//			 Run test Login
			LoginTest loginTest = new LoginTest(driver);
			loginTest.runLoginTests();
			System.out.println("Login tests completed.");

//			 Run test Booking after login
			BookingTest bookingTestAfterLogin = new BookingTest(driver);
			bookingTestAfterLogin.runBookingTests();
			System.out.println("Booking tests completed.");

			//Run test MovieList
			MovieListTest movieListTest = new MovieListTest(driver);
			movieListTest.runMovieListTests();
			System.out.println("MovieList tests completed.");

//			 Run test Profile
			ProfileTest profileTest = new ProfileTest(driver);
			profileTest.runProfileTests();
			System.out.println("Profile tests completed.");

			// Run test Search
			SearchTest searchTest = new SearchTest(driver);
			searchTest.runSearchTests();
			System.out.println("Search tests completed.");

			// Run test Ticket
			TicketTest ticketTest = new TicketTest(driver);
			ticketTest.runTicketTests();
			System.out.println("Ticket tests completed.");

			// Run test MovieHub
			VideoTest videoTest = new VideoTest(driver);
			videoTest.runMovieHubTests();
			System.out.println("MovieHub tests completed.");

			GiftTest giftTest = new GiftTest(driver);
			giftTest.runGiftTests();
			System.out.println("Gift tests completed.");

			// Run test logout
			LogoutTest logoutTest = new LogoutTest(driver);
			logoutTest.runLogoutTests();
			System.out.println("Logout tests completed.");

			//Run test Booking before login
			BookingTest bookingTestBeforeLogin = new BookingTest(driver);
			bookingTestBeforeLogin.runBookingTests();
			System.out.println("Booking tests uncompleted.");
		} catch (Exception e) {
			System.err.println("Error during test execution: " + e.getMessage());
		}
	}

}






