package com.example.newsboard.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.newsboard.base.BaseActivity;
import com.example.newsboard.R;
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

    // SharedPreferences存储用户名对应的key
    private static final String PREF_USERNAME = "username";
    // SharedPreferences存储密码对应的key
    private static final String PREF_PASSWORD= "password";
    // SharedPreferences存储记住密码对应的key
    private static final String PREF_REMEMBER_INFO = "rememberInfo";

    // 是否已经获取token，true则跳转到MainActivity，使用volatile保证多线程内存可见性
    private static volatile boolean receiveData = false;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private CheckBox rememberPassword;
    private Button loginButton;
    private TextView loadingText;

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

        username = usernameEdit.getText().toString();
        password = passwordEdit.getText().toString();

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
        loadingText = findViewById(R.id.loading_text);
        receiveData = false;

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(view -> {
            AsyncGetToken();
            createLoadingAnimation();
        });
    }

    /**
     * 用存储的信息填写登录页面的表单
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
                receiveData = true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 产生加载动画
     */
    private void createLoadingAnimation() {
        new Thread(() -> {
            loadingText.setAlpha(1.0F);
            final String[] loadingStr = {"Loading.", "Loading..", "Loading..."};
            int i = 0;
            // 使用自旋防止用户点击多次登录在栈中压入多个Activity
            while (!receiveData) {
                int index= i++ % 3;
                this.runOnUiThread(() -> loadingText.setText(loadingStr[index]));
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            jumpToMainActivity();
        }).start();
    }

    /**
     * 跳转到主页
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