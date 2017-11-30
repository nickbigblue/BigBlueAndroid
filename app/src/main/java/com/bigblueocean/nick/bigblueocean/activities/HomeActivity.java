package com.bigblueocean.nick.bigblueocean.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.bigblueocean.nick.bigblueocean.helpers.SelectionHelper;
import com.bigblueocean.nick.bigblueocean.fragments.NewsFragment;
import com.bigblueocean.nick.bigblueocean.fragments.OrderFragment;
import com.bigblueocean.nick.bigblueocean.fragments.ProdFragment;
import com.bigblueocean.nick.bigblueocean.helpers.ServerPost;
import com.bigblueocean.nick.bigblueocean.model.Category;
import com.bigblueocean.nick.bigblueocean.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.bigblueocean.nick.bigblueocean.model.News;
import com.bigblueocean.nick.bigblueocean.model.Product;
import com.google.gson.reflect.TypeToken;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import in.goodiebag.carouselpicker.CarouselPicker;

public class HomeActivity extends AppCompatActivity implements
        ProdFragment.OnListFragmentInteractionListener, OrderFragment.OnListFragmentInteractionListener,
        NewsFragment.OnListFragmentInteractionListener {

    private static ViewPagerAdapter homeViewPagerAdapter;
    private ViewPager homeViewPager;
    static FirebaseAuth homeAuthenticator = FirebaseAuth.getInstance();
    private static ArrayList<Product> currentOrder = new ArrayList<>();
    private static Product dummyProd = new Product();
    private TabLayout tabLayout;

    //METHODS FOR ACTIVITY LIFECYCLE AND FAB
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        homeAuthenticator.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    HomeActivity.this.finish();
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                }
            }
        });

        Toolbar homeToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(homeToolbar);

        homeViewPager = (ViewPager) findViewById(R.id.container);
        homeViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeViewPager.setAdapter(homeViewPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(homeViewPager, false);

        tabLayout.getTabAt(0).setIcon(R.drawable.news);
        tabLayout.getTabAt(1).setIcon(R.drawable.add_item);
        tabLayout.getTabAt(2).setIcon(R.drawable.list);

        if (this.getIntent().hasExtra("currentItem"))
            homeViewPager.setCurrentItem(Integer.parseInt(this.getIntent().getStringExtra("currentItem")));
        else
            homeViewPager.setCurrentItem(1);
        homeViewPager.setOffscreenPageLimit(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();

        if (!(homeViewPagerAdapter == null)) {
            homeViewPagerAdapter.notifyDataSetChanged();
        }

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        Type type = new TypeToken<List<Product>>(){}.getType();
        String json = appSharedPrefs.getString("CurrentOrder", "");
        currentOrder = gson.fromJson(json, type);
        if (currentOrder == null){
            currentOrder = new ArrayList<>();
        }
    }

    @Override
    public void onStop(){
        super.onStop();

    }

    @Override
    public void onPause(){
        super.onPause();


        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(currentOrder);
        prefsEditor.putString("CurrentOrder", json);
        prefsEditor.putString("CurrentFragment", Integer.toString(homeViewPager.getCurrentItem()));
        prefsEditor.commit();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public static ArrayList<Product> getCurrentOrder(){
        return currentOrder;
    }

    //METHOD FOR MENU OPTIONS
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_home_menu:
                FirebaseAuth.getInstance().signOut();
                this.finish();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //METHODS FOR FRAGMENTS IN TABBED ACTIVITY
    @Override
    public void onListFragmentInteraction(News item){
        if (hasWindowFocus()) {
            Intent toNewsPage = new Intent(this, NewsActivity.class);
            toNewsPage.putExtra("newsID", "" + item.getNewsID());
            toNewsPage.putExtra("selectedNews", new Gson().toJson(item));
            startActivity(toNewsPage);
        }
    }

    @Override
    public void onListFragmentInteraction(Product item) {
        if (hasWindowFocus()){
            Dialog input = editProductDialog(item);
            int width = (int)(getResources().getDisplayMetrics().widthPixels*0.999);
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            input.getWindow().setLayout(width, height);
            input.show();
        }
    }

    @Override
    public void onListFragmentInteraction(Category item){
        if (hasWindowFocus()) {
            Dialog input = addItemDialog(item);
            int width = (int)(getResources().getDisplayMetrics().widthPixels*0.999);
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            input.getWindow().setLayout(width, height);
            input.show();
        }
    }

    //METHODS FOR DIALOGS
    public Dialog addItemDialog(final Category cat){
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.add_product_selection_dialog);
        dummyProd = new Product();
        dummyProd.setCategory(cat);
        dummyProd.setRegion("N/P");
        dummyProd.setSpecies("N/P");
        dummyProd.setGrade("N/P");
        dummyProd.setSize("N/P");
        dummyProd.setPrice("0.00");
        dummyProd.setQuantity("0");

        ImageView iv = dialog.findViewById(R.id.dialog_image);
        iv.setBackgroundColor(Color.parseColor(cat.getColor()));

        final EditText qtyField = dialog.findViewById(R.id.dialog_edit_text2);
        qtyField.setTextColor(Color.parseColor(cat.getColor()));
        qtyField.setVisibility(View.INVISIBLE);

        final EditText priceField = dialog.findViewById(R.id.dialog_edit_text1);
        priceField.setTextColor(Color.parseColor(cat.getColor()));
        priceField.setVisibility(View.INVISIBLE);

        ////MAIN PICKER
        final ArrayList<CarouselPicker> allPickers = setSelections(cat, dialog);
        final CarouselPicker dialogPicker1 = allPickers.get(0);
        final CarouselPicker dialogPicker2 = allPickers.get(1);
        final CarouselPicker dialogPicker3 = allPickers.get(2);
        final CarouselPicker dialogPicker4 = allPickers.get(3);

//
//        mainCarouselPicker.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                String s = mainPickerItem.get(position).getText();
//                if (!qtyField.getText().toString().isEmpty() &&
//                        !qtyField.getText().toString().equalsIgnoreCase(dummyProd.getQuantity()))
//                    dummyProd.setQuantity(qtyField.getText().toString());
//                if (!priceField.getText().toString().isEmpty() &&
//                        !priceField.getText().toString().equalsIgnoreCase(dummyProd.getPrice()))
//                    dummyProd.setPrice(priceField.getText().toString());
//
//                if (s.equalsIgnoreCase("Quantity")){
//                    subPickerItem = new ArrayList<>();
//                    infoLabel.setText(R.string.quantity_prompt);
//                    infoLabel.setVisibility(View.VISIBLE);
//                    qtyField.setVisibility(View.VISIBLE);
//                    qtyField.setEnabled(true);
//                    priceField.setVisibility(View.INVISIBLE);
//                    priceField.setEnabled(false);
//                }
//                else if (s.equalsIgnoreCase("($.$$)")){
//                    subPickerItem = new ArrayList<>();
//                    infoLabel.setText(R.string.price_prompt);
//                    infoLabel.setVisibility(View.VISIBLE);
//                    priceField.setVisibility(View.VISIBLE);
//                    priceField.setEnabled(true);
//                    qtyField.setVisibility(View.INVISIBLE);
//                    qtyField.setEnabled(false);
//                }
//                else {
//                    subPickerItem = getSelections(cat,s);
//                    infoLabel.setVisibility(View.INVISIBLE);
//                    qtyField.setVisibility(View.INVISIBLE);
//                    qtyField.setEnabled(false);
//                    priceField.setVisibility(View.INVISIBLE);
//                    priceField.setEnabled(false);
//                }
//                CarouselPicker.CarouselViewAdapter subTextAdapter;
//                subTextAdapter = new CarouselPicker.CarouselViewAdapter(getApplicationContext(), subPickerItem, 0);
//                subCarouselPicker.setAdapter(subTextAdapter);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
//
//
//        subCarouselPicker.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                String s = mainPickerItem.get(mainCarouselPicker.getCurrentItem()).getText();
//                switch(s){
//                    case "Region":
//                        dummyProd.setRegion(subPickerItem.get(position).getText());
//                        break;
//                    case "Species":
//                        dummyProd.setSpecies(subPickerItem.get(position).getText());
//                        break;
//                    case "Size":
//                        dummyProd.setSize(subPickerItem.get(position).getText());
//                        break;
//                    case "Grade":
//                        dummyProd.setGrade(subPickerItem.get(position).getText());
//                        break;
//                    default:
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });

        Button dialogAddButton = (Button) dialog.findViewById(R.id.dialog_add_button);
        dialogAddButton.setTextColor(Color.parseColor(cat.getColor()));
        dialogAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentOrder.size() <= 50){
                    if (!qtyField.getText().toString().isEmpty())
                        dummyProd.setQuantity(qtyField.getText().toString());
                    if (!priceField.getText().toString().isEmpty())
                        dummyProd.setPrice(priceField.getText().toString());
                    currentOrder.add(dummyProd);
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "You've reached your order maximum.", Toast.LENGTH_SHORT).show();
                }
                dialog.cancel();
                homeViewPagerAdapter.notifyDataSetChanged();
            }
        });

        Button dialogCancelButton = (Button) dialog.findViewById(R.id.dialog_cancel_button);
        dialogCancelButton.setTextColor(Color.parseColor(cat.getColor()));
        dialogCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.999);
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        return dialog;
    }

    public Dialog editProductDialog(final Product prod){
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.add_product_selection_dialog);
        dummyProd = new Product();
        dummyProd.setCategory(prod.getCategory());
        dummyProd.setRegion(prod.getRegion());
        dummyProd.setSpecies(prod.getSpecies());
        dummyProd.setGrade(prod.getGrade());
        dummyProd.setSize(prod.getSize());
        dummyProd.setPrice(prod.getPrice());
        dummyProd.setQuantity(prod.getQuantity());

        ImageView iv = dialog.findViewById(R.id.dialog_image);
        iv.setBackgroundColor(Color.parseColor(prod.getCategory().getColor()));

        final EditText qtyField = dialog.findViewById(R.id.dialog_edit_text2);
        qtyField.setText(dummyProd.getQuantity());
        qtyField.setTextColor(Color.parseColor(prod.getCategory().getColor()));

        final EditText priceField = dialog.findViewById(R.id.dialog_edit_text1);
        priceField.setText(dummyProd.getPrice());
        priceField.setTextColor(Color.parseColor(prod.getCategory().getColor()));

//        ////MAIN PICKER
//        final SelectionHelper infoPasser = new SelectionHelper();
//        final CarouselPicker mainCarouselPicker = dialog.findViewById(R.id.dialog_main_carousel);
//        final CarouselPicker subCarouselPicker = dialog.findViewById(R.id.dialog_sub_carousel);
//        final String tag = prod.getCategory().getTag();
//        final ArrayList<CarouselPicker.PickerItem> mainPickerItem;
//        CarouselPicker.CarouselViewAdapter textAdapter;
//        switch (prod.getCategory()){
//            case TUNA:
//                mainPickerItem = infoPasser.getTunaSelections();
//                break;
//            case SWORD:
//                mainPickerItem = infoPasser.getSwordSelections();
//                break;
//            case MAHI:
//                mainPickerItem = infoPasser.getMahiSelections();
//                break;
//            case WAHOO:
//                mainPickerItem = infoPasser.getWahooSelections();
//                break;
//            case GROUPER:
//                mainPickerItem = infoPasser.getGrouperSelections();
//                break;
//            case SALMON:
//                mainPickerItem = infoPasser.getSalmonSelections();
//                break;
//            default:
//                //change for "other" selections
//                mainPickerItem = infoPasser.getTunaSelections();
//                break;
//
//        }
//
//        String s = mainPickerItem.get(0).getText();
//
//
//        ////SUBPICKER
//        if(s.equalsIgnoreCase("Quantity") || s.equalsIgnoreCase("($.$$)")){
//            subPickerItem = new ArrayList<>();
//        } else {
//            subPickerItem = getSelections(prod.getCategory(),s);
//        }
//        CarouselPicker.CarouselViewAdapter subTextAdapter;
//        subTextAdapter = new CarouselPicker.CarouselViewAdapter(getApplicationContext(), subPickerItem, 0);
//        subCarouselPicker.setAdapter(subTextAdapter);
//
//        textAdapter = new CarouselPicker.CarouselViewAdapter(this, mainPickerItem, 0);
//        mainCarouselPicker.setAdapter(textAdapter);
//
//        mainCarouselPicker.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                String s = mainPickerItem.get(position).getText();
//
//                if (!qtyField.getText().toString().isEmpty() &&
//                        !qtyField.getText().toString().equalsIgnoreCase(dummyProd.getQuantity()))
//                    dummyProd.setQuantity(qtyField.getText().toString());
//                if (!priceField.getText().toString().isEmpty() &&
//                        !priceField.getText().toString().equalsIgnoreCase(dummyProd.getPrice()))
//                    dummyProd.setPrice(priceField.getText().toString());
//
//                if (s.equalsIgnoreCase("Quantity")){
//                    subPickerItem = new ArrayList<>();
//                    infoLabel.setText(R.string.quantity_prompt);
//                    infoLabel.setVisibility(View.VISIBLE);
//                    qtyField.setVisibility(View.VISIBLE);
//                    qtyField.setEnabled(true);
//                    priceField.setVisibility(View.INVISIBLE);
//                    priceField.setEnabled(false);
//                }
//                else if (s.equalsIgnoreCase("($.$$)")){
//                    subPickerItem = new ArrayList<>();
//                    infoLabel.setText(R.string.price_prompt);
//                    infoLabel.setVisibility(View.VISIBLE);
//                    priceField.setVisibility(View.VISIBLE);
//                    priceField.setEnabled(true);
//                    qtyField.setVisibility(View.INVISIBLE);
//                    qtyField.setEnabled(false);
//                }
//                else {
//                    subPickerItem = getSelections(prod.getCategory(),s);
//                    infoLabel.setVisibility(View.INVISIBLE);
//                    qtyField.setVisibility(View.INVISIBLE);
//                    qtyField.setEnabled(false);
//                    priceField.setVisibility(View.INVISIBLE);
//                    priceField.setEnabled(false);
//                }
//                CarouselPicker.CarouselViewAdapter subTextAdapter;
//                subTextAdapter = new CarouselPicker.CarouselViewAdapter(getApplicationContext(), subPickerItem, 0);
//                subCarouselPicker.setAdapter(subTextAdapter);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
//
//
//        subCarouselPicker.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                String s = mainPickerItem.get(mainCarouselPicker.getCurrentItem()).getText();
//                switch(s){
//                    case "Region":
//                        dummyProd.setRegion(subPickerItem.get(position).getText());
//                        break;
//                    case "Species":
//                        dummyProd.setSpecies(subPickerItem.get(position).getText());
//                        break;
//                    case "Size":
//                        dummyProd.setSize(subPickerItem.get(position).getText());
//                        break;
//                    case "Grade":
//                        dummyProd.setGrade(subPickerItem.get(position).getText());
//                        break;
//                    default:
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
//
//
//        Button editDialogConfirmButton = (Button) dialog.findViewById(R.id.dialog_add_button);
//        editDialogConfirmButton.setText(R.string.dialog_confirm_changes);
//        editDialogConfirmButton.setTextColor(Color.parseColor(prod.getCategory().getColor()));
//        editDialogConfirmButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dummyProd.setQuantity(qtyField.getText().toString());
//                dummyProd.setPrice(priceField.getText().toString());
//                currentOrder.set(currentOrder.indexOf(prod), dummyProd);
//                dialog.cancel();
//                homeViewPagerAdapter.notifyDataSetChanged();
//            }
//        });
//
//        Button editDialogDeleteButton = (Button) dialog.findViewById(R.id.dialog_cancel_button);
//        editDialogDeleteButton.setText(R.string.dialog_delete);
//        editDialogDeleteButton.setTextColor(Color.parseColor(prod.getCategory().getColor()));
//        editDialogDeleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                currentOrder.remove(prod);
//                homeViewPagerAdapter.notifyDataSetChanged();
//                dialog.cancel();
//            }
//        });

        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.999);
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        return dialog;
    }

    public ArrayList<CarouselPicker> setSelections(Category cat, Dialog d){
        ArrayList<CarouselPicker> temp = new ArrayList<>(4);
        SelectionHelper S1 = new SelectionHelper();
        final CarouselPicker dialogPicker1 = d.findViewById(R.id.dialog_carousel1);
        final CarouselPicker dialogPicker2 = d.findViewById(R.id.dialog_carousel2);
        final CarouselPicker dialogPicker3 = d.findViewById(R.id.dialog_carousel3);
        final CarouselPicker dialogPicker4 = d.findViewById(R.id.dialog_carousel4);
        final SelectionHelper infoPasser = new SelectionHelper();
        CarouselPicker.CarouselViewAdapter regions = new CarouselPicker.CarouselViewAdapter(this, null, 0);
        CarouselPicker.CarouselViewAdapter species = new CarouselPicker.CarouselViewAdapter(this, null, 0);
        CarouselPicker.CarouselViewAdapter grades = new CarouselPicker.CarouselViewAdapter(this, null, 0);
        CarouselPicker.CarouselViewAdapter sizes = new CarouselPicker.CarouselViewAdapter(this, null, 0);
        switch (cat){
                    case TUNA:
                        regions = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getRegions(), 0);
                        species = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getTunaSpecies(), 0);
                        grades = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getTunaGrades(), 0);
                        sizes = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getTunaSizes(), 0);
                        break;
                    case SWORD:
                        regions = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getRegions(), 0);
                        species = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getNoSpecies(), 0);
                        grades = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getSwordGrades(), 0);
                        sizes = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getSwordSizes(), 0);
                        break;
                    case MAHI:
                        regions = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getRegions(), 0);
                        species = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getNoSpecies(), 0);
                        grades = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getNoGrades(), 0);
                        sizes = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getMahiSizes(), 0);
                        break;
                    case WAHOO:
                        regions = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getRegions(), 0);
                        species = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getNoSpecies(), 0);
                        grades = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getNoGrades(), 0);
                        break;
                    case SALMON:
                        regions = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getRegions(), 0);
                        species = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getSalmonSpecies(), 0);
                        grades = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getSwordGrades(), 0);
                        sizes = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getSwordSizes(), 0);
                        break;
                    case OTHER:
                        regions = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getRegions(), 0);
                        species = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getNoSpecies(), 0);
                        grades = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getNoGrades(), 0);
                        sizes = new CarouselPicker.CarouselViewAdapter(this, infoPasser.getTunaSizes(), 0);
                        break;
                    default:
                }
        dialogPicker1.setAdapter(regions);
        dialogPicker2.setAdapter(species);
        dialogPicker3.setAdapter(grades);
        dialogPicker4.setAdapter(sizes);
        temp.add(dialogPicker1);
        temp.add(dialogPicker2);
        temp.add(dialogPicker3);
        temp.add(dialogPicker4);
        return temp;
    }

    //METHODS FOR MANAGING CURRENT ORDER
    public void clearOrder(){
        if(!currentOrder.isEmpty()) {
            final SweetAlertDialog clearOrderDialog =
                    new SweetAlertDialog(HomeActivity.this, SweetAlertDialog.WARNING_TYPE);
            clearOrderDialog.setTitleText(getResources().getString(R.string.clear_dialog_title));
            clearOrderDialog.setContentText(getResources().getString(R.string.clear_dialog_message));
            clearOrderDialog.setConfirmText("Clear");
            clearOrderDialog.setCancelText("Cancel");
            clearOrderDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    clearOrderDialog.dismissWithAnimation();
                    currentOrder.clear();
                    homeViewPagerAdapter.notifyDataSetChanged();
                }
            });
            clearOrderDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    clearOrderDialog.dismissWithAnimation();
                }
            });
            clearOrderDialog.show();
        }
    }

    public void submitOrder(){
        if(!currentOrder.isEmpty()) {
            final SweetAlertDialog submitOrderDialog =
                    new SweetAlertDialog(HomeActivity.this, SweetAlertDialog.WARNING_TYPE);
            submitOrderDialog.setTitleText(getResources().getString(R.string.submit_dialog_title));
            submitOrderDialog.setContentText(getResources().getString(R.string.submit_dialog_message));
            submitOrderDialog.setConfirmText("Submit");
            submitOrderDialog.setCancelText("Cancel");
            submitOrderDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    boolean success = sendOrder(currentOrder);
                    if (success) {
                        submitOrderDialog.dismissWithAnimation();
                        currentOrder.clear();
                        homeViewPagerAdapter.notifyDataSetChanged();
                        SweetAlertDialog confirmOrderSubmitDialog =
                                new SweetAlertDialog(HomeActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                        confirmOrderSubmitDialog.setTitleText("Order Submission");
                        confirmOrderSubmitDialog.setContentText(getResources().getString(R.string.submission_confirmed));
                        confirmOrderSubmitDialog.setConfirmText("OK");
                        confirmOrderSubmitDialog.show();
                    } else {
                        submitOrderDialog.dismissWithAnimation();
                        SweetAlertDialog failedOrderSubmitDialog =
                                new SweetAlertDialog(HomeActivity.this, SweetAlertDialog.ERROR_TYPE);
                        failedOrderSubmitDialog.setTitleText("Order Submission");
                        failedOrderSubmitDialog.setContentText(getResources().getString(R.string.submission_notconfirmed));
                        failedOrderSubmitDialog.setConfirmText("OK");
                        failedOrderSubmitDialog.show();
                    }
                }
            });
            submitOrderDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    submitOrderDialog.dismissWithAnimation();
                }
            });
            submitOrderDialog.show();
        }
    }

    //BIGBLUE DATABASE METHODS
    public boolean sendOrder(ArrayList<Product> order){
        String json = new Gson().toJson(order);
        String[] params = {getResources().getString(R.string.order_tag), json};
        ServerPost sp = new ServerPost(params, homeAuthenticator.getCurrentUser());
        return  sp.getSuccess();
    }
    public static FirebaseUser getFirebaseUser(){
        return homeAuthenticator.getCurrentUser();
    }
    //CUSTOMIZED PAGER ADAPTER FOR TABBED ACTIVITY FUNCTION
    public static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private String tabTitles[] = new String[] { "News", "Products", "My Order", "Chat" };

        public ViewPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment intendedFragment;
            switch (position){
                case 0:
                    intendedFragment = NewsFragment.newInstance();
                    break;
                case 1:
                    intendedFragment = ProdFragment.newInstance();
                    break;
                case 2:
                    intendedFragment = OrderFragment.newInstance();
                    break;
                default:
                    intendedFragment = ProdFragment.newInstance();
                    break;
            }
            return intendedFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

    };
}
