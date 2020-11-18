package com.example.newsboard.util;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TokenUtils {

    private static final String NO_TOKEN = "";
    private static volatile String token = NO_TOKEN;

    private static final String AUTHORIZATION_KEY = "Authorization";
    private static final String TOKEN_KEY = "token";
    private static final String AUTHORIZATION_TOKEN_PREFIX = "Bearer ";


    public static Map<String, String> getAuthorizationHeader() {
        return new HashMap<String, String>() {
            {
                String auth = AUTHORIZATION_TOKEN_PREFIX + token;
                put(AUTHORIZATION_KEY, auth);
            }
        };
    }

    public static String getToken() {
        return token;
    }

    public static boolean hasToken() {
        return token != NO_TOKEN;
    }

    public static void setTokenFromResponse (String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            token = (String) jsonObject.get(TOKEN_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
            token = NO_TOKEN;
        }
    }

    public static void clearToken() {
        token = NO_TOKEN;
    }

}
