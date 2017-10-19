package com.bigblueocean.nick.bigblueocean;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter homeViewPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager homeViewPager;
    private String helpString;
    private final int tabCount = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        Toolbar homeToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        TextView toolbarTitle = (TextView) homeToolbar.findViewById(R.id.toolbar_title);
        homeToolbar.setTitle("");
        toolbarTitle.setText(R.string.app_name);
        toolbarTitle.setTypeface(Helper.impactTypeface(this));
        setSupportActionBar(homeToolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        homeViewPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        homeViewPager = (ViewPager) findViewById(R.id.container);
        homeViewPager.setAdapter(homeViewPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(homeViewPager);

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

        homeViewPager.setCurrentItem(1);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signout:
                FirebaseAuth.getInstance().signOut();
                this.finish();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            case R.id.help:
                startActivity(new Intent(this, HelpActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

        /**
         * A placeholder fragment containing a simple view.
         */
        public static class PlaceholderFragment extends Fragment {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */

            private static final String ARG_SECTION_NUMBER = "section_number";

            public PlaceholderFragment() {
            }

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            public static PlaceholderFragment newInstance(int sectionNumber) {
                PlaceholderFragment fragment = new PlaceholderFragment();
                Bundle args = new Bundle();
                args.putInt(ARG_SECTION_NUMBER, sectionNumber);
                fragment.setArguments(args);
                return fragment;
            }

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.fragment_home, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                return rootView;
            }
        }

        /**
         * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
         * one of the sections/tabs/pages.
         */
        public class SectionsPagerAdapter extends FragmentPagerAdapter {

            public SectionsPagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {
                // getItem is called to instantiate the fragment for the given page.
                // Return a PlaceholderFragment (defined as a static inner class below).
                return PlaceholderFragment.newInstance(position + 1);
            }

            @Override
            public int getCount() {
                return tabCount;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "News";
                    case 1:
                        return "Order";
                    case 2:
                        return "Products";
                    case 3:
                        return "Chat";
                }
                return null;
            }
        }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
