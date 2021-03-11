package com.example.newsboard.ui.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.newsboard.R;
import com.example.newsboard.ui.login.LoginActivity;
import com.example.newsboard.util.TokenUtils;

import static android.app.Activity.RESULT_OK;

/**
 * <pre>
 *     author : Zhong DeFeng
 *     e-mail : 1756809298@qq.com
 *     time   : 2020/11/20 18:05
 *     desc   : 个人主页Frament
 *     version: 1.0
 * </pre>
 */
public class MineFragment extends Fragment {

    /**
     * 退出登录的信号，用于广播
     */
    public static final String ACTION_LOGOUT = "com.example.newsboard.action_logout";

    private ImageButton logoutButton;

    /**
     * 浏览历史按键
     */
    private ImageButton historyButton;

    private TextView usernameTextView;


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        setLoginUsername();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mine, container, false);
        logoutButton = root.findViewById(R.id.logout_button);
        initComponents(root);
        if (TokenUtils.isNotLogin()) { // 未登录跳转到登录页面
            waitForLogin();
        } else {
            setLoginUsername();
        }
        return root;
    }

    /**
     * 初始化组件
     * @param root 根视图
     */
    private void initComponents(View root) {
        logoutButton.setOnClickListener(view -> {
            waitForLogin();
        });

        historyButton = root.findViewById(R.id.history_news_button);
        historyButton.setOnClickListener(view -> startActivity(new Intent(getActivity(), HistoryActivity.class)));

        usernameTextView = root.findViewById(R.id.username_text_view);
    }

    private void waitForLogin() {
        Intent loginIntent = new Intent(getContext(), LoginActivity.class);
        startActivityForResult(loginIntent, LoginActivity.LOGIN_REQUEST_CODE);
    }

    /**
     * 设置个人主页的已登录用户名
     */
    private void setLoginUsername() {
        String username = TokenUtils.getUsername();
        usernameTextView.setText(username);
    }

}