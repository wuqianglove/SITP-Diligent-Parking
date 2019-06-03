package com.example.admin.sitp_parkinglot;

/**
 * Created by admin on 2019-01-23.
 */

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

public class Common extends Application{
    private String Username;
    private String Password;
    public static double LONGITUDE = 0;//经度
    public static double LATITUDE = 0;//纬度

    public String getUsername(){
        return this.Username;
    }
    public void setUsername(String c){
        this.Username= c;
    }

    public String getPassword(){
        return this.Password;
    }
    public void setPassword(String c){
        this.Password= c;
    }
    @Override
    public void onCreate(){
        Username = "";
        Password="";
        super.onCreate();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }
}
