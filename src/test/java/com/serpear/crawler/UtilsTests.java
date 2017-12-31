package com.serpear.crawler;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class UtilsTests {

    @Test
    public void parsePrice() {
        String priceString = "50.95";
        BigDecimal expectedPrice = new BigDecimal(priceString);

        assertEquals(expectedPrice, Utils.parsePrice(priceString));
    }
}