package com.robertciotoiu;

import com.robertciotoiu.connection.JsoupWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class HomeAlerterService {
    private static final String FUNDA_HTML="https://www.funda.nl/zoeken/huur?selected_area=%5B%22amsterdam,30km%22%5D&price=%22-1500%22&object_type=%5B%22house%22,%22apartment%22%5D&sort=%22date_down%22&floor_area=%2250-%22";

    @Autowired
    private ListingExtractor listingExtractor;

    Map<String, Listing> cache = new HashMap<>();

    @Scheduled(fixedDelayString = "${scheduler.fixed-delay}", timeUnit = TimeUnit.MINUTES)
    private void scrape() throws IOException {
        JsoupWrapper jsoupWrapper = new JsoupWrapper();
        var doc = jsoupWrapper.getHtml(FUNDA_HTML);

        var newListings = listingExtractor.extractListings(doc);

        newListings.forEach(newListing -> {
            if (!cache.containsKey(newListing.getUrl())) {
                sendEmail(newListing);
                cache.put(newListing.getUrl(), newListing);
                System.out.println("New listing: " + newListing);
            }
        });
    }

    private void sendEmail(Listing newListing) {

    }
}
