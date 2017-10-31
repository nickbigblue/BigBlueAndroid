package com.bigblueocean.nick.bigblueocean;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bigblueocean.nick.bigblueocean.OrderFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.Scanner;

import Model.Product;

public class OrderViewAdapter extends RecyclerView.Adapter<OrderViewAdapter.ViewHolder> {

    private final ArrayList<Product> currentOrder;
    private final OnListFragmentInteractionListener orderAdapterInteractionListener;
    private Context context;

    public OrderViewAdapter(ArrayList<Product> items, OnListFragmentInteractionListener listener) {
        currentOrder = items;
        orderAdapterInteractionListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        context = holder.productView.getContext();
        final int pos = position;
        holder.currentProduct = currentOrder.get(pos);
        holder.productTitle.setBackgroundColor(currentOrder.get(pos).getCategory().getColor());
        String title = currentOrder.get(pos).getCategory().getTitle();
        String[] result = title.split("\\s+");
        holder.productTitle.setText(result[0]);
        holder.productTitle.setTypeface(FontHelper.antonTypeface(context));
        holder.productSubtitle.setBackgroundColor(currentOrder.get(pos).getCategory().getColor());
        holder.productSubtitle.setText(currentOrder.get(pos).getDescription()[0]);
        holder.productSubtitle.setTypeface(FontHelper.antonTypeface(context));
        holder.productDetailsLabel.setText("1+ \t Placeholder size \t Placeholder lbs. \t $8.75");
        holder.productDetailsLabel.setTypeface(FontHelper.antonTypeface(context));
        holder.productDetailsLabel.setTextColor(currentOrder.get(pos).getCategory().getColor());

        holder.productView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderAdapterInteractionListener != null) {
                    Log.e("adapter","On CLick listener: "+currentOrder.get(pos).getCategory().getTitle());
                    orderAdapterInteractionListener.onListFragmentInteraction(holder.currentProduct);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return currentOrder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View productView;
        public final TextView productTitle;
        public final TextView productSubtitle;
        public final TextView productDetailsLabel;
        public Product currentProduct;

        public ViewHolder(View view) {
            super(view);
            productView = view;
            productTitle = (TextView) view.findViewById(R.id.order_title);
            productSubtitle = (TextView) view.findViewById(R.id.order_subtitle);
            productDetailsLabel = (TextView) view.findViewById(R.id.order_description);
        }

        @Override
        public String toString() {
            return super.toString() + "\t" + productDetailsLabel.getText() + "\t";
        }
    }
}
