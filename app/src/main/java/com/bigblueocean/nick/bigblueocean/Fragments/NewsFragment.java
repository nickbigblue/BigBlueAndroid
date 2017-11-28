package com.bigblueocean.nick.bigblueocean.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigblueocean.nick.bigblueocean.Adapters.NewsViewAdapter;;
import com.bigblueocean.nick.bigblueocean.Helpers.FontHelper;
import com.bigblueocean.nick.bigblueocean.Helpers.ServerPost;
import com.bigblueocean.nick.bigblueocean.R;;
import java.util.ArrayList;
import com.bigblueocean.nick.bigblueocean.Model.News;


public class NewsFragment extends Fragment {
    private OnListFragmentInteractionListener newsFragmentListener;
    private ArrayList<News> recentNews = new ArrayList<News>();

    public NewsFragment() {

    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment_list, container, false);
        TextView newsTitle = view.findViewById(R.id.news_from_title);
        newsTitle.setTypeface(FontHelper.antonTypeface(getContext()));
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
            ServerPost sp = new ServerPost();
            recyclerView.setAdapter(new NewsViewAdapter(sp.getRecentNews(recentNews), newsFragmentListener));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            newsFragmentListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        newsFragmentListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(News item);
    }



}
