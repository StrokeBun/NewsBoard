package com.example.newsboard.ui.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newsboard.base.BaseActivity;
import com.example.newsboard.R;
import com.example.newsboard.util.HttpUtils;
import com.example.newsboard.util.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity {

    private static final String RIGHT_PASSWORD = "123456";
    private static final String WARN_WRONG_PASSWORD = "密码错误";
    private static final String LOGIN_URL = "https://vcapi.lvdaqian.cn/login";

    public static final String PREF_PRE_ACTIVITY = "preActivity";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD= "password";
    private static final String PREF_REMEMBER_PASSWORD = "rememberPwd";

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
        boolean isRemember = pref.getBoolean(PREF_REMEMBER_PASSWORD, false);
        if (isRemember) {
            autoFillForm();
        }

        loginButton.setOnClickListener(view -> {
            String username = usernameEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            if (!username.isEmpty() && RIGHT_PASSWORD.equals(password)) {
                if (rememberPassword.isChecked()) {
                    saveInfo(username, password);
                } else {
                    editor.clear();
                }
                editor.apply();
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
                    TokenUtils.setTokenFromResponse(result);
                    LoginActivity.this.runOnUiThread(() -> startActivity(new Intent(this, MainActivity.class)));
                }).start();
            } else {
                Toast.makeText(this, WARN_WRONG_PASSWORD, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        setContentView(R.layout.activity_login);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        usernameEdit = findViewById(R.id.username);
        passwordEdit = findViewById(R.id.password);
        rememberPassword = findViewById(R.id.remember_pwd);
        loginButton = findViewById(R.id.login_button);
    }

    private void autoFillForm() {
        String username = pref.getString(PREF_USERNAME, "");
        String password = pref.getString(PREF_PASSWORD, "");
        usernameEdit.setText(username);
        passwordEdit.setText(password);
        rememberPassword.setChecked(true);
    }

    private void saveInfo(String username, String password) {
        editor.putString(PREF_USERNAME, username);
        editor.putString(PREF_PASSWORD, password);
        editor.putBoolean(PREF_REMEMBER_PASSWORD, true);
    }

}