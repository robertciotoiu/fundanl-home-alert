package com.robertciotoiu.connection;

import com.robertciotoiu.Listing;
import io.github.bonigarcia.wdm.WebDriverManager;
import jakarta.annotation.PostConstruct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Qualifier("seleniumClient")
public class SeleniumClient extends AbstractClient{
    ChromeOptions chromeOptions = new ChromeOptions();
    private static final String LISTING_APPLY_PATH = "reageer";
    @Value("${apply.message}")
    private String message;
    @Value("${apply.email}")
    private String email;
    @Value("${apply.salutation}")
    private String salutation;
    @Value("${apply.firstName}")
    private String firstName;
    @Value("${apply.secondName}")
    private String secondName;
    @Value("${apply.phoneNumber}")
    private String phoneNumber;

    @PostConstruct
    public void initialize() {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "/Users/robert/Downloads/chromedriver-mac-arm64/chromedriver");

        // Configure Chrome options
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 13_5_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36");
    }

    @Override
    public synchronized Document getDocument(String url) {
        // Create a new ChromeDriver instance with desired capabilities
        WebDriver driver = WebDriverManager.chromedriver().create();

        // Navigate to a webpage
        driver.get(url);

        // Perform actions on the page (e.g., clicking buttons, filling forms, etc.)
        // Execute JavaScript if needed

        // Use WebDriver to find and extract data from the page
        var html = driver.getPageSource();

        // Close the WebDriver when done
        driver.quit();

        return Jsoup.parse(html);
    }

    @Override
    public boolean applyToListing(Listing listing) {
        var url = listing.getUrl() + LISTING_APPLY_PATH;

        // Create a new ChromeDriver instance with desired capabilities
        WebDriver driver = WebDriverManager.chromedriver().create();

        // Navigate to listing application form
        driver.get(url);

        solveCookies(driver);

        // Locate form elements on the new page
        WebElement messageElement = driver.findElement(By.id("Opmerking"));
        WebElement emailElement = driver.findElement(By.id("Email"));
        Select salutationElement = new Select(driver.findElement(By.id("select-salutation-type")));
        WebElement firstNameElement = driver.findElement(By.id("Voornaam"));
        WebElement secondNameElement = driver.findElement(By.id("Achternaam"));
        WebElement phoneNumberElement = driver.findElement(By.id("Telefoon"));

        // Enter data into the input field
        messageElement.sendKeys(message);
        emailElement.sendKeys(email);
        salutationElement.selectByValue(salutation);
        firstNameElement.sendKeys(firstName);
        secondNameElement.sendKeys(secondName);
        phoneNumberElement.sendKeys(phoneNumber);

        // Locate and click on the submit button
        WebElement submitButton = driver.findElement(By.className("form-submit"));
        submitButton.click();

        // Get the updated HTML content of the page
        String html = driver.getPageSource();

        // Close the session
        driver.quit();
        //TODO: check if this is the correct message
        if(html.contains("Je aanvraag is ontvangen")){
            return true;
        } else{
            System.err.println("Could not apply to listing: " + listing.getUrl() + ". Returned html: " + html);
            return false;
        }
    }

    private void solveCookies(WebDriver driver) {
        WebElement acceptCookiesButton = driver.findElement(By.id("didomi-notice-agree-button"));
        acceptCookiesButton.click();
    }
}
