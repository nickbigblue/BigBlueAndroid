package com.bigblueocean.nick.bigblueocean.Model;

/**
 * Created by nick on 10/10/17.
 */

public class Product {

    private static final int COUNT = 10;

    private Category category;
    private String [] productDetails = new String[5];
    private String region;
    private String size;
    private String grade;
    private String quantity;
    private String price;

    public Product (Category category, String regions, String grades, String sizes, String quantity, String price){
        this.category = category;
        this.region = regions;
        this.grade = grades;
        this.size = sizes;
        this.quantity = quantity;
        this.price = price;
        this.productDetails[0] = this.region;
        this.productDetails[1] = this.grade;
        this.productDetails[2] = this.size;
        this.productDetails[3] = this.quantity;
        this.productDetails[4] = this.price;

        if(!(category.getTitle().equalsIgnoreCase("Tuna H&G Wild-Caught") || category.getTitle().equalsIgnoreCase("Sword H&G Wild-Caught"))){
            this.grade = "N/A";
        }
    }

    public Category getCategory() {
        return category;
    }

    public String [] getDescription() {
        return productDetails;
    }

}
