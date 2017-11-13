package com.bigblueocean.nick.bigblueocean.Helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nick on 11/13/17.
 */

public class ReadJSONFromURLTask extends AsyncTask<String, Void, String> {

    private String data;

    protected String doInBackground(String... urlString) {
        String json = "";
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString[0]);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            reader.close();
            json = buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.data = json;
        return json;
    }

    protected void onPostExecute(String result) {
        this.data = result;
    }

    public String getData() {
        return this.data;
    }
}
