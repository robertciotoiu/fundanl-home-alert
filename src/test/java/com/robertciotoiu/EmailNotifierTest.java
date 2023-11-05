package com.robertciotoiu;

import com.robertciotoiu.notification.EmailNotifier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class EmailNotifierTest {

    @Autowired
    private EmailNotifier emailNotifier;

    @Test
    public void testEmailNotifier() {
        emailNotifier.notify(List.of(new Listing("testUrl", "testTitle", "testPrice", "testLocation","testSurface")));
    }
}
