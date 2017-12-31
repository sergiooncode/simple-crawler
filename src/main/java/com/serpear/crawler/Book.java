package com.serpear.crawler;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String title;
    private String serialNumber;
    private String link;
    private BigDecimal price;

    protected Book() {}

    public Book(String title, String serialNumber, String link, BigDecimal price) {
        this.title = title;
        this.serialNumber = serialNumber;
        this.link = link;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format(
                "Book[id=%d, title='%s', serialNumber='%s', link='%s']",
                id, title, serialNumber, link);
    }

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getSerialNumber() {
		return serialNumber;
	}
}

