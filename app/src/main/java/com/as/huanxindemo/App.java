package com.as.huanxindemo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;

import java.util.Iterator;
import java.util.List;

/**
 * -----------------------------
 * Created by zqf on 2018/6/7.
 * ---------------------------
 */

public class App extends Application {
    protected static SharedPreferences sp = null;
    protected  static Context CONTEXT = null;
    private String spName = "HuanXinDemo";
    @Override
    public void onCreate() {
        super.onCreate();
        if (CONTEXT == null) {
            CONTEXT = getApplicationContext();
        }
        if (sp == null) {
            sp = CONTEXT.getSharedPreferences(spName, MODE_PRIVATE);
        }
        initIMsdk();
    }

    public static SharedPreferences getSp() {
        return sp;
    }
    /**
     * 注册IM
     */
    private void initIMsdk() {
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        if (processAppName == null ||!processAppName.equalsIgnoreCase(this.getPackageName())) {
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        //EaseUI.getInstance().init(this,null);
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        EaseUI.getInstance().init(this, options);
        EMClient.getInstance().setDebugMode(true);
    }
    /**
     * 获取appName
     * @param pID
     * @return
     */
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}
