package com.as.huanxindemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * -----------------------------
 * Created by zqf on 2018/6/7.
 * ---------------------------
 */

public class EaseUtil {
    /**
     * 用户登录
     *
     * @param username
     * @param password
     */
    public static void login(final Activity fromActivity, final Class toClazz, String username, String password) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(fromActivity, "用户名或者密码为空！", Toast.LENGTH_SHORT).show();
        } else {
            EMClient.getInstance().login(username, password, new EMCallBack() {//回调
                @Override
                public void onSuccess() {
                    fromActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(fromActivity, "登录成功！", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent intent = new Intent(fromActivity, toClazz);
                    fromActivity.startActivity(intent);
                }

                /**
                 *
                 * @param progress //登录进度
                 * @param status  //登录状态
                 */
                @Override
                public void onProgress(int progress, String status) {
                }

                @Override
                public void onError(int code, final String message) {
                    fromActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(fromActivity, "登录失败，" + message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    /**
     * 用户注册
     *
     * @param userName
     * @param pasword
     */
    public static void registUser(final Activity fromActivity, final String userName, final String pasword) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(userName, pasword);
                    SPUtils.put(userName, userName);
                    SPUtils.put(pasword, pasword);
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    fromActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(fromActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();

    }


    /**
     * 退出登录
     */
    public static void loginOut(final Activity activity) {
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "您已退出登录！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(int code, final String message) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "退出失败，" + message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
