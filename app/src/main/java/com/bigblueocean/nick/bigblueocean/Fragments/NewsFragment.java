package com.bigblueocean.nick.bigblueocean.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigblueocean.nick.bigblueocean.Adapters.NewsViewAdapter;;
import com.bigblueocean.nick.bigblueocean.Helpers.PostJSONTask;
import com.bigblueocean.nick.bigblueocean.Helpers.ServerPost;
import com.bigblueocean.nick.bigblueocean.R;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.bigblueocean.nick.bigblueocean.Model.News;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

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
