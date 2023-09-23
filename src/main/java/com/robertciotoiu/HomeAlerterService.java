package com.robertciotoiu;

import com.robertciotoiu.connection.AbstractClient;
import com.robertciotoiu.notification.Notifier;
import com.robertciotoiu.storage.ListingFileWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class HomeAlerterService {
    @Autowired
    ListingFileWriter listingFileWriter;
    Map<String, Listing> cache = new HashMap<>();

    @Value("${notification.url}")
    private String FUNDA_HTML;

    @Autowired
    @Qualifier("seleniumClient")
    private AbstractClient client;
    @Autowired
    @Qualifier("email")
    private Notifier notifier;
    @Autowired
    private ListingExtractor listingExtractor;
    private boolean isWarmup = true;

    @Scheduled(fixedDelayString = "${scheduler.fixed-delay}", initialDelayString = "${scheduler.initialDelay}", timeUnit = TimeUnit.MINUTES)
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

        storeListings(listingsToNotify);
        sendNotification(listingsToNotify);
//        applyToListings(listingsToNotify);//TODO: not working because of captcha
        cleanupCache(newListings);
    }

    private void applyToListings(List<Listing> listingsToNotify) {
        if (!listingsToNotify.isEmpty() && !isWarmup) {
            listingsToNotify.forEach(listing -> {
                var applicationResult = client.applyToListing(listing);
                System.out.println(applicationResult ? "Applied to listing " + listing.getUrl() : "Failed to apply to listing " + listing.getUrl());
            });
        }
    }

    private void storeListings(ArrayList<Listing> listingsToNotify) {
        if (!listingsToNotify.isEmpty() && !isWarmup) {
            listingFileWriter.appendListingsToFile(listingsToNotify);
        }
    }

    private void sendNotification(List<Listing> listingsToNotify) {
        if (!listingsToNotify.isEmpty() && !isWarmup) {
            notifier.notify(listingsToNotify);
            System.out.println("Notified about " + listingsToNotify.size() + " new listings!");
        }

        isWarmup = false;
    }

    private void cleanupCache(List<Listing> listings) {
        if (cache.size() > 100) {
            cache.clear();
            listings.forEach(listing -> cache.put(listing.getUrl(), listing));
            System.out.println("Cache cleaned up!");
        }
    }
}
