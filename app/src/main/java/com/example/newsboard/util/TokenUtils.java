package com.example.newsboard.util;


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

    // http响应JSON中token对应的key
    private static final String RESPONSE_TOKEN_KEY = "token";
    // http请求头中权限对应的key
    private static final String REQUEST_HEADER_AUTHORIZATION_KEY = "Authorization";
    // http请求头中token前缀
    private static final String AUTHORIZATION_TOKEN_PREFIX = "Bearer ";

    private static final String NO_TOKEN = "";
    private static String token = NO_TOKEN;

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

    /**
     * 返回是否已经缓存了token
     */
    public static boolean isEmptyToken() {
        return token == NO_TOKEN;
    }

    /**
     * 从http response从解析token并缓存
     * @param response http response字符串
     */
    public static void setTokenFromResponse(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        token = (String) jsonObject.get(RESPONSE_TOKEN_KEY);
    }

    /**
     * 清除已经缓存的token，用户退出登录后调用
     */
    public static void clearToken() {
        token = NO_TOKEN;
    }


    private TokenUtils() {
        throw new UnsupportedOperationException("TokenUtils cant be constructed");
    }
}
