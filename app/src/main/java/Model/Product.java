package Model;

/**
 * Created by nick on 10/10/17.
 */

public class Product {
    private String title;
    private String description;
    private String imageName;
    private String [] regions;
    private String [] sizes;
    private String [] grades;

    public Product (String title, String description, String imageName, String[] regions, String [] sizes, String [] grades){
        this.title = title;
        this.description = description;
        this.imageName = imageName;
        this.regions = regions;
        this.sizes = sizes;
        this.grades = grades;
    }
}
