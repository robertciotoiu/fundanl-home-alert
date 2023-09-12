package com.robertciotoiu;

import java.util.Objects;

public class Listing {
    private String url;
    private String title;
    private String price;
    private String location;
    private String surface;

    public Listing(String url, String title, String price, String location, String surface) {
        this.url = url;
        this.title = title;
        this.price = price;
        this.location = location;
        this.surface = surface;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Listing listing = (Listing) o;

        return Objects.equals(url, listing.url);
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Listing: \n" +
                "\turl='" + url + "'\n" +
                "\ttitle='" + title + "'\n" +
                "\tprice='" + price + "'\n" +
                "\tlocation='" + location + "'\n" +
                "\tsurface='" + surface + "'\n";
    }
}
