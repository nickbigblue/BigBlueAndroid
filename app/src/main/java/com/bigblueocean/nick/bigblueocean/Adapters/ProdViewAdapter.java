package com.bigblueocean.nick.bigblueocean.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigblueocean.nick.bigblueocean.Helpers.FontHelper;
import com.bigblueocean.nick.bigblueocean.Fragments.ProdFragment.OnListFragmentInteractionListener;
import com.bigblueocean.nick.bigblueocean.R;


import java.util.ArrayList;

import com.bigblueocean.nick.bigblueocean.Model.Category;

public class ProdViewAdapter extends RecyclerView.Adapter<ProdViewAdapter.ViewHolder> {

    private final ArrayList<Category> categoryArrayList;
    private final OnListFragmentInteractionListener productInteractionListener;
    private Context context;

    public ProdViewAdapter(ArrayList<Category> categories, OnListFragmentInteractionListener listener) {
        categoryArrayList = categories;
        productInteractionListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        context = holder.categoryCell.getContext();
        final int pos = position;
        holder.currentCategory = categoryArrayList.get(pos);
        holder.categoryImage.setBackgroundColor(Color.parseColor(holder.currentCategory.getColor()));
        holder.categoryImage.setImageResource(categoryArrayList.get(pos).getImage());
        holder.categoryTitle.setText(categoryArrayList.get(pos).getTitle());
        holder.categoryTitle.setTypeface(FontHelper.antonTypeface(context));
        holder.categoryTitle.setTextColor(Color.parseColor(categoryArrayList.get(pos).getColor()));

        holder.categoryCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productInteractionListener != null) {
                    productInteractionListener.onListFragmentInteraction(holder.currentCategory);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        protected final View categoryCell;
        protected final ImageView categoryImage;
        protected final TextView categoryTitle;
        protected Category currentCategory;

        public ViewHolder(View view) {
            super(view);
            categoryCell = view;
            categoryImage = (ImageView) view.findViewById(R.id.product_image);
            categoryTitle = (TextView) view.findViewById(R.id.product_title);
        }
    }

}
