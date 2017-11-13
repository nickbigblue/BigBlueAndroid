package com.bigblueocean.nick.bigblueocean.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigblueocean.nick.bigblueocean.Activities.HomeActivity;
import com.bigblueocean.nick.bigblueocean.Adapters.NewsViewAdapter;;
import com.bigblueocean.nick.bigblueocean.Helpers.ReadJSONFromURLTask;
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
    private static int newsColumnCount = 1;
    private OnListFragmentInteractionListener newsFragmentListener;
    private ArrayList<News> recentNews;
    private String dataSourceURL = "http://bigblueocean.net/jb2/public/bigblueapp/news";

    public NewsFragment() {

    }

    public static NewsFragment newInstance(int columnCount) {
        NewsFragment fragment = new NewsFragment();
        newsColumnCount = columnCount;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment_list, container, false);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
            recyclerView.setAdapter(new NewsViewAdapter(getRecentNews(), newsFragmentListener));
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

    public ArrayList<News> getRecentNews() {
        Type dataType = new TypeToken<List<News>>(){}.getType();
        ReadJSONFromURLTask task = new ReadJSONFromURLTask();
        task.execute(dataSourceURL);
        String dataJSON = null;
            try {
                dataJSON = task.get();
                Gson gson = new Gson();
                JSONObject json = new JSONObject(dataJSON);
                recentNews = gson.fromJson(json.getString("News"), dataType);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        return recentNews;
    }

}
