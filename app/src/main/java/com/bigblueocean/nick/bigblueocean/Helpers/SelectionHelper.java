package com.bigblueocean.nick.bigblueocean.Helpers;

import java.util.ArrayList;

import in.goodiebag.carouselpicker.CarouselPicker;

/**
 * Created by nick on 11/6/17.
 */

public class SelectionHelper {
    public ArrayList<CarouselPicker.PickerItem> getTunaSelections(){
        ArrayList<CarouselPicker.PickerItem> tunaSelections = new ArrayList<>();
        tunaSelections.add(new CarouselPicker.TextItem("Region", 14));
        tunaSelections.add(new CarouselPicker.TextItem("Grade", 14));
        tunaSelections.add(new CarouselPicker.TextItem("Species", 14));
        tunaSelections.add(new CarouselPicker.TextItem("Size", 14));
        tunaSelections.add(new CarouselPicker.TextItem("Quantity", 14));
        tunaSelections.add(new CarouselPicker.TextItem("($.$$)", 16));
        return tunaSelections;
    }

        public ArrayList<CarouselPicker.PickerItem> getTunaSpecies(){
            ArrayList<CarouselPicker.PickerItem> tunaSpecies = new ArrayList<>();
            tunaSpecies.add(new CarouselPicker.TextItem("Big Eye", 10));
            tunaSpecies.add(new CarouselPicker.TextItem("Yellow Fin", 10));
            tunaSpecies.add(new CarouselPicker.TextItem("Blue Fin", 10));
            return tunaSpecies;
        }

        public ArrayList<CarouselPicker.PickerItem> getRegions(){
            ArrayList<CarouselPicker.PickerItem> tunaRegions = new ArrayList<>();
            tunaRegions.add(new CarouselPicker.TextItem("Caribbean", 10));
            tunaRegions.add(new CarouselPicker.TextItem("Southeast Asia", 10));
            tunaRegions.add(new CarouselPicker.TextItem("South America", 10));
            return tunaRegions;
        }

        public ArrayList<CarouselPicker.PickerItem> getTunaGrades(){
            ArrayList<CarouselPicker.PickerItem> tunaGrades = new ArrayList<>();
            tunaGrades.add(new CarouselPicker.TextItem("2", 10));
            tunaGrades.add(new CarouselPicker.TextItem("2+", 10));
            tunaGrades.add(new CarouselPicker.TextItem("1", 10));
            tunaGrades.add(new CarouselPicker.TextItem("1+", 10));
            return tunaGrades;
        }

        public ArrayList<CarouselPicker.PickerItem> getTunaSizes(){
            ArrayList<CarouselPicker.PickerItem> tunaSizes = new ArrayList<>();
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

////////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<CarouselPicker.PickerItem> getMahiSelections(){
        ArrayList<CarouselPicker.PickerItem> mahiSelections = new ArrayList<>();
        mahiSelections.add(new CarouselPicker.TextItem("Region", 14));
        mahiSelections.add(new CarouselPicker.TextItem("Size", 14));
        mahiSelections.add(new CarouselPicker.TextItem("Quantity", 14));
        mahiSelections.add(new CarouselPicker.TextItem("($.$$)", 16));
        return mahiSelections;
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
}
