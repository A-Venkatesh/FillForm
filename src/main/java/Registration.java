import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Registration {
    public static void main(String[] args) {

        // Set the system property for the Chrome driver
        String path = null;
        try {
            path = URLDecoder.decode(Registration.class.getClassLoader().getResource("chromedriver").getPath(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(path);
        System.setProperty("webdriver.chrome.driver", path);
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        // Create a new instance of ChromeDriver
        WebDriver driver = new ChromeDriver();

        // Navigate to the registration page
        driver.get("https://www.amazon.com/rapids/register");

        // Find the input fields by their IDs and fill them out
        WebElement firstName = driver.findElement(By.id("ap_customer_name"));
        firstName.sendKeys("John");

        WebElement lastName = driver.findElement(By.id("ap_email"));
        lastName.sendKeys("johndoe@example.com");
        WebElement password = driver.findElement(By.id("ap_password"));
        password.sendKeys("mysecretpassword");
        WebElement email = driver.findElement(By.id("ap_password_check"));
        email.sendKeys("mysecretpassword");

        // Submit the form
//        WebElement submitButton = driver.findElement(By.id("continue"));
//        submitButton.click();

        // Wait for the registration confirmation page to load
        // You may need to adjust the wait time depending on the page's loading speed
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Close the browser
        driver.quit();
    }
}

