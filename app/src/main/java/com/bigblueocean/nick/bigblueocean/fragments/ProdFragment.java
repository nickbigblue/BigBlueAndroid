package com.bigblueocean.nick.bigblueocean.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigblueocean.nick.bigblueocean.adapters.ProdViewAdapter;
import com.bigblueocean.nick.bigblueocean.model.Category;
import com.bigblueocean.nick.bigblueocean.R;

import java.util.ArrayList;

public class ProdFragment extends Fragment {

    private OnListFragmentInteractionListener prodInteractionListener;

    public ProdFragment() {
    }

    public static ProdFragment newInstance() {
        return new ProdFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_fragment_list, container, false);
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
        ArrayList<Category> categories = new ArrayList<>();
        for (Category cat: Category.values()){
            categories.add(cat);
        }
        return categories;
    }
}
