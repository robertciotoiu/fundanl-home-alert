package com.robertciotoiu.notification;

import com.robertciotoiu.Listing;

import java.util.List;

public interface Notifier {
    void notify(List<Listing> newListing);
}
