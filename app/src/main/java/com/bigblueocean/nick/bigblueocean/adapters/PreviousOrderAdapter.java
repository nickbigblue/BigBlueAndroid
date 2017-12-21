package com.bigblueocean.nick.bigblueocean.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bigblueocean.nick.bigblueocean.R;
import com.bigblueocean.nick.bigblueocean.model.OrderHeader;

import java.util.ArrayList;

/**
 * Created by nick on 12/20/17.
 */

public class PreviousOrderAdapter extends RecyclerView.Adapter<PreviousOrderAdapter.ViewHolder> {

    private final ArrayList<OrderHeader> previousOrders;
    private Context context;

    public PreviousOrderAdapter(ArrayList<OrderHeader> items) {
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
        holder.currentOrderHeader = previousOrders.get(pos);
        ArrayList<String> lineItems = holder.currentOrderHeader.getLineItems();
        holder.previousOrderDate.setText(holder.currentOrderHeader.getTimestamp());
        //TODO:: CHANGE THIS TO ACCEPT PROPER VALUES FOR TOTAL POUNDAGE
        holder.previousOrderPoundage.setText(Integer.toString(holder.currentOrderHeader.getLineItems().size()));

        for (String category: lineItems) {
            String testString = category.toUpperCase();
            switch (testString){
                case "TUNA":
                    holder.previousOrderTuna.setBackgroundColor(context.getResources().getColor(R.color.tuna));
                    break;
                case "SWORD":
                    holder.previousOrderSword.setBackgroundColor(context.getResources().getColor(R.color.sword));
                    break;
                case "MAHI":
                    holder.previousOrderMahi.setBackgroundColor(context.getResources().getColor(R.color.mahi));
                    break;
                case "WAHOO":
                    holder.previousOrderWahoo.setBackgroundColor(context.getResources().getColor(R.color.wahoo));
                    break;
                case "GROUPER":
                    holder.previousOrderGrouper.setBackgroundColor(context.getResources().getColor(R.color.grouper));
                    break;
                case "SALMON":
                    holder.previousOrderSalmon.setBackgroundColor(context.getResources().getColor(R.color.salmon));
                    break;
                case "OTHER":
                    break;
            }
        }

        holder.previousOrderDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO:: HEAD TO NEW ACTIVITY WITH DETAILS
                // holder.currentOrderHeader.getOrderId();
                // open dialog? start activity?
            }
        });
    }

    @Override
    public int getItemCount() {
        return previousOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View previousOrderCell;
        public final TextView previousOrderTuna;
        public final TextView previousOrderSword;
        public final TextView previousOrderMahi;
        public final TextView previousOrderWahoo;
        public final TextView previousOrderGrouper;
        public final TextView previousOrderSalmon;
        public final TextView previousOrderPoundage;
        public final TextView previousOrderDate;
        public final ImageButton previousOrderDetailsButton;
        public OrderHeader currentOrderHeader;

        public ViewHolder(View view) {
            super(view);
            previousOrderCell = view;
            previousOrderTuna = view.findViewById(R.id.tuna_past);
            previousOrderSword = view.findViewById(R.id.sword_past);
            previousOrderMahi = view.findViewById(R.id.mahi_past);
            previousOrderWahoo = view.findViewById(R.id.wahoo_past);
            previousOrderGrouper = view.findViewById(R.id.grouper_past);
            previousOrderSalmon = view.findViewById(R.id.salmon_past);
            previousOrderPoundage = view.findViewById(R.id.past_total_poundage);
            previousOrderDate = view.findViewById(R.id.past_order_id);
            previousOrderDetailsButton = view.findViewById(R.id.past_order_details_button);
        }
    }

}
