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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.bigblueocean.nick.bigblueocean.model.News;
import com.bigblueocean.nick.bigblueocean.model.Product;
import com.google.gson.reflect.TypeToken;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

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
        setContentView(R.layout.activity_home);
        homeAuthenticator.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseMessaging.getInstance().subscribeToTopic("global");
                String instanceId = FirebaseInstanceId.getInstance().getToken();
                Log.e("instanceid",instanceId);
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

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_news);
//        tabLayout.getTabAt(1).setIcon(R.drawable.ic_add_item);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_list);

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
        dialog.setContentView(R.layout.dialog_add_product);
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

        final EditText qtyField = dialog.findViewById(R.id.dialog_edit_text1);
        qtyField.setTextColor(Color.parseColor(cat.getColor()));

        final EditText priceField = dialog.findViewById(R.id.dialog_edit_text2);
        priceField.setTextColor(Color.parseColor(cat.getColor()));

        SelectionHelper infoPicker = new SelectionHelper();
        ArrayList<ArrayList<String>> arrayChoices = new ArrayList<>();
        switch(cat){
            case TUNA:
                arrayChoices = infoPicker.getTuna();
                break;
            case SWORD:
                arrayChoices = infoPicker.getSword();
                break;
            case MAHI:
                arrayChoices = infoPicker.getMahi();
                break;
            case WAHOO:
                arrayChoices = infoPicker.getWahoo();
                break;
            case GROUPER:
                arrayChoices = infoPicker.getGrouper();
                break;
            case SALMON:
                arrayChoices = infoPicker.getSalmon();
                break;
            case OTHER:
                arrayChoices = infoPicker.getOther();
                break;
        }

        Spinner regions = dialog.findViewById(R.id.spinner1);
        ArrayAdapter<String> regionSpinnerAdapter =
                new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, arrayChoices.get(0));
        regionSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regions.setAdapter(regionSpinnerAdapter);
        regions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getItemAtPosition(position).toString().equalsIgnoreCase("No Pref."))
                dummyProd.setRegion(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dummyProd.setRegion("N/P");
            }
        });

        Spinner species = dialog.findViewById(R.id.spinner2);
        ArrayAdapter<String> speciesSpinnerAdapter =
                new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, arrayChoices.get(1));
        speciesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        species.setAdapter(speciesSpinnerAdapter);
        species.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getItemAtPosition(position).toString().equalsIgnoreCase("No Pref."))
                dummyProd.setSpecies(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dummyProd.setSpecies("");
            }
        });

        Spinner grades = dialog.findViewById(R.id.spinner3);
        ArrayAdapter<String> gradesSpinnerAdapter =
                new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, arrayChoices.get(2));
        gradesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grades.setAdapter(gradesSpinnerAdapter);
        grades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getItemAtPosition(position).toString().equalsIgnoreCase("No Pref."))
                dummyProd.setGrade(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dummyProd.setGrade("N/P");
            }
        });

        Spinner sizes = dialog.findViewById(R.id.spinner4);
        ArrayAdapter<String> sizesSpinnerAdapter =
                new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, arrayChoices.get(3));
        sizesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizes.setAdapter(sizesSpinnerAdapter);
        sizes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getItemAtPosition(position).toString().equalsIgnoreCase("No Pref."))
                dummyProd.setSize(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dummyProd.setSize("N/P");
            }
        });

        Button dialogAddButton = dialog.findViewById(R.id.dialog_add_button);
        dialogAddButton.setText(getResources().getString(R.string.dialog_add_button));
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

        Button dialogCancelButton = dialog.findViewById(R.id.dialog_cancel_button);
        dialogCancelButton.setText(getResources().getString(R.string.dialog_cancel_button));
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
        dialog.setContentView(R.layout.dialog_add_product);
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

        final EditText qtyField = dialog.findViewById(R.id.dialog_edit_text1);
        qtyField.setText(dummyProd.getQuantity());
        qtyField.setTextColor(Color.parseColor(prod.getCategory().getColor()));

        final EditText priceField = dialog.findViewById(R.id.dialog_edit_text2);
        priceField.setText(dummyProd.getPrice());
        priceField.setTextColor(Color.parseColor(prod.getCategory().getColor()));

        SelectionHelper infoPicker = new SelectionHelper();
        ArrayList<ArrayList<String>> arrayChoices = new ArrayList<>();
        switch(prod.getCategory()){
            case TUNA:
                arrayChoices = infoPicker.getTuna();
                break;
            case SWORD:
                arrayChoices = infoPicker.getSword();
                break;
            case MAHI:
                arrayChoices = infoPicker.getMahi();
                break;
            case WAHOO:
                arrayChoices = infoPicker.getWahoo();
                break;
            case GROUPER:
                arrayChoices = infoPicker.getGrouper();
                break;
            case SALMON:
                arrayChoices = infoPicker.getSalmon();
                break;
            case OTHER:
                arrayChoices = infoPicker.getOther();
                break;
        }

        Spinner regions = dialog.findViewById(R.id.spinner1);
        ArrayAdapter<String> regionSpinnerAdapter =
                new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, arrayChoices.get(0));
        regionSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regions.setAdapter(regionSpinnerAdapter);
        regions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("No Pref."))
                dummyProd.setRegion(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dummyProd.setRegion(dummyProd.getRegion());
            }
        });

        Spinner species = dialog.findViewById(R.id.spinner2);
        ArrayAdapter<String> speciesSpinnerAdapter =
                new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, arrayChoices.get(1));
        speciesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        species.setAdapter(speciesSpinnerAdapter);
        species.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("No Pref."))
                dummyProd.setSpecies(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dummyProd.setSpecies(dummyProd.getSpecies());
            }
        });

        Spinner grades = dialog.findViewById(R.id.spinner3);
        ArrayAdapter<String> gradesSpinnerAdapter =
                new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, arrayChoices.get(2));
        gradesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grades.setAdapter(gradesSpinnerAdapter);
        grades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("No Pref."))
                dummyProd.setGrade(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dummyProd.setGrade(dummyProd.getGrade());
            }
        });

        Spinner sizes = dialog.findViewById(R.id.spinner4);
        ArrayAdapter<String> sizesSpinnerAdapter =
                new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, arrayChoices.get(3));
        sizesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizes.setAdapter(sizesSpinnerAdapter);
        sizes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("No Pref."))
                dummyProd.setSize(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dummyProd.setSize(dummyProd.getSize());
            }
        });

        Button dialogAddButton = dialog.findViewById(R.id.dialog_add_button);
        dialogAddButton.setText(getResources().getString(R.string.dialog_confirm_changes));
        dialogAddButton.setTextColor(Color.parseColor(prod.getCategory().getColor()));
        dialogAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentOrder.size() <= 50){
                    if (!qtyField.getText().toString().isEmpty())
                        dummyProd.setQuantity(qtyField.getText().toString());
                    if (!priceField.getText().toString().isEmpty())
                        dummyProd.setPrice(priceField.getText().toString());
                    currentOrder.set(currentOrder.indexOf(prod), dummyProd);
                    dialog.cancel();
                    homeViewPagerAdapter.notifyDataSetChanged();
                }
            }
        });

        Button dialogCancelButton = dialog.findViewById(R.id.dialog_cancel_button);
        dialogCancelButton.setText(getResources().getString(R.string.dialog_delete));
        dialogCancelButton.setTextColor(Color.parseColor(prod.getCategory().getColor()));
        dialogCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentOrder.remove(prod);
                dialog.cancel();
                homeViewPagerAdapter.notifyDataSetChanged();
            }
        });

        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.999);
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        return dialog;
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
        private String tabTitles[] = new String[] { "News", "What You Want", "My Order"};

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
