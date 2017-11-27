package com.bigblueocean.nick.bigblueocean.Helpers;

import android.os.AsyncTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nick on 11/14/17.
 */

public class PostJSONTask extends AsyncTask<String, Void, JSONObject> {

    @Override
    protected JSONObject doInBackground(String... params) {
        JSONObject responseJsonObj = new JSONObject();
        String data = "";
        if (params[1] != null) {
            data = params[1];
        }
        final String tag = params[0];
        final String url = params[2];
        final String token = params[3];

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
            char[] codearr = Character.toChars(code);


            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            reader.close();
            responseJsonObj = new JSONObject(buffer.toString());

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return responseJsonObj;
    }


}
