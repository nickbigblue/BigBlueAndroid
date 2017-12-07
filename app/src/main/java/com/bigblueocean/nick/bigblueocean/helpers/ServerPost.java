package com.bigblueocean.nick.bigblueocean.helpers;

import android.util.Log;

import com.bigblueocean.nick.bigblueocean.model.News;
import com.bigblueocean.nick.bigblueocean.model.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by nick on 11/27/17.
 */

public class ServerPost{

    private String[] params = new String[5];
    private JSONObject response = new JSONObject();
    private String message;
    private boolean success = false;
    private String id;
    private String url = "http://bigblueocean.net/jb2/public/bigblueapp/android";
    private final String TOKEN = "wD3TE7ThkpTxtx4b7kD0Lli1sYJzJOBUeM";

    public ServerPost(FirebaseUser user) {
        this.id = user.getUid();
    }

    public ServerPost(String[] params, FirebaseUser user) {
        this.id = user.getUid();
        setupParam(params);
        this.send();
    }

    public ServerPost(String[] params, String url) {
        this.url = url;
        setupParam(params);
        this.send();
    }

    public JSONObject getJson() {
        JSONObject jsob = this.response;
        try{
            return jsob.getJSONObject("json");
        }catch (JSONException e){

        }
        return null;
    }

    public boolean getSuccess(){
        return this.success;
    }

    private String[] setupParam(String[] params){
        this.params[0] = params[0];
        this.params[1] = params[1];
        this.params[2] = url;
        this.params[3] = TOKEN;
        this.params[4] = id;
        return params;
    }

    public void send(){
        PostJSONTask task = new PostJSONTask();
        task.execute(params);
        try{
            response = task.get();
            success = Boolean.parseBoolean(response.get("success").toString());
            message = response.get("message").toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public User setUser(){
        User currentUser = new User();
        try {
            JSONObject js = new JSONObject();
            js.put("uid", id);
            String[] params = {"LOGIN",  new Gson().toJson(js)};
            setupParam(params);
            send();
            Log.e("", response.toString());
            currentUser = new Gson().fromJson(getJson().getString("JSON"), User.class);

        } catch(JSONException e){
            e.printStackTrace();
        }
        return currentUser;
    }

    public ArrayList<News> getRecentNews(ArrayList<News> recentNews) {
        try {
            String[] param = {"NEWS", ""};
            setupParam(param);
            send();
            Gson gson = new Gson();
            JSONObject json = getJson();
            Type dataType = new TypeToken<List<News>>() {}.getType();
            recentNews = gson.fromJson(json.getString("News"), dataType);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return recentNews;
    }

    public boolean createUser(User currentUser){
        boolean added = false;
        try {
            String js = new Gson().toJson(currentUser);
            String[] params = {"REG",  js};
            setupParam(params);
            send();
            Log.e("", response.toString());
            added = Boolean.parseBoolean(response.get("success").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return added;
    }
}
