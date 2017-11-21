package com.bigblueocean.nick.bigblueocean.Helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.bigblueocean.nick.bigblueocean.Model.News;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nick on 11/14/17.
 */

public class PostJSONTask extends AsyncTask<String, Void, String> {


    protected String doInBackground(String... params) {

        String response = "false";
        String data = "";
        if (params[1] != null) {
            data = params[1];
        }

        final String tag = params[0];
        final String token = "1234567890";
        final String url = "http://bigblueocean.net/jb2/public/bigblueapp/posttest";

        try {
            URL uri = new URL(url);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Tag", tag);
            jsonObject.addProperty("Token",token);
            jsonObject.addProperty("UID","0");
            jsonObject.addProperty("JSON", data);
            String gson = new Gson().toJson(jsonObject);

            HttpURLConnection con = (HttpURLConnection) uri.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestMethod("POST");

            OutputStream localDataOutputStream = con.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(localDataOutputStream, "UTF-8");
            osw.write(gson);
            osw.flush();
            osw.close();
            int code = con.getResponseCode();
            Log.e("PostJSON log", Integer.toString(code));

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            reader.close();
            JSONObject responseJsonObj = new JSONObject(buffer.toString());
            if (responseJsonObj.get("success").toString().equalsIgnoreCase("true")){
//                JSONArray jsarray = responseJsonObj.getJSONArray("json");
//                for(int i = 0; i <= jsarray.length(); i++){
//                    jsarray.get(i);
//                }
                response = "true";
            }

        }
        catch (Exception e){
            Log.v("ErrorAPP",e.toString());
        }
        return response;
    }


}
