package com.robertciotoiu.connection;

import com.robertciotoiu.Listing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Random;

import static java.time.Instant.now;

public abstract class AbstractClient {
    private static final Random random = new Random();
    private static final Logger logger = LogManager.getLogger(AbstractClient.class);

    /**
     * Use this method for a responsible scraping.
     * It enforces delay access to a URL to be at least 1-2 seconds from each other.
     *
     * @param url URL to access and extract its HTML
     * @return HTML of the page
     * @throws IOException when page is not accessible
     */
    public synchronized Document getHtml(String url) throws IOException, MultithreadingNotAllowedException {
        Document doc;
        try {
            doc = getDocument(url);
            detectValidation(doc);
            String time = now().toString();
            long seconds = random.nextInt(1000, 2000);
            logger.info("HTTP get request at address: {} at UTC time: {}. Awaiting {} seconds until the next request", url, time, seconds);
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            logger.fatal("Sleep failed! Application stopped or multithreading execution spotted. Stopping the application...", e);
            Thread.currentThread().interrupt();
            throw new MultithreadingNotAllowedException("Sleep failed! Application stopped or multithreading execution spotted. Stopping the application...");
        }

        return doc;
    }

    public abstract Document getDocument(String url) throws IOException;
    public abstract boolean applyToListing(Listing listing);

    private void detectValidation(Document carSpecPage) {
        if(carSpecPage.html().contains("Challenge Validation")){
            logger.error("Validation captcha required. Stopping the scraper...");
            throw new CaptchaValidationError("Validation captcha required. Stopping the scraper...");
        }
    }

}
