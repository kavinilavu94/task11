package task11;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.Set;

public class WindowHandling{
    public static void main(String[] args) {
        // Set the path to the ChromeDriver executable if not set in system PATH
        // System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");

        // Initialize ChromeOptions if needed (e.g., to run headless)
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // Uncomment to run in headless mode

        // Initialize the WebDriver
        WebDriver driver = new ChromeDriver(options);

        try {
            // Navigate to the specified URL
            driver.get("https://the-internet.herokuapp.com/windows");

            // Store the handle of the original window
            String originalWindow = driver.getWindowHandle();

            // Click the "Click Here" link to open a new window
            WebElement clickHereLink = driver.findElement(By.linkText("Click Here"));
            clickHereLink.click();

            // Wait for the new window to open
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(dri -> dri.getWindowHandles().size() > 1);

            // Get all window handles
            Set<String> windowHandles = driver.getWindowHandles();

            // Switch to the new window
            for (String handle : windowHandles) {
                if (!handle.equals(originalWindow)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            // Wait for the new window's content to load
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h3")));

            // Verify that the text "New Window" is present on the page
            WebElement heading = driver.findElement(By.tagName("h3"));
            String headingText = heading.getText();
            if ("New Window".equals(headingText)) {
                System.out.println("Text verification passed: 'New Window' is present.");
            } else {
                System.out.println("Text verification failed: Expected 'New Window' but found '" + headingText + "'");
            }

            // Close the new window
            driver.close();

            // Switch back to the original window
            driver.switchTo().window(originalWindow);

            // Verify that the original window is active
            String currentURL = driver.getCurrentUrl();
            if ("https://the-internet.herokuapp.com/windows".equals(currentURL)) {
                System.out.println("Successfully returned to the original window.");
            } else {
                System.out.println("Failed to return to the original window. Current URL: " + currentURL);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser instance
            driver.quit();
        }
    }
}
