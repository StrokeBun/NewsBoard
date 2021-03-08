package com.example.newsboard.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.newsboard.base.BaseActivity;
import com.example.newsboard.R;
import com.example.newsboard.ui.MainActivity;
import com.example.newsboard.util.HttpUtils;
import com.example.newsboard.util.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <pre>
 *     author : Zhong DeFeng
 *     e-mail : 1756809298@qq.com
 *     time   : 2020/11/15 20:57
 *     desc   : 登录页面活动
 *     version: 1.0
 * </pre>
 */
public class LoginActivity extends BaseActivity {

    // 传递给MainActivity的Intent中用户名对应的key
    public static final String EXTRA_USERNAME = "username";
    // 用户登录获取token的http url
    private static final String LOGIN_URL = "https://vcapi.lvdaqian.cn/login";
    private static final String RIGHT_PASSWORD = "123456";

    // SharedPreferences存储用户名对应的key
    private static final String PREF_USERNAME = "username";
    // SharedPreferences存储密码对应的key
    private static final String PREF_PASSWORD= "password";
    // SharedPreferences存储记住密码对应的key
    private static final String PREF_REMEMBER_INFO = "rememberInfo";

    // 登录成功的 Message.what
    private static final int MSG_LOGIN = 1;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private CheckBox rememberPassword;
    private Button loginButton;
    private ProgressBar progressBar;
    private Handler mHandler;

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();

        boolean isRemember = pref.getBoolean(PREF_REMEMBER_INFO, false);
        // 勾选了记住密码则自动填写表单
        if (isRemember) {
            autoFillForm();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        loginButton.setEnabled(true);
    }

    /**
     * 初始化组件
     */
    private void initComponents() {
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        usernameEdit = findViewById(R.id.username);
        passwordEdit = findViewById(R.id.password);
        rememberPassword = findViewById(R.id.remember_info);

        mHandler = new Handler(this.getMainLooper(), null) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_LOGIN: {
                        jumpToMainActivity();
                        runOnUiThread(()-> {progressBar.setVisibility(View.GONE);});
                    }
                }
            }
        };

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(view -> {
            username = usernameEdit.getText().toString();
            password = passwordEdit.getText().toString();
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "账号/密码为空", Toast.LENGTH_SHORT).show();
                return;
            } else if (!RIGHT_PASSWORD.equals(password)) {
                Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                return;
            }
            AsyncGetToken();
            // 登录按键不可重复
            loginButton.setEnabled(false);
            // 开启加载动画
            progressBar.setVisibility(View.VISIBLE);
        });

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
    }

    /**
     * 用存储的用户名密码填写登录页面的表单
     */
    private void autoFillForm() {
        String username = pref.getString(PREF_USERNAME, "");
        String password = pref.getString(PREF_PASSWORD, "");
        usernameEdit.setText(username);
        passwordEdit.setText(password);
        rememberPassword.setChecked(true);
    }

    /**
     * 异步获取token
     */
    private void AsyncGetToken() {
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
                Message message = new Message();
                message.what = MSG_LOGIN;
                mHandler.sendMessage(message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }


    /**
     * 跳转到主页，并传递用户名
     */
    private void jumpToMainActivity() {
        saveRememberInfo(username, password);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_USERNAME, username);
        startActivity(intent);
    }

    /**
     * 存储用户名和密码
     * @param username 用户名
     * @param password 密码
     */
    private void saveRememberInfo(String username, String password) {
        if (rememberPassword.isChecked()) { // 勾选了记住密码
            editor.putString(PREF_USERNAME, username);
            editor.putString(PREF_PASSWORD, password);
            editor.putBoolean(PREF_REMEMBER_INFO, true);
        } else {
            editor.remove(PREF_USERNAME);
            editor.remove(PREF_PASSWORD);
            editor.remove(PREF_REMEMBER_INFO);
        }
        editor.apply();
    }

}