package com.romanbel.testyota;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman on 23.01.18.
 */

public class Request {

    private static final String LOG_TAG = Request.class.getSimpleName();

    public List<Flight>  fetchTranslateData(String urlSpec) {
        URL url = createUrl(urlSpec);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Flight> flights = extractFeatureFromJson(jsonResponse);
        return flights;
    }

    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            Log.d(LOG_TAG, url.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    private List<Flight>  extractFeatureFromJson(String translateJSON) {
        if (TextUtils.isEmpty(translateJSON)) {
            return null;
        }

        String translate = "";
        List<Flight> list = new ArrayList<Flight>();

        try {
            JSONArray itemArray = new JSONArray(translateJSON);
            for (int i = 0; i < itemArray.length(); i++) {
                JSONObject object = itemArray.getJSONObject(i);
                Flight flight = new Flight();
                flight.setLaunchDateUnix(object.getInt("launch_date_unix"));
                flight.setDetails(object.getString("details"));
                JSONObject rocket = object.getJSONObject("rocket");
                flight.setRocketName(rocket.getString("rocket_name"));
                JSONObject link = object.getJSONObject("links");
                flight.setArticleLink(link.getString("article_link"));
                flight.setMissionPatch(link.getString("mission_patch"));
                list.add(flight);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return list;
    }

}
