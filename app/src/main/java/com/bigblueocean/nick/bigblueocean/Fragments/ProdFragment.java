package com.bigblueocean.nick.bigblueocean.Fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigblueocean.nick.bigblueocean.Adapters.ProdViewAdapter;
import com.bigblueocean.nick.bigblueocean.R;

import java.util.ArrayList;

import com.bigblueocean.nick.bigblueocean.Model.Category;

public class ProdFragment extends Fragment {

    private static int categoriesColumnCount;
    private OnListFragmentInteractionListener prodInteractionListener;

    public ProdFragment() {
    }

    public static ProdFragment newInstance(int columnCount) {
        ProdFragment fragment = new ProdFragment();
        categoriesColumnCount = columnCount;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_fragment_list, container, false);

        // Set the adapter
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setAdapter(new ProdViewAdapter(categories(), prodInteractionListener));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            prodInteractionListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        prodInteractionListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Category item);
    }

    public ArrayList<Category> categories() {
        ArrayList<Category> CAL = new ArrayList<Category>();
        CAL.add(new Category("Tuna H&G Wild-Caught", R.drawable.tuna2x, getResources().getColor(R.color.tuna), "Tuna"));

        CAL.add(new Category("Sword H&G Wild-Caught", R.drawable.sword2x, getResources().getColor(R.color.sword), "Sword"));

        CAL.add(new Category("Mahi H&G Wild-Caught", R.drawable.mahi2x, getResources().getColor(R.color.mahi), "Mahi"));

        CAL.add(new Category("Wahoo H&G Wild-Caught", R.drawable.wahoo2x, getResources().getColor(R.color.wahoo), "Wahoo"));

        CAL.add(new Category("Grouper H&G Wild-Caught", R.drawable.grouper2x, getResources().getColor(R.color.grouper), "Grouper"));

        CAL.add(new Category("Salmon H&G Farm-Raised", R.drawable.tuna2x, getResources().getColor(R.color.salmon), "Salmon"));

        return CAL;
    }
}
