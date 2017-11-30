package com.bigblueocean.nick.bigblueocean.model;


/**
 * Created by nick on 10/10/17.
 */

public class Product {

    private Category category;
    private String species;
    private String region;
    private String size;
    private String grade;
    private String quantity;
    private String price;

    public Product(){

    }

    public Product (Category category, String species, String regions, String grades, String sizes, String quantity, String price){
        setCategory(category);
        this.species = species;
        this.region = regions;
        this.grade = grades;
        this.size = sizes;
        this.quantity = quantity;
        this.price = price;

        if(!(category == Category.TUNA || category == Category.SWORD)){
            this.grade = "U";
        }
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        for (Category c : Category.values()){
            if (category.getTag().equalsIgnoreCase(c.getTag())){
                this.category = c;
            }
        }
    }

    public String getSpecies(){
        return species;
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
        if(!(category.getTag().equalsIgnoreCase("Tuna") || category.getTag().equalsIgnoreCase("Sword"))){
            this.grade = "U";
        }
        else
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
