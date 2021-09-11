package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.Date;

public class PriceHystoryEntry {
    private BigDecimal price;
    private Date startDate;

    public PriceHystoryEntry() {
    }

    public PriceHystoryEntry(BigDecimal price, Date startDate) {
        this.price = price;
        this.startDate = startDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
