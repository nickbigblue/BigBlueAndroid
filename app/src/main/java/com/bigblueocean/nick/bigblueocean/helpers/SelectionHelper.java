package com.bigblueocean.nick.bigblueocean.helpers;

import java.util.ArrayList;

/**
 * Created by nick on 11/6/17.
 */

public class SelectionHelper {

    public ArrayList<ArrayList<String>> getTuna(){
        ArrayList<ArrayList<String>> tuna = new ArrayList<>();
        ArrayList<String> regions = getRegions();
        ArrayList<String> species = new ArrayList<>();
        species.add("No Pref.");
        species.add("Yellow Fin");
        species.add("Big Eye");
        ArrayList<String> grades = new ArrayList<>();
        grades.add("No Pref.");
        grades.add("1+");
        grades.add("1");
        grades.add("2+");
        grades.add("2");
        ArrayList<String> sizes = new ArrayList<>();
        sizes.add("No Pref.");
        sizes.add("150+");
        sizes.add("100+");
        sizes.add("60+");
        sizes.add("40+");
        tuna.add(regions);
        tuna.add(species);
        tuna.add(grades);
        tuna.add(sizes);
        return tuna;
    }

    public ArrayList<ArrayList<String>> getSword(){
        ArrayList<ArrayList<String>> sword = new ArrayList<>();
        ArrayList<String> regions = getRegions();
        ArrayList<String> species = getNoSpecies();
        ArrayList<String> grades = new ArrayList<>();
        grades.add("No Pref.");
        grades.add("R+");
        grades.add("R");
        grades.add("R-");
        grades.add("BR");
        ArrayList<String> sizes = new ArrayList<>();
        sizes.add("No Pref.");
        sizes.add("150+");
        sizes.add("100+");
        sizes.add("60+");
        sizes.add("40+");
        sword.add(regions);
        sword.add(species);
        sword.add(grades);
        sword.add(sizes);
        return sword;
    }

    public ArrayList<ArrayList<String>> getMahi(){
        ArrayList<ArrayList<String>> mahi = new ArrayList<>();
        ArrayList<String> regions = getRegions();
        ArrayList<String> species = getNoSpecies();
        ArrayList<String> grades = getNoGrades();
        ArrayList<String> sizes = new ArrayList<>();
        sizes.add("No Pref.");
        sizes.add("150+");
        sizes.add("100+");
        sizes.add("60+");
        sizes.add("40+");
        mahi.add(regions);
        mahi.add(species);
        mahi.add(grades);
        mahi.add(sizes);
        return mahi;
    }

    public ArrayList<ArrayList<String>> getWahoo(){
        ArrayList<ArrayList<String>> wahoo = new ArrayList<>();
        ArrayList<String> regions = getRegions();
        ArrayList<String> species = getNoSpecies();
        ArrayList<String> grades = getNoGrades();
        ArrayList<String> sizes = new ArrayList<>();
        sizes.add("No Pref.");
        sizes.add("150+");
        sizes.add("100+");
        sizes.add("60+");
        sizes.add("40+");
        wahoo.add(regions);
        wahoo.add(species);
        wahoo.add(grades);
        wahoo.add(sizes);
        return wahoo;
    }

    public ArrayList<ArrayList<String>> getGrouper(){
        ArrayList<ArrayList<String>> grouper = new ArrayList<>();
        ArrayList<String> regions = getRegions();
        ArrayList<String> species = new ArrayList<>();;
        ArrayList<String> grades = getNoGrades();
        ArrayList<String> sizes = new ArrayList<>();
        sizes.add("No Pref.");
        sizes.add("150+");
        sizes.add("100+");
        sizes.add("60+");
        sizes.add("40+");
        grouper.add(regions);
        grouper.add(species);
        grouper.add(grades);
        grouper.add(sizes);
        return grouper;
    }

    public ArrayList<ArrayList<String>> getSalmon(){
        ArrayList<ArrayList<String>> salmon = new ArrayList<>();
        ArrayList<String> regions = getRegions();
        ArrayList<String> species = new ArrayList<>();
        species.add("No Pref.");
        species.add("H&G");
        species.add("Fillet");
        ArrayList<String> grades = getNoGrades();
        ArrayList<String> sizes = new ArrayList<>();
        sizes.add("No Pref.");
        sizes.add("150+");
        sizes.add("100+");
        sizes.add("60+");
        sizes.add("40+");
        salmon.add(regions);
        salmon.add(species);
        salmon.add(grades);
        salmon.add(sizes);
        return salmon;
    }

    public ArrayList<ArrayList<String>> getOther(){
        ArrayList<ArrayList<String>> other = new ArrayList<>();
        ArrayList<String> regions = getRegions();
        ArrayList<String> species = getNoSpecies();
        ArrayList<String> grades = getNoGrades();
        ArrayList<String> sizes = getNoSizes();
        other.add(regions);
        other.add(species);
        other.add(grades);
        other.add(sizes);
        return other;
    }

    public ArrayList<String> getRegions(){
        ArrayList<String> regionChoices = new ArrayList<>();
        regionChoices.add("No Pref.");
        regionChoices.add("Caribbean");
        regionChoices.add("Southeast Asia");
        regionChoices.add("South America");
        return regionChoices;
    }

    public ArrayList<String> getNoGrades(){
        ArrayList<String> regionChoices = new ArrayList<>();
        regionChoices.add("Ungraded");
        return regionChoices;
    }

    public ArrayList<String> getNoSpecies(){
        ArrayList<String> regionChoices = new ArrayList<>();
        regionChoices.add("None");
        return regionChoices;
    }

    public ArrayList<String> getNoSizes(){
        ArrayList<String> sizeChoices = new ArrayList<>();
        sizeChoices.add("None");
        return sizeChoices;
    }

}
