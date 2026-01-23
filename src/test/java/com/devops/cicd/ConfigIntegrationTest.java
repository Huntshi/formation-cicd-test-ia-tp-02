package com.devops.cicd;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ConfigIntegrationTest {

    @Test
    void canLoadConfigFile() throws Exception {
        Properties props = new Properties();

        try (InputStream in = getClass().getClassLoader().getResourceAsStream("app.properties")) {
            assertNotNull(in, "app.properties doit être présent dans src/test/resources");
            props.load(in);
        }

        assertEquals("20", props.getProperty("vatRate"));
        assertEquals("50", props.getProperty("freeShippingThreshold"));
    }
}
