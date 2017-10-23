package com.bigblueocean.nick.bigblueocean;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigblueocean.nick.bigblueocean.ProdFragment.OnListFragmentInteractionListener;


import java.util.ArrayList;
import java.util.List;

import Model.Category;

/**

 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ProdViewAdapter extends RecyclerView.Adapter<ProdViewAdapter.ViewHolder> {

    private final ArrayList<Category> mValues;
    private final OnListFragmentInteractionListener mListener;

    public ProdViewAdapter(ArrayList<Category> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Context context = holder.mIdView.getContext();
        final int pos = position;
        holder.mItem = mValues.get(pos);
        holder.mIdView.setText(mValues.get(position).title);
        holder. categoryImage.setImageBitmap(mValues.get(position).image);
        holder.category.setText("asdfasdfads");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                    Log.e("adapter","On CLick listener");
                   // mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final ImageView categoryImage;
        public final TextView category;
        public Category mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            categoryImage = (ImageView) view.findViewById(R.id.product_image);
            category = (TextView) view.findViewById(R.id.content2);
        }

        @Override
        public String toString() {
            return super.toString() + " '" +  category.getText() + "'";
        }
    }

    public void onListFragmentInteraction(Category item){

    }
}
