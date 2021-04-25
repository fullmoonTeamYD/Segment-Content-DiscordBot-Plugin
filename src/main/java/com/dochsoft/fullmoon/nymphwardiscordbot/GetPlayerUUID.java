package com.dochsoft.fullmoon.nymphwardiscordbot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.UUID;

public class GetPlayerUUID {

    public static String getPlayerUUID(String playerName) throws IOException {
        String url = "https://api.mojang.com/users/profiles/minecraft/" + playerName;
        JSONObject jsonObject = getJsonData(url);

        if (jsonObject.toString().contains("name")) {
            return (String) jsonObject.get("id");
        } else {
            return (String) jsonObject.get("error");
        }
    }

    public static JSONObject getJsonData(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            String jsonText = sb.toString();
            URL urlFromString = new URL(url);
            HttpURLConnection http = (HttpURLConnection)urlFromString.openConnection();
            int statusCode = http.getResponseCode();
            if (statusCode == 204) {
                String errorJson = "{\"error\": \"Not Found\"}";
                return new JSONObject(errorJson);
            } else {
                JSONObject json = new JSONObject(jsonText);
                return json;
            }
        } finally {
            is.close();
        }
    }
}
