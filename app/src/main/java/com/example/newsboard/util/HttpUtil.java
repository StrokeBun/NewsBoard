package com.example.newsboard.util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpUtil {

    private static final String POST = "POST";
    private static final String GET = "GET";

    public static String get(String urlPath) {
        return get(urlPath, null, null);
    }

    public static String get(String urlPath, JSONObject params) {
        return get(urlPath, null, params);
    }

    public static String get(String urlPath, Map<String, String> header) {
        return get(urlPath, header, null);
    }

    public static String get(String urlPath, Map<String,String> header, JSONObject params) {
        return HttpRequestInternal(urlPath, GET, header, params);
    }

    public static String post(String urlPath) {
        return post(urlPath, null, null);
    }

    public static String post(String urlPath, Map<String, String> header) {
        return post(urlPath, header, null);
    }

    public static String post(String urlPath, JSONObject params) {
        return post(urlPath, null, params);
    }

    public static String post(String urlPath, Map<String,String> header, JSONObject params) {
        return HttpRequestInternal(urlPath, POST, header, params);
    }

    private static String HttpRequestInternal(String urlPath, String method, Map<String, String> header,JSONObject params) {
        HttpURLConnection connection = null;
        try {
            connection = getHttpURLConnection(urlPath, method, header);
            if (POST.equals(method)) {
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    private static HttpURLConnection getHttpURLConnection(String urlPath, String method, Map<String, String> header) throws IOException {
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
        if (header != null) {
            header.forEach((k, v) -> {
                connection.setRequestProperty(k, v);
            });
        }
        return connection;
    }
}
