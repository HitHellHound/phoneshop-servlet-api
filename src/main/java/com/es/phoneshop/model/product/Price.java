package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.Date;

public class Price {
    private BigDecimal price;
    private Date startDate;

    public Price() {
    }

    public Price(BigDecimal price, Date startDate) {
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
