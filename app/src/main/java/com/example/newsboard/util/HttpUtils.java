package com.example.newsboard.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class HttpUtils {

    private static final String POST = "POST";
    private static final String GET = "GET";

    /**
     * 发送http get请求
     * @param urlPath http请求的url
     * @return http response body字符串
     */
    public static String get(String urlPath) {
        return get(urlPath, null, null);
    }

    /**
     * 发送携带参数的http get请求
     * @param urlPath http请求的url
     * @param params http请求所携带的参数，JSON格式
     * @return http response body字符串
     */
    public static String get(String urlPath, JSONObject params) {
        return get(urlPath, null, params);
    }

    /**
     * 发送自定义http header的http get请求
     * @param urlPath http请求的url
     * @param header 自定义http header，Map<String, String> 格式
     * @return http response body字符串
     */
    public static String get(String urlPath, Map<String, String> header) {
        return get(urlPath, header, null);
    }


    /**
     *
     * @param urlPath http请求的url
     * @param header 自定义http header，Map<String, String> 格式
     * @param params http请求所携带的参数，JSON格式
     * @return http response body字符串
     */
    public static String get(String urlPath, Map<String,String> header, JSONObject params) {
        return HttpRequestInternal(urlPath, GET, header, params);
    }

    /**
     * 发送http post请求
     * @param urlPath http请求的url
     * @return http response body字符串
     */
    public static String post(String urlPath) {
        return post(urlPath, null, null);
    }

    /**
     * 发送自定义http header的http post请求
     * @param urlPath http请求的url
     * @param header 自定义http header，Map<String, String> 格式
     * @return http response body字符串
     */
    public static String post(String urlPath, Map<String, String> header) {
        return post(urlPath, header, null);
    }

    /**
     * 发送携带参数的http post请求
     * @param urlPath http请求的url
     * @param params http请求所携带的参数，JSON格式
     * @return http response body字符串
     */
    public static String post(String urlPath, JSONObject params) {
        return post(urlPath, null, params);
    }

    /**
     *
     * @param urlPath http请求的url
     * @param header 自定义http header，Map<String, String> 格式
     * @param params http请求所携带的参数，JSON格式
     * @return http response body字符串
     */
    public static String post(String urlPath, Map<String,String> header, JSONObject params) {
        return HttpRequestInternal(urlPath, POST, header, params);
    }

    /**
     * 发起http请求
     * @param urlPath http请求的url
     * @param method 请求方式，支持get和post
     * @param header 自定义请求头部
     * @param params 携带的参数
     * @return http response body 字符串
     */
    private static String HttpRequestInternal(String urlPath, String method, Map<String, String> header,JSONObject params) {
        HttpURLConnection connection = null;
        try {
            connection = getHttpURLConnection(urlPath, method, header, params);
            // POST请求参数在RequestBody中
            if (params != null && POST.equals(method)) {
                try (DataOutputStream os = new DataOutputStream(connection.getOutputStream())) {
                    String requestBody = String.valueOf(params);
                    os.writeBytes(requestBody);
                    os.flush();
                }
            }

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (InputStream is = connection.getInputStream();
                     BufferedReader bf = new BufferedReader(new InputStreamReader(is))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bf.readLine()) != null) {
                        response.append(line);
                    }
                    return response.toString();
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    /**
     * 获取HttpURLConnection，默认连接超时时间为8秒，关闭缓存，编码为UTF-8，请求参数格式为JSON
     * @param urlPath http请求的url
     * @param method http请求方式，支持get和post
     * @param header 自定义http header，默认设置编码为UTF-8，请求格式为JSON
     * @return http response body 字符串
     * @throws IOException 错误的url将抛出IOException
     * @throws JSONException params解析失败将抛出JSONException
     */
    private static HttpURLConnection getHttpURLConnection(String urlPath, String method, Map<String, String> header, JSONObject params) throws IOException, JSONException {
        // GET请求在url上拼接参数
        if (GET.equals(method) && params != null) {
            StringBuilder sb = new StringBuilder(urlPath);
            sb.append('?');
            Iterator<String> iterator = params.keys();
            while(iterator.hasNext()){
                String key = iterator.next();
                sb.append(key).append('=').append(params.getString(key));
                if (iterator.hasNext()) {
                    sb.append('&');
                }
            }
            urlPath = sb.toString();
        }

        URL url = new URL(urlPath);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(8000);
        connection.setReadTimeout(8000);
        connection.setUseCaches(false);
        connection.setDoInput(true);
        if (POST.equals(method)) {
            connection.setDoOutput(true);
        }
        connection.setRequestMethod(method);
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Content-Type", "application/json");
        // 写入头部
        if (header != null) {
            header.forEach(connection::setRequestProperty);
        }
        return connection;
    }
}
