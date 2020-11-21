package com.example.newsboard.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.newsboard.R;
import com.example.newsboard.ui.activity.LoginActivity;
import com.example.newsboard.util.TokenUtils;

public class MineFragment extends Fragment {

    public static final String ACTION_LOGOUT = "com.example.newsboard.action_logout";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mine, container, false);
        Button button = root.findViewById(R.id.logout_button);
        button.setOnClickListener(view -> {
            TokenUtils.clearToken();
            Intent intent = new Intent(ACTION_LOGOUT);
            getActivity().sendBroadcast(intent);
        });
        // 未登录则跳转到登录页面
        if (TokenUtils.isEmptyToken()) {
            startActivity(new Intent(getContext(), LoginActivity.class));
        }
        return root;
    }

}