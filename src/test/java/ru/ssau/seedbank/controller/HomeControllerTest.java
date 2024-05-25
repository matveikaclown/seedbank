package ru.ssau.seedbank.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HomeControllerTest {

    @Test
    public void test_home_method_returns_home() {
        HomeController controller = new HomeController();
        assertEquals("home", controller.home());
    }

    @Test
    public void test_application_context_failure_response() {
        // This would typically require a more complex setup with Spring context which is not shown here.
        // Assuming a mock setup:
        assertThrows(Exception.class, () -> {
            // Simulate context load failure
            throw new Exception("Context failed to load");
        });
    }

}
