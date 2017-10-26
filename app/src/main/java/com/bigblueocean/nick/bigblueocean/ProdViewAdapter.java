package com.bigblueocean.nick.bigblueocean;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigblueocean.nick.bigblueocean.ProdFragment.OnListFragmentInteractionListener;


import java.util.ArrayList;

import Model.Category;

/**

 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ProdViewAdapter extends RecyclerView.Adapter<ProdViewAdapter.ViewHolder> {

    private final ArrayList<Category> categoryArrayList;
    private final OnListFragmentInteractionListener prodAdapterInteractionListener;
    private Context context;

    public ProdViewAdapter(ArrayList<Category> categories, OnListFragmentInteractionListener listener) {
        categoryArrayList = categories;
        prodAdapterInteractionListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        context = holder.categoryView.getContext();
        final int pos = position;
        holder.currentCategory = categoryArrayList.get(pos);
        holder.categoryImage.setImageBitmap(categoryArrayList.get(pos).getImage());
        holder.categoryTitle.setText(categoryArrayList.get(pos).getTitle());
        holder.categoryTitle.setTypeface(FontHelper.antonTypeface(context));
        holder.categoryTitle.setTextColor(categoryArrayList.get(pos).getColor());

        holder.categoryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prodAdapterInteractionListener != null) {
                    Log.e("adapter","On CLick listener: "+categoryArrayList.get(pos).getTitle());
                    prodAdapterInteractionListener.onListFragmentInteraction(holder.currentCategory);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        protected final View categoryView;
        protected final ImageView categoryImage;
        protected final TextView categoryTitle;
        protected Category currentCategory;

        public ViewHolder(View view) {
            super(view);
            categoryView = view;
            categoryImage = (ImageView) view.findViewById(R.id.product_image);
            categoryTitle = (TextView) view.findViewById(R.id.product_title);
        }
    }

}
