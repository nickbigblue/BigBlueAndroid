package com.bigblueocean.nick.bigblueocean;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import java.util.ArrayList;

import Model.Product;

public class OrderFragment extends Fragment {

    private static int productColumnCount = 1;
    private OnListFragmentInteractionListener orderFragmentListener;
    public ArrayList<Product> currentOrder = HomeActivity.getCurrentOrder();

    public OrderFragment() {
    }

    public static OrderFragment newInstance(int columnCount) {
        OrderFragment fragment = new OrderFragment();
        productColumnCount = columnCount;
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeActivity) getActivity()).logE();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_fragment_list, container, false);


            Context context = view.getContext();
            RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.list);
            recyclerView.setAdapter(new OrderViewAdapter(currentOrder, orderFragmentListener));

        Button submitButton = (Button) view.findViewById(R.id.order_submit);
        Button clearButton = (Button) view.findViewById(R.id.order_clear);
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
