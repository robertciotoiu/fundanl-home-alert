package com.robertciotoiu.index;

import com.robertciotoiu.Listing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ListingFileWriterTest {

    @Autowired
    ListingFileWriter listingFileWriter;

    @Test
    void append_listings_to_file_test() {
        List<Listing> listings = new ArrayList<>();

        listings.add(new Listing("testUrl", "testTitle", "testPrice", "testLocation","testSurface"));
        listings.add(new Listing("testUrl1", "testTitle1", "testPrice1", "testLocation1","testSurface1"));
        listings.add(new Listing("testUrl2", "testTitle2", "testPrice2", "testLocation2","testSurface2"));
        listings.add(new Listing("testUrl3", "testTitle3", "testPrice3", "testLocation3","testSurface3"));

        listingFileWriter.appendListingsToFile(listings);

        System.out.println("Success!");
    }
}