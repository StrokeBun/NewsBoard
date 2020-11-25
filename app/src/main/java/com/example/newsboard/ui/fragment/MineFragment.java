package com.example.newsboard.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.newsboard.R;
import com.example.newsboard.ui.activity.HistoryActivity;
import com.example.newsboard.ui.activity.LoginActivity;
import com.example.newsboard.util.TokenUtils;

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

    // 退出登录的信号，用于广播
    public static final String ACTION_LOGOUT = "com.example.newsboard.action_logout";
    // 退出登录按键
    private ImageButton logoutButton;
    // 浏览历史按键
    private ImageButton historyButton;
    // 用户名文本框
    private TextView usernameTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mine, container, false);
        logoutButton = root.findViewById(R.id.logout_button);
        initComponents(root);

        if (TokenUtils.isNotLogin()) {
            startActivity(new Intent(getContext(), LoginActivity.class));
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
            Intent intent = new Intent(ACTION_LOGOUT);
            getActivity().sendBroadcast(intent);
        });

        historyButton = root.findViewById(R.id.history_news_button);
        historyButton.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), HistoryActivity.class));
        });

        usernameTextView = root.findViewById(R.id.username_text_view);
    }

    /**
     * 设置个人主页的已登录用户名
     */
    private void setLoginUsername() {
        Intent intent = getActivity().getIntent();
        String username = intent.getStringExtra(LoginActivity.EXTRA_USERNAME);
        usernameTextView.setText(username);
    }

}