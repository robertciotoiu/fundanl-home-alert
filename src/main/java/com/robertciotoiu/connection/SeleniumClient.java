package com.robertciotoiu.connection;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("seleniumClient")
public class SeleniumClient extends AbstractClient{
    @Override
    public synchronized Document getDocument(String url) {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "/Users/robert/Downloads/chromedriver-mac-arm64/chromedriver");

        // Configure Chrome options
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 13_5_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36");

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
}
