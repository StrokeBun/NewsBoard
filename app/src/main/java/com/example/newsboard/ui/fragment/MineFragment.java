package com.example.newsboard.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.newsboard.R;
import com.example.newsboard.ui.activity.HistoryActivity;
import com.example.newsboard.ui.activity.LoginActivity;
import com.example.newsboard.util.TokenUtils;

public class MineFragment extends Fragment {

    public static final String ACTION_LOGOUT = "com.example.newsboard.action_logout";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mine, container, false);
        init(root);

        // 未登录则跳转到登录页面
        if (TokenUtils.isEmptyToken()) {
            startActivity(new Intent(getContext(), LoginActivity.class));
        } else {
            TextView usernameTextView = root.findViewById(R.id.username_text_view);
            Intent intent = getActivity().getIntent();
            String username = intent.getStringExtra(LoginActivity.EXTRA_USERNAME);
            if (username != null) {
                usernameTextView.setText(username);
            }
        }
        return root;
    }

    private void init(View root) {
        ImageButton logoutButton = root.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(view -> {
            Intent intent = new Intent(ACTION_LOGOUT);
            getActivity().sendBroadcast(intent);
        });

        ImageButton historyButton = root.findViewById(R.id.history_news_button);
        historyButton.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), HistoryActivity.class));
        });
    }

}