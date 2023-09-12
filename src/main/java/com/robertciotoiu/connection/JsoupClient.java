package com.robertciotoiu.connection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Wrapper over Jsoup library.
 * Must be used for every access to a URL.
 * To use: @Autowire this component
 * Ensures even in a multithreading environment the awaiting between HTTP requests.
 */
@Component
@Qualifier("jsoupClient")
public class JsoupClient extends AbstractClient{
    @Override
    public Document getDocument(String url) throws IOException {
        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                .get();
    }
}
