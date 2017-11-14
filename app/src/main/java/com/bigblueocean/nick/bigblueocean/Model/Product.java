package com.bigblueocean.nick.bigblueocean.Model;


import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by nick on 10/10/17.
 */

public class Product {

    private Category category;
    private String [] productDetails = new String[5];
    private String species;
    private String region;
    private String size;
    private String grade;
    private String quantity;
    private String price;

    public Product(){

    }

    public Product (Category category, String species, String regions, String grades, String sizes, String quantity, String price){
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
        this.category = category;
        this.species = species;
        this.region = regions;
        this.grade = grades;
        this.size = sizes;
        this.quantity = quantity;
        this.price = price;
        String nf = numberFormat.format(Double.parseDouble(price));
        this.productDetails[0] = this.region;
        this.productDetails[1] = this.grade;
        this.productDetails[2] = this.size;
        this.productDetails[3] = this.quantity;
        this.productDetails[4] = nf;

        if(!(category.getTag().equalsIgnoreCase("Tuna") || category.getTag().equalsIgnoreCase("Sword"))){
            this.grade = "N/A";
        }
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String [] getDescription() {
        return productDetails;
    }

    public String getSpecies(){
        return this.species;
    }

    public String getRegion() {
        return region;
    }

    public String getSize() {
        return size;
    }

    public String getGrade() {
        return grade;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setRegion(String region){
        this.region = region;
    }

    public void setGrade(String grade){
        this.grade = grade;
    }

    public void setQuantity(String quantity){
        this.quantity = quantity;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setSpecies(String species){
        this.species = species;
    }
}
