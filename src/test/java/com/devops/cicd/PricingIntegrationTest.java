package com.devops.cicd;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
class PricingIntegrationTest {

    @Test
    void fullPricingFlow_withRealConfigFile() {
        PricingConfigLoader loader = new PricingConfigLoader();
        PricingConfig config = loader.load();

        assertEquals(20.0, config.getVatRate(), 0.0001);
        assertEquals(50.0, config.getFreeShippingThreshold(), 0.0001);

        PricingService service = new PricingService(config);

        // HT=100, VAT=20%, VIP=true => 108 + free shipping
        assertEquals(108.0, service.finalTotal(100.0, true), 0.0001);
    }
}
