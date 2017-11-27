package com.bigblueocean.nick.bigblueocean.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.bigblueocean.nick.bigblueocean.Activities.HomeActivity;
import com.bigblueocean.nick.bigblueocean.Adapters.OrderViewAdapter;
import com.bigblueocean.nick.bigblueocean.R;

import java.util.ArrayList;

import com.bigblueocean.nick.bigblueocean.Model.Product;

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
        View view = inflater.inflate(R.layout.order_fragment_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setAdapter(new OrderViewAdapter(currentOrder, orderFragmentListener));
        Button submitButton = view.findViewById(R.id.order_submit);
        Button clearButton = view.findViewById(R.id.order_clear);
        TextView hint = view.findViewById(R.id.my_order_help_hint);
        if (currentOrder.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            submitButton.setVisibility(View.GONE);
            clearButton.setVisibility(View.GONE);
            hint.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            submitButton.setVisibility(View.VISIBLE);
            clearButton.setVisibility(View.VISIBLE);
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
