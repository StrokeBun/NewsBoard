package com.example.newsboard.util;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.newsboard.ui.activity.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: TokenUtils
 * @Package: util
 * @Description: token工具类，存储和读取token
 * @author: Zhong Defeng
 * @date: 2020/11/17 20:57
 */
public final class TokenUtils {

    private static SharedPreferences pref;
    // token对应的key
    private static final String TOKEN_KEY = "token";
    // http请求头中权限对应的key
    private static final String REQUEST_HEADER_AUTHORIZATION_KEY = "Authorization";
    // http请求头中token前缀
    private static final String AUTHORIZATION_TOKEN_PREFIX = "Bearer ";
    // 本地未存储token的默认值
    private static final String NO_TOKEN = "";

    /**
     * 返回使用token进行权限识别的http header，返回类型为Map<String, String>
     */
    public static Map<String, String> getAuthorizationHeader() {
        return new HashMap<String, String>() {
            {
                String auth = AUTHORIZATION_TOKEN_PREFIX + getToken();
                put(REQUEST_HEADER_AUTHORIZATION_KEY, auth);
            }
        };
    }

    /**
     * 返回是否已经缓存了token
     */
    public static boolean isEmptyToken() {
        return getToken().equals(NO_TOKEN);
    }

    /**
     * 从http response从解析token并缓存
     * @param response http response字符串
     */
    public static void setTokenFromResponse(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        String token = (String) jsonObject.get(TOKEN_KEY);
        setToken(token);
    }

    /**
     * 清除已经缓存的token，用户退出登录后调用
     */
    public static void clearToken() {
        setToken(NO_TOKEN);
    }

    public static void initTokenUtils(SharedPreferences aPref) {
        pref = aPref;
        clearToken();
    }

    private static String getToken() {
        return pref.getString(TOKEN_KEY, NO_TOKEN);
    }

    private static void setToken(String token) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    private TokenUtils() {
        throw new UnsupportedOperationException("TokenUtils cant be constructed");
    }
}
