package com.bigblueocean.nick.bigblueocean.Helpers;

import com.bigblueocean.nick.bigblueocean.Model.News;
import com.bigblueocean.nick.bigblueocean.Model.Product;
import com.bigblueocean.nick.bigblueocean.Model.User;
import com.bigblueocean.nick.bigblueocean.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
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

    private String[] params = new String[4];
    private JSONObject response = new JSONObject();
    private String message;
    private boolean success = false;
    private String url = "http://bigblueocean.net/jb2/public/bigblueapp/android";
    private final String token = "wD3TE7ThkpTxtx4b7kD0Lli1sYJzJOBUeM";

    public ServerPost() {

    }

    public ServerPost(String[] params) {
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
        this.params[3] = token;
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
        }
    }

    public void setUser(){
        try {
            FirebaseAuth fbauth = FirebaseAuth.getInstance();
            String uid = fbauth.getCurrentUser().getUid();
            JSONObject js = new JSONObject();
            js.put("uid", uid);
            String[] params = {"AUTH",  new Gson().toJson(js)};
            setupParam(params);
            send();
//            User currentUser = new Gson().fromJson(getJson().getString("News"), User.class);
            //bind @currentUser params
        } catch(JSONException e){
            e.printStackTrace();
        }
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

}
