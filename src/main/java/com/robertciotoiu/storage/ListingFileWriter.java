package com.robertciotoiu.storage;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robertciotoiu.Listing;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Component
public class ListingFileWriter {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final JsonFactory jsonFactory = new JsonFactory();

    @Value("${file.path}")
    private String filePath;

    private File file;

    @PostConstruct
    public void init() {
        file = Paths.get(filePath, "listings.json").toFile();
    }

    public boolean appendListingsToFile(List<Listing> listings) {
        try (FileWriter fileWriter = new FileWriter(file, true);
             JsonGenerator jsonGenerator = jsonFactory.createGenerator(fileWriter)) {

            // Serialize and append each Listing as JSON
            for (Listing listing : listings) {
                objectMapper.writeValue(jsonGenerator, listing);
                jsonGenerator.writeRaw('\n'); // Add a newline between objects
            }

            System.out.println(listings.size() + " listings appended to " + filePath);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error appending listings to file: " + e.getMessage());
            return false;
        }
    }
}
