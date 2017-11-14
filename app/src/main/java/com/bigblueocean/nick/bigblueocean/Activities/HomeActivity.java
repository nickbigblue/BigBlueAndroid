package com.bigblueocean.nick.bigblueocean.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
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
import android.widget.TextView;
import android.widget.Toast;
import com.bigblueocean.nick.bigblueocean.Helpers.FontHelper;
import com.bigblueocean.nick.bigblueocean.Helpers.SelectionHelper;
import com.bigblueocean.nick.bigblueocean.Fragments.ChatFragment;
import com.bigblueocean.nick.bigblueocean.Fragments.NewsFragment;
import com.bigblueocean.nick.bigblueocean.Fragments.OrderFragment;
import com.bigblueocean.nick.bigblueocean.Fragments.ProdFragment;
import com.bigblueocean.nick.bigblueocean.R;
import com.bigblueocean.nick.bigblueocean.dummy.DummyContent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.bigblueocean.nick.bigblueocean.Model.Category;
import com.bigblueocean.nick.bigblueocean.Model.News;
import com.bigblueocean.nick.bigblueocean.Model.Product;
import com.google.gson.reflect.TypeToken;

import in.goodiebag.carouselpicker.CarouselPicker;

public class HomeActivity extends AppCompatActivity implements
ProdFragment.OnListFragmentInteractionListener, OrderFragment.OnListFragmentInteractionListener,
NewsFragment.OnListFragmentInteractionListener, ChatFragment.OnListFragmentInteractionListener {

    private static FragmentStatePagerAdapter homeViewPagerAdapter;
    private ViewPager homeViewPager;
    private static ArrayList<Product> currentOrder = new ArrayList<>();
    private static ArrayList<News> recentNews = NewsFragment.newInstance(1).getRecentNews();
    private static ArrayList<CarouselPicker.PickerItem> subPickerItem;
    public static String userEmail;
    public static Product dummyProd = new Product();

//METHODS FOR ACTIVITY LIFECYCLE AND FAB
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        FirebaseAuth homeAuthenticator = FirebaseAuth.getInstance();
        homeAuthenticator.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    HomeActivity.this.finish();
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                }
                else{
                    userEmail = user.getEmail();
                }
            }
        });

        Toolbar homeToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        TextView toolbarTitle = (TextView) homeToolbar.findViewById(R.id.toolbar_title);
        homeToolbar.setTitle("");
        toolbarTitle.setText(R.string.app_name_caps);
        toolbarTitle.setTypeface(FontHelper.antonTypeface(this));
        setSupportActionBar(homeToolbar);

        homeViewPager = (ViewPager) findViewById(R.id.container);
        homeViewPagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager());
        homeViewPager.setAdapter(homeViewPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(homeViewPager);

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
            case R.id.help_home_menu:
                startActivity(new Intent(this, HelpActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//METHODS FOR FRAGMENTS IN TABBED ACTIVITY
    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item){

    }

    @Override
    public void onListFragmentInteraction(News item){
        Intent toNewsPage = new Intent(this, NewsActivity.class);
        toNewsPage.putExtra("newsID", ""+item.getNewsID());
        toNewsPage.putExtra("selectedNews", new Gson().toJson(item));
        startActivity(toNewsPage);
    }

    @Override
    public void onListFragmentInteraction(Product item){
        editProductDialog(item).show();
    }

    @Override
    public void onListFragmentInteraction(Category item){
         listItemDialog(item).show();
    }

//METHODS FOR DIALOGS
    public Dialog listItemDialog(final Category cat){
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.add_product_selection_dialog);
        dummyProd.setCategory(cat);
        dummyProd.setPrice("0.00");
        dummyProd.setQuantity("100");


        ImageView iv = new ImageView(this);
        iv = (ImageView) dialog.findViewById(R.id.dialog_image);
        iv.setImageBitmap(BitmapFactory.decodeResource(dialog.getContext().getResources(), cat.getImage()));
        iv.setBackgroundColor(cat.getColor());

        EditText field = (EditText) dialog.findViewById(R.id.dialog_edit_text);
        field.setTextColor(cat.getColor());
        field.setVisibility(View.INVISIBLE);

        ////MAIN PICKER
        final SelectionHelper infoPasser = new SelectionHelper();
        final CarouselPicker mainCarouselPicker = (CarouselPicker) dialog.findViewById(R.id.dialog_main_carousel);
        final CarouselPicker subCarouselPicker = (CarouselPicker) dialog.findViewById(R.id.dialog_sub_carousel);
        final String tag = cat.getTag();
        final ArrayList<CarouselPicker.PickerItem> mainPickerItem;
        CarouselPicker.CarouselViewAdapter textAdapter;
        switch (cat.getTag()){
            case "Tuna":
                mainPickerItem = infoPasser.getTunaSelections();
                break;
            case "Sword":
                mainPickerItem = infoPasser.getSwordSelections();
                break;
            case "Mahi":
                mainPickerItem = infoPasser.getMahiSelections();
                break;
            case "Wahoo":
                mainPickerItem = infoPasser.getWahooSelections();
                break;
            case "Grouper":
                mainPickerItem = infoPasser.getGrouperSelections();
                break;
            case "Salmon":
                mainPickerItem = infoPasser.getSalmonSelections();
                break;
            default:
                mainPickerItem = infoPasser.getTunaSelections();
                break;

        }

        String s = mainPickerItem.get(0).getText();


        ////SUBPICKER
        if(s.equalsIgnoreCase("Quantity") || s.equalsIgnoreCase("($.$$)")){
            subPickerItem = new ArrayList<>();
        } else {
            subPickerItem = getSubPicker(tag,s);
        }
        CarouselPicker.CarouselViewAdapter subTextAdapter;
        subTextAdapter = new CarouselPicker.CarouselViewAdapter(getApplicationContext(), subPickerItem, 0);
        subCarouselPicker.setAdapter(subTextAdapter);

        textAdapter = new CarouselPicker.CarouselViewAdapter(this, mainPickerItem, 0);
        mainCarouselPicker.setAdapter(textAdapter);

        mainCarouselPicker.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String s = mainPickerItem.get(position).getText();
                if(s.equalsIgnoreCase("Quantity") || s.equalsIgnoreCase("($.$$)")){
                    subPickerItem = new ArrayList<>();
                    EditText field = dialog.findViewById(R.id.dialog_edit_text);
                    field.setVisibility(View.VISIBLE);
                } else {
                    subPickerItem = getSubPicker(tag,s);
                    EditText field = dialog.findViewById(R.id.dialog_edit_text);
                    field.setVisibility(View.INVISIBLE);
                }
                CarouselPicker.CarouselViewAdapter subTextAdapter;
                subTextAdapter = new CarouselPicker.CarouselViewAdapter(getApplicationContext(), subPickerItem, 0);
                subCarouselPicker.setAdapter(subTextAdapter);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        subCarouselPicker.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String s = mainPickerItem.get(position).getText();
                switch(s){
                    case "Region":
                        dummyProd.setRegion(subPickerItem.get(position).getText());
                        break;
                    case "Species":
                        dummyProd.setSpecies(subPickerItem.get(position).getText());
                        break;
                    case "Size":
                        dummyProd.setSize(subPickerItem.get(position).getText());
                        break;
                    case "Grade":
                        dummyProd.setGrade(subPickerItem.get(position).getText());
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        Button dialogAddButton = (Button) dialog.findViewById(R.id.dialog_add_button);
        dialogAddButton.setTextColor(cat.getColor());
        dialogAddButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (currentOrder.size() <= 50){
                currentOrder.add(dummyProd);
            }
            else {
                Toast.makeText(getApplicationContext(), "You've reached your order maximum.", Toast.LENGTH_SHORT).show();
            }
            dialog.cancel();
            homeViewPagerAdapter.notifyDataSetChanged();
        }
    });

    Button dialogCancelButton = (Button) dialog.findViewById(R.id.dialog_cancel_button);
    dialogCancelButton.setTextColor(cat.getColor());
    dialogCancelButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.cancel();
        }
    });

    return dialog;
}

    public Dialog editProductDialog(final Product prod){
        final Product product = prod;
        final Dialog editProductDialog = new Dialog(HomeActivity.this);
        editProductDialog.setContentView(R.layout.add_product_selection_dialog);

        ImageView iv = new ImageView(this);
        iv = (ImageView) editProductDialog.findViewById(R.id.dialog_image);
        iv.setImageBitmap(BitmapFactory.decodeResource(editProductDialog.getContext().getResources(), prod.getCategory().getImage()));
        iv.setBackgroundColor(prod.getCategory().getColor());

        ////MAIN PICKER
        final SelectionHelper infoPasser = new SelectionHelper();
        final CarouselPicker mainCarouselPicker = (CarouselPicker) editProductDialog.findViewById(R.id.dialog_main_carousel);
        final CarouselPicker subCarouselPicker = (CarouselPicker) editProductDialog.findViewById(R.id.dialog_sub_carousel);
        final String tag = prod.getCategory().getTag();
        final ArrayList<CarouselPicker.PickerItem> mainPickerItem;
        CarouselPicker.CarouselViewAdapter textAdapter;
        switch (tag){
            case "Tuna":
                mainPickerItem = infoPasser.getTunaSelections();
                break;
            case "Sword":
                mainPickerItem = infoPasser.getSwordSelections();
                break;
            case "Mahi":
                mainPickerItem = infoPasser.getMahiSelections();
                break;
            case "Wahoo":
                mainPickerItem = infoPasser.getWahooSelections();
                break;
            case "Grouper":
                mainPickerItem = infoPasser.getGrouperSelections();
                break;
            case "Salmon":
                mainPickerItem = infoPasser.getSalmonSelections();
                break;
            default:
                mainPickerItem = infoPasser.getTunaSelections();
                break;

        }

        String s = mainPickerItem.get(0).getText();


        ////SUBPICKER
        if(s.equalsIgnoreCase("Quantity") || s.equalsIgnoreCase("($.$$)")){
            subPickerItem = new ArrayList<>();
        } else {
            subPickerItem = getSubPicker(tag,s);
        }
        CarouselPicker.CarouselViewAdapter subTextAdapter;
        subTextAdapter = new CarouselPicker.CarouselViewAdapter(getApplicationContext(), subPickerItem, 0);
        subCarouselPicker.setAdapter(subTextAdapter);



        textAdapter = new CarouselPicker.CarouselViewAdapter(this, mainPickerItem, 0);
        mainCarouselPicker.setAdapter(textAdapter);

        mainCarouselPicker.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String s = mainPickerItem.get(position).getText();
                if(s.equalsIgnoreCase("Quantity") || s.equalsIgnoreCase("($.$$)")){
                    subPickerItem = new ArrayList<>();
                } else {
                    subPickerItem = getSubPicker(tag,s);
                }
                CarouselPicker.CarouselViewAdapter subTextAdapter;
                subTextAdapter = new CarouselPicker.CarouselViewAdapter(getApplicationContext(), subPickerItem, 0);
                subCarouselPicker.setAdapter(subTextAdapter);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        subCarouselPicker.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        Button editDialogConfirmButton = (Button) editProductDialog.findViewById(R.id.dialog_add_button);
        editDialogConfirmButton.setText(R.string.dialog_confirm_changes);
        editDialogConfirmButton.setTextColor(prod.getCategory().getColor());
        editDialogConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentOrder.set(currentOrder.indexOf(prod), dummyProd);
                editProductDialog.cancel();
                homeViewPagerAdapter.notifyDataSetChanged();
            }
        });

        Button editDialogDeleteButton = (Button) editProductDialog.findViewById(R.id.dialog_cancel_button);
        editDialogDeleteButton.setText(R.string.dialog_delete);
        editDialogDeleteButton.setTextColor(prod.getCategory().getColor());
        editDialogDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentOrder.remove(product);
                homeViewPagerAdapter.notifyDataSetChanged();
                editProductDialog.cancel();
            }
        });

        return editProductDialog;
    }

    public ArrayList<CarouselPicker.PickerItem> getSubPicker(String tag, String title){
        ArrayList<CarouselPicker.PickerItem> temp;
        SelectionHelper S1 = new SelectionHelper();
        switch (title){
            case "Grade":
                switch (tag){
                    case "Tuna":
                        temp = S1.getTunaGrades();
                        break;
                    case "Sword":
                        temp = S1.getSwordGrades();
                        break;
                    default:
                        temp = S1.getUngraded();
                }
                break;
            case "Species":
                switch (tag){
                    case "Tuna":
                        temp = S1.getTunaSpecies();
                        break;
                    case "Grouper":
                        temp = S1.getGrouperSpecies();
                        break;
                    case "Salmon":
                        temp = S1.getSalmonSpecies();
                        break;
                    default:
                        temp = S1.getTunaSpecies();
                }
                break;
            case "Region":
                temp = S1.getRegions();
                break;
            case "Size":
                switch (tag){
                    case "Tuna":
                        temp = S1.getTunaSizes();
                        break;
                    case "Sword":
                        temp = S1.getSwordSizes();
                        break;
                    case "Mahi":
                        temp = S1.getMahiSizes();
                        break;
                    case "Wahoo":
                        temp = S1.getWahooSizes();
                        break;
                    case "Grouper":
                        temp = S1.getGrouperSizes();
                        break;
                    case "Salmon":
                        if (dummyProd.getSpecies().equalsIgnoreCase("Fillet")){
                            temp = S1.getSalmonFilletSizes();
                        }
                        else if(dummyProd.getSpecies().equalsIgnoreCase("H&G")){
                            temp = S1.getSalmonHgSizes();
                        }
                        else{
                            temp = new ArrayList<CarouselPicker.PickerItem>();
                        }
                        break;
                    default:
                        temp = S1.getTunaSizes();
                }
                break;
            default:
                temp = S1.getTunaGrades();
                break;

        }
        return temp;
    }

//METHODS FOR MANAGING CURRENT ORDER
    public void clearOrder(){
        if(!currentOrder.isEmpty()) {
            final AlertDialog clearOrderDialog;
            AlertDialog.Builder clearOrderDialogBuilder;
            clearOrderDialogBuilder = new AlertDialog.Builder(HomeActivity.this, R.style.AlertDialogTheme);
            clearOrderDialogBuilder.setTitle(R.string.clear_dialog_title);
            clearOrderDialogBuilder.setMessage(R.string.clear_dialog_message);
            clearOrderDialogBuilder.setPositiveButton(R.string.dialog_continue, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    currentOrder.clear();
                    homeViewPagerAdapter.notifyDataSetChanged();
                }
            });
            clearOrderDialogBuilder.setNegativeButton(R.string.dialog_cancel_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            clearOrderDialog = clearOrderDialogBuilder.create();
            clearOrderDialog.show();
        }
    }

    public void submitOrder(){
        if(!currentOrder.isEmpty()) {
            final AlertDialog confirmOrderDialog;
            AlertDialog.Builder confirmOrderDialogBuilder;
            confirmOrderDialogBuilder = new AlertDialog.Builder(HomeActivity.this, R.style.AlertDialogTheme);
            confirmOrderDialogBuilder.setTitle(R.string.submit_dialog_title);
            confirmOrderDialogBuilder.setMessage(R.string.submit_dialog_message);
            confirmOrderDialogBuilder.setPositiveButton(R.string.dialog_continue, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //SEND currentOrder TO JASON, ASK FOR CONFIRMATION
                    //
                    //
                    //
                    boolean success = sendOrder();
                    if (success) {
                        currentOrder.clear();
                        homeViewPagerAdapter.notifyDataSetChanged();
                        Toast.makeText(HomeActivity.this,
                                R.string.submission_confirmed, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(HomeActivity.this,
                                R.string.submission_notconfirmed, Toast.LENGTH_LONG).show();
                    }
                }
            });
            confirmOrderDialogBuilder.setNegativeButton(R.string.dialog_cancel_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            confirmOrderDialog = confirmOrderDialogBuilder.create();
            confirmOrderDialog.show();
        }
    }

//BIGBLUE DATABASE METHODS
    public boolean sendOrder(){
        boolean confirmation =  true;

        //SEND DATA TO DATA BASE AND ASK FOR A CONFIRMATION SOMEHOW

        return confirmation;
    }

//CUSTOMIZED PAGER ADAPTER FOR TABBED ACTIVITY FUNCTION
    public static class FragmentStatePagerAdapter extends FragmentPagerAdapter {

        public FragmentStatePagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }
        @Override
        public Fragment getItem(int position) {
            Fragment intendedFragment;

            switch (position){
                case 0:
                    intendedFragment = NewsFragment.newInstance(1);
                    break;
                case 1:
                    intendedFragment = ProdFragment.newInstance(1);
                    break;
                case 2:
                    intendedFragment = OrderFragment.newInstance(1);
                    break;
                case 3:
                    intendedFragment = ChatFragment.newInstance(1);
                    break;
                default:
                    intendedFragment = ProdFragment.newInstance(1);
                    break;
            }
            return intendedFragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public int getItemPosition(Object object) {
        return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "News";
                case 1:
                    return "Products";
                case 2:
                    return "Order";

                case 3:
                    return "Chat";
            }
            return null;
        }
    };
}
