package com.bigblueocean.nick.bigblueocean.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigblueocean.nick.bigblueocean.Helpers.FontHelper;
import com.bigblueocean.nick.bigblueocean.Fragments.OrderFragment.OnListFragmentInteractionListener;
import com.bigblueocean.nick.bigblueocean.R;

import java.util.ArrayList;

import com.bigblueocean.nick.bigblueocean.Model.Product;

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

        holder.productTitle.setText(currentOrder.get(pos).getCategory().getTag());
        holder.productTitle.setTypeface(FontHelper.antonTypeface(context));

        holder.productSubtitle.setBackgroundColor(currentOrder.get(pos).getCategory().getColor());
        holder.productSubtitle.setText(currentOrder.get(pos).getDescription()[0]);
        holder.productSubtitle.setTypeface(FontHelper.antonTypeface(context));

        String label = currentOrder.get(pos).getDescription()[1];
        holder.productDetailsLabel1.setText(label);
        holder.productDetailsLabel1.setTypeface(FontHelper.antonTypeface(context));
        holder.productDetailsLabel1.setTextColor(currentOrder.get(pos).getCategory().getColor());

        label = currentOrder.get(pos).getDescription()[2];
        holder.productDetailsLabel2.setText(label);
        holder.productDetailsLabel2.setTypeface(FontHelper.antonTypeface(context));
        holder.productDetailsLabel2.setTextColor(currentOrder.get(pos).getCategory().getColor());

        label = currentOrder.get(pos).getDescription()[3];
        holder.productDetailsLabel3.setText(label);
        holder.productDetailsLabel3.setTypeface(FontHelper.antonTypeface(context));
        holder.productDetailsLabel3.setTextColor(currentOrder.get(pos).getCategory().getColor());

        label = currentOrder.get(pos).getDescription()[4];
        holder.productDetailsLabel4.setText(label);
        holder.productDetailsLabel4.setTypeface(FontHelper.antonTypeface(context));
        holder.productDetailsLabel4.setTextColor(currentOrder.get(pos).getCategory().getColor());


        holder.productView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (orderAdapterInteractionListener != null) {
                    Log.e("adapter","On CLick listener: "+currentOrder.get(pos).getCategory().getTitle());
                    orderAdapterInteractionListener.onListFragmentInteraction(holder.currentProduct);
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
        public final View productView;
        public final TextView productTitle;
        public final TextView productSubtitle;
        public final TextView productDetailsLabel1;
        public final TextView productDetailsLabel2;
        public final TextView productDetailsLabel3;
        public final TextView productDetailsLabel4;
        public Product currentProduct;

        public ViewHolder(View view) {
            super(view);
            productView = view;
            productTitle = (TextView) view.findViewById(R.id.order_title);
            productSubtitle = (TextView) view.findViewById(R.id.order_subtitle);
            productDetailsLabel1 = (TextView) view.findViewById(R.id.order_description_sub1);
            productDetailsLabel2 = (TextView) view.findViewById(R.id.order_description_sub2);
            productDetailsLabel3 = (TextView) view.findViewById(R.id.order_description_sub3);
            productDetailsLabel4 = (TextView) view.findViewById(R.id.order_description_sub4);
        }
    }
}
