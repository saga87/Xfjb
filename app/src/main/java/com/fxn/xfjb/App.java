package com.fxn.xfjb;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by fxn on 2017/6/19.
 */

public class App extends Application {
    private static OkHttpClient client;
    private static Application contect;
    @Override
    public void onCreate() {
        super.onCreate();
        contect = this;
        client = new OkHttpClient.Builder()
                //设置超时，不设置可能会报异常
                .connectTimeout(1000, TimeUnit.MINUTES)
                .readTimeout(1000, TimeUnit.MINUTES)
                .writeTimeout(1000, TimeUnit.MINUTES)
                .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }
    public static OkHttpClient getClient(){
        return client;
    }

    public static Context getContext(){
        return contect;
    }

}
