package com.robertciotoiu.connection;

import com.robertciotoiu.Listing;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SeleniumClientTest {

    @Autowired
    private SeleniumClient seleniumClient;

    @Test
    void applyToListing() {
        seleniumClient.applyToListing(new Listing("www.google.com", "test", "test", "test", "test"));
    }

    @Disabled
    @Test
    void apply_to_real_listing_test(){
        var realListing = new Listing("https://www.funda.nl/huur/nijmegen/huis-88654391-st-jacobslaan-556/", "test", "test", "test", "test");
        seleniumClient.applyToListing(realListing);
    }
}