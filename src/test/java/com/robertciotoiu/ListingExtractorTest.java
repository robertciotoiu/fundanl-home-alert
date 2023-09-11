package com.robertciotoiu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

@SpringBootTest
class ListingExtractorTest {
    @Autowired
    ListingExtractor listingsExtractor;

    @Test
    void extract_listing_basic_test() throws IOException {
        File in = new File("src/test/resources/fundanl-example.html");
        Document doc = Jsoup.parse(in, null);

        var listings = listingsExtractor.extractListings(doc);

        Assertions.assertNotNull(listings);
        Assertions.assertEquals(15, listings.size());
    }
}
