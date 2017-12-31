package com.serpear.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner runScrape(BookRepository repository) {
		return (args) -> {
			Scraper scraper = new Scraper();
			Map<String, HashMap<String, String>> scrapedResults = scraper.scrape();
			scraper.storeResults(scrapedResults, repository);
		};
	}

}
