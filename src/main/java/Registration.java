import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Registration {
    public static void main(String[] args) {
        script();
    }

    static void script() {
        // Set the system property for the Chrome driver
        String path;
        path = URLDecoder.decode(Registration.class.getClassLoader().getResource("chromedriver").getPath(), StandardCharsets.UTF_8);
        System.out.println(path);
        System.setProperty("webdriver.chrome.driver", path);
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        // Create a new instance of ChromeDriver
        WebDriver driver = new ChromeDriver();
        // Navigate to the registration page
        driver.get("https://www.amazon.com/rapids/register");

        // Find the input fields by their IDs and fill them out
        List<WebElement> webElements = new SpreadSheet().getIds()
                .stream()
                .peek(System.out::println)
                .map(id -> driver.findElement(By.id(id)))
                .collect(Collectors.toList());
        new SpreadSheet().getRows().stream().peek(System.out::println)
                .forEach(row -> IntStream.range(0, webElements.size() - 1)
                        .forEach(i -> {
                            webElements.get(i).clear();
                            webElements.get(i).sendKeys(row.get(i));
                        }));


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

