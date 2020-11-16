package com.example.newsboard.util;


import org.json.JSONException;
import org.json.JSONObject;

public class TokenUtil {

    private static final String TOKEN = "token";
    private static String EMPTY_TOKEN = "";
    private static volatile String token = EMPTY_TOKEN;
    private static final String TOKEN_PREFIX = "Bearer ";

    public static String getAuthorization() {
        return TOKEN_PREFIX + token;
    }

    public static String getToken() {
        return token;
    }

    public static boolean hasToken() {
        return token != EMPTY_TOKEN;
    }

    public static void setTokenFromResponse (String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
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
