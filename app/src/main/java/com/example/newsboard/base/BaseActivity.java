package com.example.newsboard.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newsboard.ui.mine.HistoryActivity;
import com.example.newsboard.ui.login.LoginActivity;
import com.example.newsboard.ui.mine.MineFragment;
import com.example.newsboard.util.TokenUtils;


/**
 * <pre>
 *     author : Zhong DeFeng
 *     e-mail : 1756809298@qq.com
 *     time   : 2020/11/16 14:21
 *     desc   : 活动的基类，提供处理退出登录广播机制
 *     version: 1.0
 * </pre>
 */
public class BaseActivity extends AppCompatActivity {

    private LogoutReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MineFragment.ACTION_LOGOUT);
        receiver = new LogoutReceiver();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

    /**
     * 用于处理登录返回
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 退出登录广播接收器
     */
    static class LogoutReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("退出确认");
            builder.setMessage("退出当前账号，将无法进行评论和收藏");
            builder.setCancelable(true);
            builder.setPositiveButton("确认退出", (dialog, which) -> {
                clear();
                Intent loginIntent = new Intent(context, LoginActivity.class);
                ((Activity)context).startActivityForResult(loginIntent, LoginActivity.LOGIN_REQUEST_CODE);
            });

            builder.setNegativeButton("取消", (dialog, which) -> {

            });
            builder.show();
        }

        /**
         * 清除Activity、token、浏览历史等数据，确认退出时调用
         */
        private void clear() {
//            ActivityController.finishAll();
            TokenUtils.clearToken();
            HistoryActivity.clearHistoryNews();
        }
    }
}
