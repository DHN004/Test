package mobile_testing_app;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import java.net.MalformedURLException;

public class Main {
	public static void main(String[] args) {
		AndroidDriver<AndroidElement> driver = null;
		try {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13");
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel 8 Pro API 33");

			capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
			capabilities.setCapability("appPackage", "com.cgv.cinema.vn");
			capabilities.setCapability("appActivity", "com.cgv.cinema.vn.ui.MainActivity");
			capabilities.setCapability("automationName", "UiAutomator2");

			capabilities.setCapability("intentCategory", "android.intent.category.LAUNCHER");
			capabilities.setCapability("autoGrantPermissions", true);
			capabilities.setCapability("noReset", true);


			driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723"), capabilities);

			// Run test from Assignment.java
			Assignment assignment = new Assignment(driver);
			assignment.runTest();

		} catch (MalformedURLException e) {
			System.err.println("Appium initialization error: " + e.getMessage());
		} finally {
			if (driver != null) {
				driver.quit();
			}
		}
	}
}
