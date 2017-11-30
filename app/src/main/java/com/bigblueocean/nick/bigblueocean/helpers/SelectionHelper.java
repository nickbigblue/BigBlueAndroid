package com.bigblueocean.nick.bigblueocean.helpers;

import java.util.ArrayList;

import in.goodiebag.carouselpicker.CarouselPicker;

/**
 * Created by nick on 11/6/17.
 */

public class SelectionHelper {
    public ArrayList<CarouselPicker.PickerItem> getTunaSelections(){
        ArrayList<CarouselPicker.PickerItem> tunaSelections = new ArrayList<>();
        tunaSelections.add(new CarouselPicker.TextItem("Region", 12));
        tunaSelections.add(new CarouselPicker.TextItem("Grade", 12));
        tunaSelections.add(new CarouselPicker.TextItem("Species", 12));
        tunaSelections.add(new CarouselPicker.TextItem("Size", 12));
        tunaSelections.add(new CarouselPicker.TextItem("Quantity", 12));
        tunaSelections.add(new CarouselPicker.TextItem("($.$$)", 14));
        return tunaSelections;
    }

        public ArrayList<CarouselPicker.PickerItem> getTunaSpecies(){
            ArrayList<CarouselPicker.PickerItem> tunaSpecies = new ArrayList<>();
            tunaSpecies.add(new CarouselPicker.TextItem("No Pref.", 8));
            tunaSpecies.add(new CarouselPicker.TextItem("Big Eye", 8));
            tunaSpecies.add(new CarouselPicker.TextItem("Yellow Fin", 8));
            tunaSpecies.add(new CarouselPicker.TextItem("Blue Fin", 8));
            return tunaSpecies;
        }

        public ArrayList<CarouselPicker.PickerItem> getTunaGrades(){
            ArrayList<CarouselPicker.PickerItem> tunaGrades = new ArrayList<>();
            tunaGrades.add(new CarouselPicker.TextItem("No Pref.", 8));
            tunaGrades.add(new CarouselPicker.TextItem("2", 10));
            tunaGrades.add(new CarouselPicker.TextItem("2+", 10));
            tunaGrades.add(new CarouselPicker.TextItem("1", 10));
            tunaGrades.add(new CarouselPicker.TextItem("1+", 10));
            return tunaGrades;
        }

        public ArrayList<CarouselPicker.PickerItem> getTunaSizes(){
            ArrayList<CarouselPicker.PickerItem> tunaSizes = new ArrayList<>();
            tunaSizes.add(new CarouselPicker.TextItem("No Pref.", 8));
            tunaSizes.add(new CarouselPicker.TextItem("40+", 10));
            tunaSizes.add(new CarouselPicker.TextItem("60+", 10));
            tunaSizes.add(new CarouselPicker.TextItem("100+", 10));
            tunaSizes.add(new CarouselPicker.TextItem("150+", 10));
            return tunaSizes;
        }


////////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<CarouselPicker.PickerItem> getSwordSelections(){
        ArrayList<CarouselPicker.PickerItem> swordSelections = new ArrayList<>();
        swordSelections.add(new CarouselPicker.TextItem("Region", 14));
        swordSelections.add(new CarouselPicker.TextItem("Grade", 14));
        swordSelections.add(new CarouselPicker.TextItem("Size", 14));
        swordSelections.add(new CarouselPicker.TextItem("Quantity", 14));
        swordSelections.add(new CarouselPicker.TextItem("($.$$)", 16));
        return swordSelections;
    }

    public ArrayList<CarouselPicker.PickerItem> getSwordGrades(){
        ArrayList<CarouselPicker.PickerItem> swordGrades = new ArrayList<>();
        swordGrades.add(new CarouselPicker.TextItem("No Pref.", 8));
        swordGrades.add(new CarouselPicker.TextItem("BR", 10));
        swordGrades.add(new CarouselPicker.TextItem("R-", 10));
        swordGrades.add(new CarouselPicker.TextItem("R", 10));
        swordGrades.add(new CarouselPicker.TextItem("R+", 10));
        return swordGrades;
    }

    public ArrayList<CarouselPicker.PickerItem> getSwordSizes(){
        ArrayList<CarouselPicker.PickerItem> swordSizes = new ArrayList<>();
        swordSizes.add(new CarouselPicker.TextItem("No Pref.", 8));
        swordSizes.add(new CarouselPicker.TextItem("40+", 10));
        swordSizes.add(new CarouselPicker.TextItem("60+", 10));
        swordSizes.add(new CarouselPicker.TextItem("100+", 10));
        swordSizes.add(new CarouselPicker.TextItem("150+", 10));
        return swordSizes;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<CarouselPicker.PickerItem> getMahiSelections(){
        ArrayList<CarouselPicker.PickerItem> mahiSelections = new ArrayList<>();
        mahiSelections.add(new CarouselPicker.TextItem("Region", 14));
        mahiSelections.add(new CarouselPicker.TextItem("Size", 14));
        mahiSelections.add(new CarouselPicker.TextItem("Quantity", 14));
        mahiSelections.add(new CarouselPicker.TextItem("($.$$)", 16));
        return mahiSelections;
    }

    public ArrayList<CarouselPicker.PickerItem> getMahiSizes(){
        ArrayList<CarouselPicker.PickerItem> mahiSizes = new ArrayList<>();
        mahiSizes.add(new CarouselPicker.TextItem("No Pref.", 8));
        mahiSizes.add(new CarouselPicker.TextItem("40+", 10));
        mahiSizes.add(new CarouselPicker.TextItem("60+", 10));
        mahiSizes.add(new CarouselPicker.TextItem("100+", 10));
        mahiSizes.add(new CarouselPicker.TextItem("150+", 10));
        return mahiSizes;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<CarouselPicker.PickerItem> getWahooSelections(){
        ArrayList<CarouselPicker.PickerItem> wahooSelections = new ArrayList<>();
        wahooSelections.add(new CarouselPicker.TextItem("Region", 14));
        wahooSelections.add(new CarouselPicker.TextItem("Size", 14));
        wahooSelections.add(new CarouselPicker.TextItem("Quantity", 14));
        wahooSelections.add(new CarouselPicker.TextItem("($.$$)", 16));
        return wahooSelections;
    }

    public ArrayList<CarouselPicker.PickerItem> getWahooSizes(){
        ArrayList<CarouselPicker.PickerItem> wahooSizes = new ArrayList<>();
        wahooSizes.add(new CarouselPicker.TextItem("No Pref.", 8));
        wahooSizes.add(new CarouselPicker.TextItem("40+", 10));
        wahooSizes.add(new CarouselPicker.TextItem("60+", 10));
        wahooSizes.add(new CarouselPicker.TextItem("100+", 10));
        wahooSizes.add(new CarouselPicker.TextItem("150+", 10));
        return wahooSizes;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<CarouselPicker.PickerItem> getGrouperSelections(){
        ArrayList<CarouselPicker.PickerItem> grouperSelections = new ArrayList<>();
        grouperSelections.add(new CarouselPicker.TextItem("Species", 14));
        grouperSelections.add(new CarouselPicker.TextItem("Region", 14));
        grouperSelections.add(new CarouselPicker.TextItem("Size", 14));
        grouperSelections.add(new CarouselPicker.TextItem("Quantity", 14));
        grouperSelections.add(new CarouselPicker.TextItem("($.$$)", 16));
        return grouperSelections;
    }

    public ArrayList<CarouselPicker.PickerItem> getGrouperSpecies(){
        ArrayList<CarouselPicker.PickerItem> grouperSpecies = new ArrayList<>();
        grouperSpecies.add(new CarouselPicker.TextItem("No Pref.", 8));
        grouperSpecies.add(new CarouselPicker.TextItem("Red", 10));
        grouperSpecies.add(new CarouselPicker.TextItem("Black", 10));
        grouperSpecies.add(new CarouselPicker.TextItem("Dusky", 10));
        return grouperSpecies;
    }

    public ArrayList<CarouselPicker.PickerItem> getGrouperSizes(){
        ArrayList<CarouselPicker.PickerItem> grouperSizes = new ArrayList<>();
        grouperSizes.add(new CarouselPicker.TextItem("No Pref.", 8));
        grouperSizes.add(new CarouselPicker.TextItem("40+", 10));
        grouperSizes.add(new CarouselPicker.TextItem("60+", 10));
        grouperSizes.add(new CarouselPicker.TextItem("100+", 10));
        grouperSizes.add(new CarouselPicker.TextItem("150+", 10));
        return grouperSizes;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<CarouselPicker.PickerItem> getSalmonSelections(){
        ArrayList<CarouselPicker.PickerItem> salmonSelections = new ArrayList<>();
        salmonSelections.add(new CarouselPicker.TextItem("Species", 14));
        salmonSelections.add(new CarouselPicker.TextItem("Region", 14));
        salmonSelections.add(new CarouselPicker.TextItem("Size", 14));
        salmonSelections.add(new CarouselPicker.TextItem("Quantity", 14));
        salmonSelections.add(new CarouselPicker.TextItem("($.$$)", 16));
        return salmonSelections;
    }

    public ArrayList<CarouselPicker.PickerItem> getSalmonSpecies(){
        ArrayList<CarouselPicker.PickerItem> salmonSpecies = new ArrayList<>();
        salmonSpecies.add(new CarouselPicker.TextItem("No Pref.", 8));
        salmonSpecies.add(new CarouselPicker.TextItem("H&G", 10));
        salmonSpecies.add(new CarouselPicker.TextItem("Fillet", 10));
        return salmonSpecies;
    }

    public ArrayList<CarouselPicker.PickerItem> getSalmonHgSizes(){
        ArrayList<CarouselPicker.PickerItem> salmonSizes = new ArrayList<>();
        salmonSizes.add(new CarouselPicker.TextItem("No Pref.", 8));
        salmonSizes.add(new CarouselPicker.TextItem("40+", 10));
        salmonSizes.add(new CarouselPicker.TextItem("60+", 10));
        salmonSizes.add(new CarouselPicker.TextItem("100+", 10));
        salmonSizes.add(new CarouselPicker.TextItem("150+", 10));
        return salmonSizes;
    }

    public ArrayList<CarouselPicker.PickerItem> getSalmonFilletSizes(){
        ArrayList<CarouselPicker.PickerItem> salmonSizes = new ArrayList<>();
        salmonSizes.add(new CarouselPicker.TextItem("No Pref.", 8));
        salmonSizes.add(new CarouselPicker.TextItem("10+", 10));
        salmonSizes.add(new CarouselPicker.TextItem("20+", 10));
        salmonSizes.add(new CarouselPicker.TextItem("30+", 10));
        salmonSizes.add(new CarouselPicker.TextItem("50+", 10));
        return salmonSizes;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<CarouselPicker.PickerItem> getRegions(){
        ArrayList<CarouselPicker.PickerItem> regionChoices = new ArrayList<>();
        regionChoices.add(new CarouselPicker.TextItem("No Pref.", 8));
        regionChoices.add(new CarouselPicker.TextItem("Caribbean", 8));
        regionChoices.add(new CarouselPicker.TextItem("Southeast Asia", 8));
        regionChoices.add(new CarouselPicker.TextItem("South America", 8));
        return regionChoices;
    }

    public ArrayList<CarouselPicker.PickerItem> getNoGrades(){
        ArrayList<CarouselPicker.PickerItem> regionChoices = new ArrayList<>();
        regionChoices.add(new CarouselPicker.TextItem("Ungraded", 8));
        return regionChoices;
    }

    public ArrayList<CarouselPicker.PickerItem> getNoSpecies(){
        ArrayList<CarouselPicker.PickerItem> regionChoices = new ArrayList<>();
        regionChoices.add(new CarouselPicker.TextItem("None", 8));
        return regionChoices;
    }
}
