package com.bigblueocean.nick.bigblueocean.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.bigblueocean.nick.bigblueocean.R;
import com.bigblueocean.nick.bigblueocean.model.Order;
import com.bigblueocean.nick.bigblueocean.model.Product;

import java.util.ArrayList;

/**
 * Created by nick on 12/20/17.
 */

public class PreviousOrderAdapter extends RecyclerView.Adapter<PreviousOrderAdapter.ViewHolder> {

    private final ArrayList<Order> previousOrders;
    private Context context;

    public PreviousOrderAdapter(ArrayList<Order> items) {
        previousOrders = items;
    }

    @Override
    public PreviousOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.previous_order_cell, parent, false);
        return new PreviousOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PreviousOrderAdapter.ViewHolder holder, int position) {
        context = holder.previousOrderCell.getContext();
        final int pos = position;
        holder.currentOrder = previousOrders.get(pos);
        ArrayList<Product> lineItems = holder.currentOrder.getLineItems();

        String[] detailsArr = {holder.currentOrder.getSender(), holder.currentOrder.getTimestamp()};
        String localizedDetail = "Sent by: "+holder.currentOrder.getSender()+" @ "+holder.currentOrder.getTimestamp();
        ArrayAdapter<String> detailsAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, detailsArr);
        detailsArr[0] = localizedDetail;
        holder.previousOrderDetails.setAdapter(detailsAdapter);

        ArrayList<String> contentsArr = new ArrayList<String>();
        for (Product prod : lineItems) {
            String localizedContents = prod.getQuantity() + " lbs. of " + prod.getCategory() + " @ $" + prod.getPrice();
            contentsArr.add(localizedContents);
        }
        ArrayAdapter<String> contentsAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, contentsArr);
        holder.previousOrderContents.setAdapter(contentsAdapter);
    }

    @Override
    public int getItemCount() {
        return previousOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View previousOrderCell;
        public final ListView previousOrderDetails;
        public final ListView previousOrderContents;
        public Order currentOrder;

        public ViewHolder(View view) {
            super(view);
            previousOrderCell = view;
            previousOrderDetails = view.findViewById(R.id.past_order_details);
            previousOrderContents = view.findViewById(R.id.past_order_contents);
        }
    }

}
