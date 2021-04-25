package com.dochsoft.fullmoon.nymphwardiscordbot;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class GetPatcherUrl {

    public static String getPatcherUrl() throws IOException {
        String url = "https://fullmoon-ff654.firebaseapp.com/contents/segment/patcherUrl.json";
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            String jsonText = sb.toString();
            JSONObject json = new JSONObject(jsonText);
            return (String) json.get("url");
        } finally {
            is.close();
        }
    }

    public static String getPatchDate() throws IOException {
        String url = "https://fullmoon-ff654.firebaseapp.com/contents/segment/patcherUrl.json";
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            String jsonText = sb.toString();
            JSONObject json = new JSONObject(jsonText);
            int patchMonth = (Integer) json.get("patchMonth");
            int patchDay = (Integer) json.get("patchDay");
            return patchMonth + "월 " + patchDay + "일자 ";
        } finally {
            is.close();
        }
    }

}
