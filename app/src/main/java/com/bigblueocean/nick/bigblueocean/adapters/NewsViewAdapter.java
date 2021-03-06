package com.bigblueocean.nick.bigblueocean.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bigblueocean.nick.bigblueocean.fragments.NewsFragment.OnListFragmentInteractionListener;
import com.bigblueocean.nick.bigblueocean.R;

import java.util.ArrayList;

import com.bigblueocean.nick.bigblueocean.model.News;
import com.squareup.picasso.Picasso;

public class NewsViewAdapter extends RecyclerView.Adapter<NewsViewAdapter.ViewHolder> {

    private ArrayList<News> recentNews = new ArrayList<News>(1);
    private final OnListFragmentInteractionListener newsInteractionListener;

    public NewsViewAdapter(ArrayList<News> items, OnListFragmentInteractionListener listener) {
        recentNews = items;
        newsInteractionListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Context context = holder.newsCell.getContext();
        holder.currentNews = recentNews.get(position);
            if(recentNews.get(position).getImage() == null) {
                holder.newsBanner.setImageResource(R.drawable.newsgenericimage);
            }
            else{
                Picasso
                        .with(context)
                        .load(recentNews.get(position).getImage())
                        .resize(640,360)
                        .centerCrop()
                        .into(holder.newsBanner);
            }
        holder.newsTitle.setText(recentNews.get(position).getTitle());
        holder.newsCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != newsInteractionListener) {
                    newsInteractionListener.onListFragmentInteraction(holder.currentNews);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (recentNews != null) {
            return recentNews.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View newsCell;
        public final ImageView newsBanner;
        public final TextView newsTitle;
        public News currentNews;

        public ViewHolder(View view) {
            super(view);
            newsCell = view;
            newsBanner = (ImageView) view.findViewById(R.id.news_image);
            newsTitle = (TextView) view.findViewById(R.id.news_title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + newsTitle.getText() + "'";
        }
    }
}
