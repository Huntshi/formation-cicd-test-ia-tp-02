package com.devops.cicd;

import com.devops.cicd.PricingConfig;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class PricingServiceTest {

    private final PricingConfig fakeConfig = new PricingConfig(20.0, 50.0);
    private final PricingService service = new PricingService(fakeConfig);

    @Test
    void vatAppliedCorrectly() {
        assertEquals(120.0, service.applyVat(100.0), 0.0001);
    }

    @Test
    void vipDiscount_applies10Percent() {
        assertEquals(90.0, service.applyVipDiscount(100.0, true), 0.0001);
        assertEquals(100.0, service.applyVipDiscount(100.0, false), 0.0001);
    }

    @Test
    void shipping_freeAboveOrEqualThreshold() {
        assertEquals(0.0, service.shippingCost(50.0), 0.0001);
        assertEquals(0.0, service.shippingCost(120.0), 0.0001);
    }

    @Test
    void shipping_paidBelowThreshold() {
        assertEquals(4.99, service.shippingCost(49.99), 0.0001);
    }

    @Test
    void finalTotal_vipTrue_freeShipping() {
        // HT=100 -> TTC=120
        // VIP => 108
        // shipping free
        assertEquals(108.0, service.finalTotal(100.0, true), 0.0001);
    }

    @Test
    void finalTotal_vipFalse_shippingPaid() {
        // HT=40 -> TTC=48
        // no discount => 48
        // shipping => 4.99
        assertEquals(52.99, service.finalTotal(40.0, false), 0.0001);
    }

    @Test
    void constructorRejectsNullConfig() {
        assertThrows(IllegalArgumentException.class, () -> new PricingService(null));
    }
}
