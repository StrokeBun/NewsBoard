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

public class MineFragment extends Fragment {

    public static final String ACTION_LOG_OUT = "com.example.newsboard.LOG_OUT";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mine, container, false);
        Button logoutButton = root.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(view -> {
            Intent intent = new Intent(ACTION_LOG_OUT);
            getActivity().sendBroadcast(intent);
        });
        return root;
    }
}