package com.bigblueocean.nick.bigblueocean.model;

import java.util.ArrayList;

/**
 * Created by nick on 12/20/17.
 */

public class Order {
    private String timestamp;
    private String sender;
    private ArrayList<Product> lineItems = new ArrayList<Product>();

    public Order(String timestamp, String sender, ArrayList<Product> lineItems){
        this.timestamp = timestamp;
        this.sender = sender;
        this.lineItems.addAll(lineItems);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public ArrayList<Product> getLineItems() {
        return lineItems;
    }

    public void setLineItems(ArrayList<Product> lineItems) {
        this.lineItems = lineItems;
    }
}
