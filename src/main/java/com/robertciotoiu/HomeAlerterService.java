package com.robertciotoiu;

import com.robertciotoiu.connection.AbstractClient;
import com.robertciotoiu.notification.Notifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class HomeAlerterService {
    private static final String FUNDA_HTML="https://www.funda.nl/zoeken/huur?selected_area=%5B%22amsterdam,30km%22%5D&price=%22-1500%22&object_type=%5B%22house%22,%22apartment%22%5D&sort=%22date_down%22&floor_area=%2250-%22";

    @Autowired
    @Qualifier("seleniumClient")
    private AbstractClient client;
    @Autowired
    @Qualifier("email")
    private Notifier notifier;
    @Autowired
    private ListingExtractor listingExtractor;
    Map<String, Listing> cache = new HashMap<>();
    private boolean isWarmup = true;

    @Scheduled(fixedDelayString = "${scheduler.fixed-delay}", timeUnit = TimeUnit.MINUTES)
    private void scrape() throws IOException {
        var doc = client.getHtml(FUNDA_HTML);
        var newListings = listingExtractor.extractListings(doc);
        var listingsToNotify = new ArrayList<Listing>();

        newListings.forEach(newListing -> {
            if (!cache.containsKey(newListing.getUrl())) {
                listingsToNotify.add(newListing);
                cache.put(newListing.getUrl(), newListing);
            }
        });

        sendNotification(listingsToNotify);
    }

    private void sendNotification(ArrayList<Listing> listingsToNotify) {
        if(!listingsToNotify.isEmpty() && !isWarmup) {
            notifier.notify(listingsToNotify);
            System.out.println("Notified about " + listingsToNotify.size() + " new listings!");
        }

        isWarmup = false;
    }
}
