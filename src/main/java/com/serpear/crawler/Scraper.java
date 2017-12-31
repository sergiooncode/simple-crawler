package com.serpear.crawler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Scraper {
    private static final String BASE_URL = "http://books.toscrape.com/";
    private WebDriver driver;

    Scraper() {
        PhantomJsDriverManager.getInstance().setup();
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(
                PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                "./phantomjs"
        );
        driver = new PhantomJSDriver(caps);
    }

    Map<String, HashMap<String, String>> scrape() {
        HashMap<String, HashMap<String, String>> books = new HashMap<String, HashMap<String, String>>();
        try {
            driver.get(BASE_URL);
            List<WebElement> productPods = getAllBooks();
            ArrayList<HashMap<String, String>> booksValues = new ArrayList<HashMap<String, String>>();
            for (WebElement bookElement : productPods) {
                HashMap<String, String> values = new HashMap<String, String>();
                WebElement prodLink = getLinkElement(bookElement);
                values.put("link", getLink(prodLink));
                values.put("price", getBookPrice(bookElement));
                values.put("title", getTitle(prodLink));
                booksValues.add(values);
            }

            for (HashMap<String, String> bookValues : booksValues) {
                driver.get(bookValues.get("link"));
                WebElement serialNumberEl = getSerialNumberElement();
                books.put(getSerialNumber(serialNumberEl), bookValues);
            }
        } catch(Exception exc) {
            exc.printStackTrace();
        } finally {
            driver.quit();
        }
        return books;
    }

    void storeResults(Map<String, HashMap<String, String>> scrapedResults, BookRepository repository) {
        for (Map.Entry<String, HashMap<String, String>> bookValues : scrapedResults.entrySet()) {
            HashMap<String, String> values = bookValues.getValue();
            repository.save(new Book(values.get("title"), bookValues.getKey(), values.get("link"), Utils.parsePrice(values.get("price"))));
        }
    }

    private List<WebElement> getAllBooks() {
        return driver.findElements(By.xpath("//article[@class='product_pod']"));
    }

    private WebElement getLinkElement(WebElement bookElement) {
        return bookElement.findElement(By.xpath(".//h3/a"));
    }
    private String getBookPrice(WebElement bookElement) {
        return bookElement.findElement(By.xpath(".//div[@class='product_price']/p[@class='price_color']")).getText().replace("\u00a3", "");
    }

    private String getLink(WebElement prodLink) {
        return prodLink.getAttribute("href");
    }

    private String getTitle(WebElement prodLink) {
        return prodLink.getAttribute("title");
    }

    private WebElement getSerialNumberElement() {
        return driver.findElement(By.xpath("//table[@class='table table-striped']/tbody/tr"));
    }

    private String getSerialNumber(WebElement serialNumberElement) {
        return serialNumberElement.getText().split("\\s+")[1];
    }
}
