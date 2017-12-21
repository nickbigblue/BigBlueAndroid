package com.bigblueocean.nick.bigblueocean.model;

import java.util.ArrayList;

/**
 * Created by nick on 12/20/17.
 */

public class OrderHeader {
    private String timestamp;
    private String orderId;
    private ArrayList<String> lineItems = new ArrayList<>();

    public OrderHeader(String timestamp, String orderId, ArrayList<String> lineItems){
        this.timestamp = timestamp;
        this.orderId = orderId;
        this.lineItems.addAll(lineItems);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ArrayList<String> getLineItems() {
        return lineItems;
    }

    public void setLineItems(ArrayList<String> lineItems) {
        this.lineItems = lineItems;
    }
}
