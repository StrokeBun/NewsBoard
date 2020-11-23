package com.example.newsboard.ui.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.newsboard.base.BaseActivity;
import com.example.newsboard.R;
import com.example.newsboard.util.HttpUtils;
import com.example.newsboard.util.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import scut.carson_ho.kawaii_loadingview.Kawaii_LoadingView;

public class LoginActivity extends BaseActivity {

    public static final String EXTRA_USERNAME = "username";
    private static final String LOGIN_URL = "https://vcapi.lvdaqian.cn/login";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD= "password";
    private static final String PREF_REMEMBER_INFO = "rememberInfo";
    // volatile保证多线程内存可见性
    private static volatile boolean receiveData = false;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private CheckBox rememberPassword;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        boolean isRemember = pref.getBoolean(PREF_REMEMBER_INFO, false);
        if (isRemember) {
            autoFillForm();
        }

        loginButton.setOnClickListener(view -> {
            String username = usernameEdit.getText().toString();
            String password = passwordEdit.getText().toString();

            new Thread(() -> {
                JSONObject params = new JSONObject();
                try {
                    params.put(PREF_USERNAME, username);
                    params.put(PREF_PASSWORD, password);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
                String result = HttpUtils.post(LOGIN_URL, params);
                try {
                    TokenUtils.setTokenFromResponse(result);
                    receiveData = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }).start();
            // 采用乐观锁防止启动多次MainActivity
            while (!receiveData) {

            }
            handleRememberInfo(username, password);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra(EXTRA_USERNAME, username);
            startActivity(intent);
        });
    }

    private void init() {
        setContentView(R.layout.activity_login);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        usernameEdit = findViewById(R.id.username);
        passwordEdit = findViewById(R.id.password);
        rememberPassword = findViewById(R.id.remember_info);
        loginButton = findViewById(R.id.login_button);
        receiveData = false;
    }

    private void autoFillForm() {
        String username = pref.getString(PREF_USERNAME, "");
        String password = pref.getString(PREF_PASSWORD, "");
        usernameEdit.setText(username);
        passwordEdit.setText(password);
        rememberPassword.setChecked(true);
    }

    private void handleRememberInfo(String username, String password) {
        if (rememberPassword.isChecked()) {
            editor.putString(PREF_USERNAME, username);
            editor.putString(PREF_PASSWORD, password);
            editor.putBoolean(PREF_REMEMBER_INFO, true);
        } else {
            editor.clear();
        }
        editor.apply();
    }

}