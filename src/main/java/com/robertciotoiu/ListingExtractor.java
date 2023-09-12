package com.robertciotoiu;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ListingExtractor {

    private static final String LISTINGS_PARENT = "//div[contains(@class,'border-light-2 mb-4 border-b pb-4')]";
    private static final String LISTING_URL_XPATH = "div/div/div/div[1]/div[1]/a";
    private static final String LISTING_TITLE_XPATH = "div/div/div/div[1]/div[1]/a/h2";
    private static final String LISTING_PRICE_XPATH = "div/div/div/div[1]/div[1]/p";
    private static final String LISTING_LOCATION_XPATH = "div/div/div/div[1]/div[1]/a/div";
    private static final String LISTING_SURFACE_XPATH = "div/div/div/div[1]/div[1]/ul/li";

    public List<Listing> extractListings(Document doc) {
        var listings = new ArrayList<Listing>();
        var elements = doc.selectXpath(LISTINGS_PARENT);
        elements.forEach(element -> {
            var url = element.selectXpath(LISTING_URL_XPATH).attr("href");
            var title = element.selectXpath(LISTING_TITLE_XPATH).text();
            var price = element.selectXpath(LISTING_PRICE_XPATH).text();
            var location = element.selectXpath(LISTING_LOCATION_XPATH).text();
            var surface = element.selectXpath(LISTING_SURFACE_XPATH).text();

            if(!url.isEmpty())
                listings.add(new Listing(url, title, price, location, surface));
        });

        return listings;
    }
}
