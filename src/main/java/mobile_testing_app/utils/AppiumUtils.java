package mobile_testing_app.utils;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Collections;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class AppiumUtils  {

    public static void scrollUsingCoordinatesLeft(AndroidDriver<AndroidElement> driver) {
        try {
            int startX = 1154;
            int startY = 1680;
            int endX = 190;
            int endY = 1680;

            // Tạo đối tượng PointerInput
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

            Sequence swipe = new Sequence(finger, 0)
                    .addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY)) // Di chuyển đến điểm bắt đầu
                    .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg())) // Nhấn xuống
                    .addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), endX, endY)) // Di chuyển đến điểm kết thúc
                    .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg())); // Nhả tay

            // Thực hiện thao tác
            ((AndroidDriver<?>) driver).perform(Collections.singletonList(swipe));

            System.out.println("Scrolled from left to right using coordinates.");
        } catch (Exception e) {
            System.err.println("Error while scrolling: " + e.getMessage());
        }
    }

    public static void scrollUsingCoordinatesDown(AndroidDriver<AndroidElement> driver) {
        try {
            Dimension size = driver.manage().window().getSize();
            int startX = size.width / 2;
            int startY = (int) (size.height * 0.7);
            int endX = size.width / 2;
            int endY = (int) (size.height * 0.3);

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

            Sequence swipe = new Sequence(finger, 0)
                    .addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY)) // Di chuyển đến điểm bắt đầu
                    .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg())) // Nhấn xuống
                    .addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), endX, endY)) // Di chuyển đến điểm kết thúc
                    .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg())); // Nhả tay

            // Thực hiện thao tác
            ((AndroidDriver<?>) driver).perform(Collections.singletonList(swipe));

            System.out.println("Scrolled down using coordinates.");
        } catch (Exception e) {
            System.err.println("Error while scrolling: " + e.getMessage());
        }
    }

        public static void scrollUsingCoordinatesLeftCustom(AndroidDriver<AndroidElement> driver) {
            try {
                int startX = 1344;
                int startY = 1679;
                int endX = 0;
                int endY = 2169;

                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                Sequence swipe = new Sequence(finger, 0)
                        .addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY))
                        .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                        .addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), endX, endY))
                        .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
                ((AndroidDriver<?>) driver).perform(Collections.singletonList(swipe));
                System.out.println("Scrolled left with custom coordinates [1344,1679] to [0,2169].");
            } catch (Exception e) {
                System.err.println("Error while swiping left with custom coordinates: " + e.getMessage());
            }
        }
    }

