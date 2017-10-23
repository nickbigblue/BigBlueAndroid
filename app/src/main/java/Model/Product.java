package Model;

import com.bigblueocean.nick.bigblueocean.dummy.DummyContent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nick on 10/10/17.
 */

public class Product {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyContent.DummyItem> ITEMS = new ArrayList<DummyContent.DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyContent.DummyItem> ITEM_MAP = new HashMap<String, DummyContent.DummyItem>();

    private static final int COUNT = 10;

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
