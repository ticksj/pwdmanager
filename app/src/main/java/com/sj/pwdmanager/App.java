package com.sj.pwdmanager;

import android.app.Application;

import com.sj.pwdmanager.db.DBUtils;

/**
 * Created by SJ on 2020/6/16.
 */
public class App extends Application {

    private String key="";
    private static App app;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        DBUtils.init(this);
    }

    public static App getApp() {
        return app;
    }
}
