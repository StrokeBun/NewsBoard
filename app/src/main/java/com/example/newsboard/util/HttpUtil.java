package com.example.newsboard.util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    private static final String POST = "POST";
    private static final String GET = "GET";

    public static String get(String urlPath, JSONObject params) {
        return HttpRequestInternal(urlPath, GET, params);
    }

    public static String post(String urlPath, JSONObject params) {
        return HttpRequestInternal(urlPath, POST, params);
    }

    private static String HttpRequestInternal(String urlPath, String method, JSONObject params) {
        HttpURLConnection connection = null;
        try {
            connection = getHttpURLConnection(urlPath, method);
            if (POST.equals(method)) {
                writeRequestBody(connection, params);
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

    private static HttpURLConnection getHttpURLConnection(String urlPath, String method) throws IOException {
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
        connection.connect();
        return connection;
    }

    private static void writeRequestBody(HttpURLConnection connection,  JSONObject params) throws IOException {
        try (DataOutputStream os = new DataOutputStream(connection.getOutputStream())) {
            String requestBody = String.valueOf(params);
            os.writeBytes(requestBody);
            os.flush();
        }
    }

}
