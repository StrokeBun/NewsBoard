package com.example.newsboard;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newsboard.util.HttpUtil;
import com.example.newsboard.util.TokenUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity {

    private static final String RIGHT_PASSWORD = "123456";
    private static final String WRONG_PASSWORD = "密码错误";
    private static final String LOGIN_URL = "https://vcapi.lvdaqian.cn/login";

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String REMEMBER_PWD = "rememberPwd";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private CheckBox remeberPwd;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        boolean isRemember = pref.getBoolean(REMEMBER_PWD, false);
        if (isRemember) {
            action();
        }

        loginButton.setOnClickListener(view -> {
            String username = usernameEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            if (!username.isEmpty() && RIGHT_PASSWORD.equals(password)) {
                if (remeberPwd.isChecked()) {
                    saveInfo(username, password);
                } else {
                    editor.clear();
                }
                editor.apply();
                new Thread(() -> {
                    JSONObject params = new JSONObject();
                    try {
                        params.put("username", "123");
                        params.put("password", "123");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return;
                    }
                    String result = HttpUtil.post(LOGIN_URL, params);
                    TokenUtil.setTokenFromResponse(result);
                    LoginActivity.this.runOnUiThread(() -> startActivity(new Intent(LoginActivity.this, ArticleActivity.class)));
                }).start();
            } else {
                Toast.makeText(LoginActivity.this, WRONG_PASSWORD, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        setContentView(R.layout.activity_login);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        usernameEdit = findViewById(R.id.username);
        passwordEdit = findViewById(R.id.password);
        remeberPwd = findViewById(R.id.remember_pwd);
        loginButton = findViewById(R.id.login_button);
    }

    private void action() {
        String username = pref.getString(USERNAME, "");
        String password = pref.getString(PASSWORD, "");
        usernameEdit.setText(username);
        passwordEdit.setText(password);
        remeberPwd.setChecked(true);
    }

    private void saveInfo(String username, String password) {
        editor.putString(USERNAME, username);
        editor.putString(PASSWORD, password);
        editor.putBoolean(REMEMBER_PWD, true);
    }

}