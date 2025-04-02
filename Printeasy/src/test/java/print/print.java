import org.openqa.selenium.WebDriver; // Browser automation ke liye
import org.openqa.selenium.chrome.ChromeDriver; // Chrome browser ke liye
import org.openqa.selenium.By; // Elements find karne ke liye
import org.openqa.selenium.WebElement; // Web elements ko handle karne ke liye
import org.openqa.selenium.Keys; // Keyboard inputs ke liye
import org.openqa.selenium.interactions.Actions; // Mouse actions (hover, drag-drop)
import org.openqa.selenium.support.ui.WebDriverWait; // Explicit waits ke liye
import org.openqa.selenium.support.ui.ExpectedConditions; // Wait conditions ke liye
import java.time.Duration; // Time-based operations ke liye

public class CartTest {
    public static void main(String[] args) {
        // Step 1: Initialize WebDriver
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver"); // Change path accordingly
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        try {
            // Step 2: Open Cart Page
            driver.get("https://printeasy.vercel.app/cart");
            System.out.println("✅ Cart page opened successfully.");

            // Step 3: Check if Cart Title is Displayed
            WebElement cartTitle = driver.findElement(By.xpath("//h1[contains(text(),'Your Cart')]"));
            if (cartTitle.isDisplayed()) {
                System.out.println("✅ Cart title is visible.");
            }

            // Step 4: Check if Cart is Empty
            try {
                WebElement emptyCartMsg = driver.findElement(By.xpath("//p[contains(text(),'Your cart is empty')]"));
                if (emptyCartMsg.isDisplayed()) {
                    System.out.println("✅ Empty cart message is displayed.");
                }
            } catch (Exception e) {
                System.out.println("🛒 Cart is not empty, checking product details...");

                // Step 5: Validate Product Details
                WebElement productName = driver.findElement(By.className("product-name"));
                WebElement productPrice = driver.findElement(By.className("product-price"));
                WebElement quantitySelector = driver.findElement(By.className("quantity-selector"));

                if (productName.isDisplayed() && productPrice.isDisplayed() && quantitySelector.isDisplayed()) {
                    System.out.println("✅ Product details are correctly displayed.");
                }

                // Step 6: Remove Product from Cart
                WebElement removeBtn = driver.findElement(By.className("remove-button"));
                removeBtn.click();
                Thread.sleep(3000); // Wait for item to be removed

                // Step 7: Verify Cart is Empty
                WebElement emptyCartMsg = driver.findElement(By.xpath("//p[contains(text(),'Your cart is empty')]"));
                if (emptyCartMsg.isDisplayed()) {
                    System.out.println("✅ Product removed successfully, cart is now empty.");
                }
            }

            // Step 8: Check Checkout Button (if cart has products)
            try {
                WebElement checkoutBtn = driver.findElement(By.xpath("//button[contains(text(),'Checkout')]"));
                if (checkoutBtn.isDisplayed()) {
                    System.out.println("✅ Checkout button is visible.");
                }
            } catch (Exception e) {
                System.out.println("ℹ️ No checkout button found as cart is empty.");
            }

        } catch (Exception e) {
            System.out.println("❌ Test failed: " + e.getMessage());
        } finally {
            // Close the browser
            driver.quit();
            System.out.println("🚀 Test Completed & Browser Closed!");
        }
    }
}
