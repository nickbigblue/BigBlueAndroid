package com.bigblueocean.nick.bigblueocean.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigblueocean.nick.bigblueocean.Helpers.FontHelper;
import com.bigblueocean.nick.bigblueocean.Fragments.ChatFragment;
import com.bigblueocean.nick.bigblueocean.Fragments.NewsFragment;
import com.bigblueocean.nick.bigblueocean.Fragments.OrderFragment;
import com.bigblueocean.nick.bigblueocean.Fragments.ProdFragment;
import com.bigblueocean.nick.bigblueocean.R;
import com.bigblueocean.nick.bigblueocean.dummy.DummyContent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import java.util.ArrayList;
import com.bigblueocean.nick.bigblueocean.Model.Category;
import com.bigblueocean.nick.bigblueocean.Model.News;
import com.bigblueocean.nick.bigblueocean.Model.Product;
import in.goodiebag.carouselpicker.CarouselPicker;

public class HomeActivity extends AppCompatActivity implements
ProdFragment.OnListFragmentInteractionListener, OrderFragment.OnListFragmentInteractionListener,
NewsFragment.OnListFragmentInteractionListener, ChatFragment.OnListFragmentInteractionListener {

    private static FragmentStatePagerAdapter homeViewPagerAdapter;
    private ViewPager homeViewPager;
    private static ArrayList<Product> currentOrder = new ArrayList<>();

//METHODS FOR ACTIVITY LIFECYCLE AND FAB
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
        }

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

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            this.finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if (!(homeViewPagerAdapter == null)) {
            homeViewPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        for(Product element : currentOrder){
            int counter = 0;
            Gson gson = new Gson();
            String json = gson.toJson(element);
            prefsEditor.putString("Element"+counter, json);
            prefsEditor.commit();
            counter++;
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        for(Product element : currentOrder){
            int counter = 0;
            Gson gson = new Gson();
            String json = gson.toJson(element);
            prefsEditor.putString("Element"+counter, json);
            prefsEditor.commit();
            counter++;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        for(Product element : currentOrder){
            int counter = 0;
            Gson gson = new Gson();
            String json = gson.toJson(element);
            prefsEditor.putString("Element"+counter, json);
            prefsEditor.commit();
            counter++;
        }
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
    public void onListFragmentInteraction(News item){

    }

    @Override
    public void onListFragmentInteraction(Product item){
        editProductDialog(item).show();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item){

    }

    @Override
    public void onListFragmentInteraction(Category item){
         listItemDialog(item).show();
    }

//METHODS FOR DIALOGS
    public Dialog listItemDialog(final Category cat){
        final Dialog addProductDialog = new Dialog(HomeActivity.this);
        addProductDialog.setContentView(R.layout.add_product_selection_dialog);

        ImageView iv = new ImageView(this);
        iv = (ImageView) addProductDialog.findViewById(R.id.add_dialog_image);
        iv.setImageBitmap(cat.getImage());
        iv.setBackgroundColor(cat.getColor());

        CarouselPicker carouselPicker = (CarouselPicker) addProductDialog.findViewById(R.id.add_dialog_main_carousel);
        ArrayList<CarouselPicker.PickerItem> textItems = new ArrayList<>();
        textItems.add(new CarouselPicker.TextItem("Sp", 24));
        textItems.add(new CarouselPicker.TextItem("Rg", 24));
        textItems.add(new CarouselPicker.TextItem("Gr", 24));
        textItems.add(new CarouselPicker.TextItem("Sz", 24));
        textItems.add(new CarouselPicker.TextItem("Qty", 24));
        textItems.add(new CarouselPicker.TextItem("$.$$", 24));
        CarouselPicker.CarouselViewAdapter textAdapter = new CarouselPicker.CarouselViewAdapter(this, textItems, 0);
        carouselPicker.setAdapter(textAdapter);
//        carouselPicker.setCurrentItem(0);

        Button dialogAddButton = (Button) addProductDialog.findViewById(R.id.add_dialog_add_button);
        dialogAddButton.setTextColor(cat.getColor());
        dialogAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product dummyProd = new Product(cat, "Caribbean","1", "70+", "1000lbs", "5.00");
                if (currentOrder.size() <= 50){
                    currentOrder.add(dummyProd);
                }
                else {
                    Toast.makeText(getApplicationContext(), "You've reached your order maximum.", Toast.LENGTH_LONG).show();
                }
                addProductDialog.cancel();
                homeViewPagerAdapter.notifyDataSetChanged();
            }
        });

        Button dialogCancelButton = (Button) addProductDialog.findViewById(R.id.add_dialog_cancel_button);
        dialogCancelButton.setTextColor(cat.getColor());
        dialogCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductDialog.cancel();
            }
        });

        return addProductDialog;
    }

    public Dialog editProductDialog(final Product prod){
        final Product product = prod;
        final Dialog editProductDialog = new Dialog(HomeActivity.this);
        editProductDialog.setContentView(R.layout.edit_existing_product_dialog);

        ImageView iv = new ImageView(this);
        iv = (ImageView) editProductDialog.findViewById(R.id.edit_dialog_image);
        iv.setImageBitmap(prod.getCategory().getImage());
        iv.setBackgroundColor(prod.getCategory().getColor());

        CarouselPicker carouselPicker = (CarouselPicker) editProductDialog.findViewById(R.id.edit_dialog_main_carousel);
        ArrayList<CarouselPicker.PickerItem> textItems = new ArrayList<>();
        textItems.add(new CarouselPicker.TextItem("Sp", 24));
        textItems.add(new CarouselPicker.TextItem("Rg", 24));
        textItems.add(new CarouselPicker.TextItem("Gr", 24));
        textItems.add(new CarouselPicker.TextItem("Sz", 24));
        textItems.add(new CarouselPicker.TextItem("Qty", 24));
        textItems.add(new CarouselPicker.TextItem("$.$$", 24));
        CarouselPicker.CarouselViewAdapter textAdapter = new CarouselPicker.CarouselViewAdapter(this, textItems, 0);
        carouselPicker.setAdapter(textAdapter);
//        carouselPicker.setCurrentItem(0);

        Button editDialogConfirmButton = (Button) editProductDialog.findViewById(R.id.edit_dialog_change_button);
        editDialogConfirmButton.setTextColor(prod.getCategory().getColor());
        editDialogConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product dummyProd = new Product(prod.getCategory(), "Southeast Asia","1+", "100+", "500lbs", "8.00");
                currentOrder.set(currentOrder.indexOf(prod), dummyProd);
                editProductDialog.cancel();
                homeViewPagerAdapter.notifyDataSetChanged();
            }
        });

        Button editDialogDeleteButton = (Button) editProductDialog.findViewById(R.id.edit_dialog_delete_button);
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
            confirmOrderDialogBuilder.setTitle(R.string.clear_dialog_title);
            confirmOrderDialogBuilder.setMessage(R.string.clear_dialog_message);
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
