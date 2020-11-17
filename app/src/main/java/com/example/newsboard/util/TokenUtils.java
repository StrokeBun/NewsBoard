package com.example.newsboard.util;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TokenUtils {

    private static final String NO_TOKEN = "";
    private static volatile String token = NO_TOKEN;

    private static final String REQUEST_HEADER_AUTHORIZATION_KEY = "Authorization";
    private static final String RESPONSE_TOKEN_KEY = "token";
    private static final String AUTHORIZATION_TOKEN_PREFIX = "Bearer ";


    /**
     * 返回使用token进行权限识别的http header，返回类型为Map<String, String>
     */
    public static Map<String, String> getAuthorizationHeader() {
        return new HashMap<String, String>() {
            {
                String auth = AUTHORIZATION_TOKEN_PREFIX + token;
                put(REQUEST_HEADER_AUTHORIZATION_KEY, auth);
            }
        };
    }

    public static String getToken() {
        return token;
    }

    /**
     * 返回是否已经缓存token
     */
    public static boolean hasToken() {
        return token != NO_TOKEN;
    }

    /**
     * 从http response从解析token并缓存
     * @param response http response字符串
     */
    public static void setTokenFromResponse (String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            token = (String) jsonObject.get(RESPONSE_TOKEN_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
            token = NO_TOKEN;
        }
    }

    /**
     * 清除已经缓存的token，用户退出登录后调用
     */
    public static void clearToken() {
        token = NO_TOKEN;
    }

}
