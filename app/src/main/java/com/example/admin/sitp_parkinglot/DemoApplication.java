package com.example.admin.sitp_parkinglot;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by admin on 2019-01-30.
 */

public class DemoApplication extends Application{
    @Override
    public void onCreate(){
        super.onCreate();
        SDKInitializer.initialize(this);
    }
}