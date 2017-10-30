package com.bigblueocean.nick.bigblueocean;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bigblueocean.nick.bigblueocean.dummy.DummyContent;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import Model.Category;
import Model.News;
import Model.Product;

public class HomeActivity extends AppCompatActivity implements
ProdFragment.OnListFragmentInteractionListener, OrderFragment.OnListFragmentInteractionListener,
NewsFragment.OnListFragmentInteractionListener, ChatFragment.OnListFragmentInteractionListener {

    private FragmentStatePagerAdapter homeViewPagerAdapter;
    private ViewPager homeViewPager;
    private String helpString;
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
       // toolbarTitle.
        toolbarTitle.setTypeface(FontHelper.antonTypeface(this));
        setSupportActionBar(homeToolbar);

        homeViewPager = (ViewPager) findViewById(R.id.container);
        homeViewPager.setAdapter(new homeStatePagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(homeViewPager);
        setupFAB();

        homeViewPager.setCurrentItem(1);

        //currentOrder.add(0, new Product(new Category("", null, R.color.colorPrimary),"Placeholder","Pl","ace","hol", "der"));
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void setupFAB(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.home_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (homeViewPager.getCurrentItem()){
                    case 0:
                        helpString = getString(R.string.news_help);
                        break;
                    case 1:
                        helpString = getString(R.string.order_help);
                        break;
                    case 2:
                        helpString = getString(R.string.product_help);
                        break;
                    case 3:
                        helpString = getString(R.string.chat_help);
                        break;
                    default:
                        helpString = "Unable to recognize current tab...";
                        break;
                }

                Snackbar.make(view, helpString, Snackbar.LENGTH_LONG).setAction(null, null).show();
            }
        });
    }

//METHOD FOR TAB BAR MENU
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
//    @Override
//    public void onListFragmentInteraction(News item){
//
//    }

    @Override
    public void onListFragmentInteraction(Product item){
        editProductDialog(item).show();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item){

    }

    @Override
    public void onListFragmentInteraction(Category item){
         listItemDialogCreate(item).show();
    }

    public AlertDialog listItemDialogCreate(final Category cat){
        //Dialog creation
        AlertDialog listItemDialog;
        AlertDialog.Builder listItemDialogBuilder;
        listItemDialogBuilder = new AlertDialog.Builder(HomeActivity.this, R.style.AlertDialogTheme);
        listItemDialogBuilder.setMessage("For testing the pass of a confirmation to order tab");
        listItemDialogBuilder.setTitle("Adding a dummy item");
        listItemDialogBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Product dummyProd = new Product(cat, "Caribbean","1", "70+", "1000lbs", "5.00");
                currentOrder.add(dummyProd);
            }
        });
        listItemDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        listItemDialog = listItemDialogBuilder.create();
        return listItemDialog;
    }

    public AlertDialog editProductDialog(Product prod){
        AlertDialog listItemDialog;
        AlertDialog.Builder listItemDialogBuilder;
        listItemDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = View.inflate(this, R.layout.product_selected_dialog, null);
        view.setBackgroundColor(prod.getCategory().getColor());
        listItemDialogBuilder.setView(view);
        listItemDialog = listItemDialogBuilder.create();
        return listItemDialog;
    }

//PAGER ADAPTER FOR TABBED ACTIVITY FUNCTION
    public static class homeStatePagerAdapter extends FragmentPagerAdapter {

        public homeStatePagerAdapter(FragmentManager fragmentManager){
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
                    Log.e("case","DEF on click");
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

    public void logE(){
        Log.e("HVP",homeViewPager.getCurrentItem()+"");
    }

    public static ArrayList<Product> getCurrentOrder(){
        return currentOrder;
    }
}
