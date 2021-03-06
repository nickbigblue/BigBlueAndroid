package com.bigblueocean.nick.bigblueocean.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.bigblueocean.nick.bigblueocean.activities.HomeActivity;
import com.bigblueocean.nick.bigblueocean.activities.PreviousOrderActivity;
import com.bigblueocean.nick.bigblueocean.adapters.OrderViewAdapter;
import com.bigblueocean.nick.bigblueocean.R;

import java.util.ArrayList;

import com.bigblueocean.nick.bigblueocean.model.Product;

public class OrderFragment extends Fragment {

    private OnListFragmentInteractionListener orderFragmentListener;
    public ArrayList<Product> currentOrder = HomeActivity.getCurrentOrder();

    public OrderFragment() {
    }

    public static OrderFragment newInstance() {
        return new OrderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_order, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setAdapter(new OrderViewAdapter(currentOrder, orderFragmentListener));
        Button submitButton = view.findViewById(R.id.order_submit);
        Button clearButton = view.findViewById(R.id.order_clear);
        Button previousButton = view.findViewById(R.id.order_previous);
        TextView hint = view.findViewById(R.id.order_help_hint);
        if (currentOrder.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            submitButton.setVisibility(View.GONE);
            clearButton.setVisibility(View.GONE);
            previousButton.setVisibility(View.VISIBLE);
            hint.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            submitButton.setVisibility(View.VISIBLE);
            clearButton.setVisibility(View.VISIBLE);
            previousButton.setVisibility(View.GONE);
            hint.setVisibility(View.GONE);
        }
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).submitOrder();
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).clearOrder();
            }
        });
        previousButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PreviousOrderActivity.class));
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            orderFragmentListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        orderFragmentListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Product item);
    }

}
