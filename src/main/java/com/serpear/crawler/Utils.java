package com.serpear.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public final class Utils {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    static BigDecimal parsePrice(String priceString) {
        DecimalFormat decimalFormat;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        String pattern = "#,##0.0#";
        decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);
        BigDecimal price = new BigDecimal("0.00");
        try {
            price = (BigDecimal) decimalFormat.parse(priceString);
        } catch (ParseException e) {
            e.printStackTrace();
            log.error(String.format("Exception %s caught while parsing price string", e));
        }
        return price;
    }
}
