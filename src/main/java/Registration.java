import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



public class Registration {
    public static void main(String[] args) {
        try {
            script();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void script() {
        // Set the system property for the Chrome driver
        String path;
        String profile = "/Users/anbu/Library/Application Support/Google/Chrome/Profile 3";
        path = URLDecoder.decode(Objects.requireNonNull(Registration.class.getClassLoader().getResource("chromedriver")).getPath(), StandardCharsets.UTF_8);
        System.out.println(path);
        System.setProperty("webdriver.chrome.driver", path);
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=" + profile);

        options.addArguments("--disable-application-cache");
        options.addArguments("--disable-cache");
        options.addArguments("--disk-cache-size=0");
        options.addArguments("--v8-cache-options=off");

        // Create a new instance of ChromeDriver
        WebDriver driver = new ChromeDriver(options);

        driver.manage().deleteAllCookies();
        // Navigate to the registration page
        driver.get("https://www.amazon.com/rapids/register");

        // Find the input fields by their IDs and fill them out
        List<WebElement> webElements = new SpreadSheet().getIds().stream().peek(System.out::println).map(id -> driver.findElement(By.id(id))).collect(Collectors.toList());
        new SpreadSheet().getRows()
//                .peek(System.out::println)
                .forEach(row -> IntStream.range(0, webElements.size()).forEach(i -> {
                    if (row.get(i).equalsIgnoreCase("click")) {
                        webElements.get(i).click();

                    } else if (row.get(i).equalsIgnoreCase("wait")) {
                        try {
                            Thread.sleep(Integer.valueOf(i) * 1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if (row.get(i).equalsIgnoreCase("refresh")) {
                        try {
                            Thread.sleep(Integer.valueOf(i) * 1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        webElements.get(i).clear();
                        webElements.get(i).sendKeys(row.get(i));
                    }
                }));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.navigate().refresh();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.navigate().refresh();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.navigate().refresh();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
                System.out.println("Waiting");
                WebElement button = null;
                try {
                    clickbuttonInIframe(driver);
                    // switch back to the default content
                    driver.switchTo().defaultContent();
                } catch (Exception e) {
                    System.out.println(e + "  *********   " + e.getMessage());
                }

        System.out.println("Waiting for captcha resolver");
        // Wait for the registration confirmation page to load
        // You may need to adjust the wait time depending on the page's loading speed
        try {
            Thread.sleep(280000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Close the browser
        driver.quit();
    }

    private static void clickbuttonInIframe(WebDriver driver) {
        driver.switchTo().frame("cvf-aamation-challenge-iframe");
        driver.switchTo().frame("aacb-arkose-frame");
        WebElement iFrame = driver.findElement(By.tagName("iframe"));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.switchTo().frame(iFrame);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.switchTo().frame("fc-iframe-wrap");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // switch to the nested iframe
        WebElement captchaFrame = driver.findElement(By.id("CaptchaFrame"));
        driver.switchTo().frame(captchaFrame);

        System.out.println("----   Page : " + driver.getPageSource());
        // click the button inside the iframe
        WebElement captchaButton = driver.findElement(By.id("home_children_button"));
        captchaButton.click();
    }

}

