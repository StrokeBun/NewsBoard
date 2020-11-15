package com.example.newsboard.util;


import org.json.JSONException;
import org.json.JSONObject;

public class TokenUtil {

    private static final String TOKEN = "token";
    private static String EMPTY_TOKEN = "";
    private static String token = EMPTY_TOKEN;

    public static String getToken() {
        return token;
    }

    public static void setToken(String newToken) {
        token = newToken;
    }

    public static void setTokenFromResponse (String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            token = (String) jsonObject.get(TOKEN);
        } catch (JSONException e) {
            e.printStackTrace();
            token = EMPTY_TOKEN;
        }
    }

    public static void clearToken() {
        token = EMPTY_TOKEN;
    }

}
