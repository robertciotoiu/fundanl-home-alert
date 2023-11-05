package com.robertciotoiu.connection;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HtmlExtractor {

    private final WebDriver driver = WebDriverManager
            .chromedriver()
            .browserInDocker()
            .create();

    private String notificationUrl;

    @Autowired
    public HtmlExtractor(@Value("${notification.url}") String notificationUrl) {
        this.notificationUrl = notificationUrl;
        driver.get(notificationUrl);
    }

    public Document getHtml() {
        driver.navigate().refresh();
        var html = driver.getPageSource();
        return Jsoup.parse(html);
    }

}
