package com.bigblueocean.nick.bigblueocean.adapters;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigblueocean.nick.bigblueocean.helpers.FontHelper;
import com.bigblueocean.nick.bigblueocean.fragments.OrderFragment.OnListFragmentInteractionListener;
import com.bigblueocean.nick.bigblueocean.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.bigblueocean.nick.bigblueocean.model.Product;

public class OrderViewAdapter extends RecyclerView.Adapter<OrderViewAdapter.ViewHolder> {

    private final ArrayList<Product> currentOrder;
    private final OnListFragmentInteractionListener orderInteractionListener;
    private Context context;

    public OrderViewAdapter(ArrayList<Product> items, OnListFragmentInteractionListener listener) {
        currentOrder = items;
        orderInteractionListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        context = holder.productCell.getContext();
        final int pos = position;
        holder.currentProduct = currentOrder.get(pos);
        holder.productTitle.setBackgroundColor(Color.parseColor(currentOrder.get(pos).getCategory().getColor()));

        holder.productTitle.setText(currentOrder.get(pos).getCategory().getTag());
        holder.productTitle.setTypeface(FontHelper.antonTypeface(context));

        holder.productSubtitle.setBackgroundColor(Color.parseColor(currentOrder.get(pos).getCategory().getColor()));
        if (currentOrder.get(pos).getSpecies() != null && !currentOrder.get(pos).getSpecies().equalsIgnoreCase("N/P") ){
            holder.productSubtitle.setText(currentOrder.get(pos).getRegion()+" " +currentOrder.get(pos).getSpecies());
        }
        else
            holder.productSubtitle.setText(currentOrder.get(pos).getRegion());
        holder.productSubtitle.setTypeface(FontHelper.antonTypeface(context));

        String label = currentOrder.get(pos).getGrade();
        holder.productDetailsLabel1.setText(label);
        holder.productDetailsLabel1.setTypeface(FontHelper.antonTypeface(context));
        holder.productDetailsLabel1.setTextColor(Color.parseColor(currentOrder.get(pos).getCategory().getColor()));

        label = currentOrder.get(pos).getSize();
        holder.productDetailsLabel2.setText(label);
        holder.productDetailsLabel2.setTypeface(FontHelper.antonTypeface(context));
        holder.productDetailsLabel2.setTextColor(Color.parseColor(currentOrder.get(pos).getCategory().getColor()));

        label = currentOrder.get(pos).getQuantity() + " lbs.";
        holder.productDetailsLabel3.setText(label);
        holder.productDetailsLabel3.setTypeface(FontHelper.antonTypeface(context));
        holder.productDetailsLabel3.setTextColor(Color.parseColor(currentOrder.get(pos).getCategory().getColor()));

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
        if (!currentOrder.get(pos).getPrice().isEmpty()) {
            label = numberFormat.format(Double.parseDouble(currentOrder.get(pos).getPrice()));
        }
        else
            label = "$0.00";
        holder.productDetailsLabel4.setText(label);
        holder.productDetailsLabel4.setTypeface(FontHelper.antonTypeface(context));
        holder.productDetailsLabel4.setTextColor(Color.parseColor(currentOrder.get(pos).getCategory().getColor()));


        holder.productCell.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (orderInteractionListener != null) {
                    orderInteractionListener.onListFragmentInteraction(holder.currentProduct);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return currentOrder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View productCell;
        public final TextView productTitle;
        public final TextView productSubtitle;
        public final TextView productDetailsLabel1;
        public final TextView productDetailsLabel2;
        public final TextView productDetailsLabel3;
        public final TextView productDetailsLabel4;
        public Product currentProduct;

        public ViewHolder(View view) {
            super(view);
            productCell = view;
            productTitle = (TextView) view.findViewById(R.id.order_title);
            productSubtitle = (TextView) view.findViewById(R.id.order_subtitle);
            productDetailsLabel1 = (TextView) view.findViewById(R.id.order_description_sub1);
            productDetailsLabel2 = (TextView) view.findViewById(R.id.order_description_sub2);
            productDetailsLabel3 = (TextView) view.findViewById(R.id.order_description_sub3);
            productDetailsLabel4 = (TextView) view.findViewById(R.id.order_description_sub4);
        }
    }
}
