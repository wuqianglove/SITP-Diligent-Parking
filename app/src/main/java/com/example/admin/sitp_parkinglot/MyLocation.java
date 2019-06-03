package com.example.admin.sitp_parkinglot;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.map.MyLocationConfiguration;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.RuntimePermissions;

public class MyLocation extends AppCompatActivity {
    private MapView mMapView;
    private LocationClient locationClient;
    private BaiduMap baiduMap;
    private boolean firstLocation;
    private BitmapDescriptor mCurrentMarker;
    private double Latitude1;
    private double Longtitude1;
    private boolean isFront;
    //private MyLocationConfiguration config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sysClose.getInstance().addActivity(this);
        //此方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_my_location);
        mMapView =(MapView)findViewById(R.id.baiDuMv);
        baiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15f);
        baiduMap.setMapStatus(msu);
        // 定位初始化

        baiduMap.setMyLocationEnabled(true);
        locationClient = new LocationClient(this);
        firstLocation =true;

        List<String> permissionList=new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MyLocation.this, Manifest.
                permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MyLocation.this, Manifest.
                permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MyLocation.this, Manifest.
                permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()){
            String[] permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MyLocation.this,permissions,1);
        }else {

            LocationClientOption option = new LocationClientOption();
            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
            option.setIsNeedAddress(true);
            option.setIsNeedLocationDescribe(true);
            option.setIsNeedLocationPoiList(true);
            option.setOpenGps(true);
            option.setCoorType("bd09ll"); // 设置坐标类型
            option.setScanSpan(1000);
            locationClient.setLocOption(option);
//            BitmapDescriptor myMarker = BitmapDescriptorFactory
//                    .fromResource(R.drawable.loc);
//            MyLocationConfiguration config = new MyLocationConfiguration(
//                    MyLocationConfiguration.LocationMode.FOLLOWING, true, myMarker);
            locationClient.start();
            // requestLocation();
        }
        // 设置定位的相关配置


        // 设置自定义图标

        onStart();

        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                // map view 销毁后不在处理新接收的位置
                if (location == null || mMapView == null)
                    return;
                // 构造定位数据
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                // 设置定位数据
                baiduMap.setMyLocationData(locData);
                baiduMap.setMyLocationEnabled(true);
                if(isFront&&location.getLatitude()!=4.9E-324) {
                    Toast.makeText(MyLocation.this, "地址:"+location.getAddrStr()+"\n经度:" + location.getLatitude() + "\n纬度:" + location.getLongitude(), Toast.LENGTH_SHORT).show();
                }
                // 第一次定位时，将地图位置移动到当前位置
                if (firstLocation)
                {
                    firstLocation = false;
                    LatLng xy1 = new LatLng(location.getLatitude(), location.getLongitude());
                    Toast.makeText(MyLocation.this, "经度:"+location.getLatitude()+"\n纬度:"+location.getLongitude(), Toast.LENGTH_SHORT).show();
                    MapStatusUpdate status1 = MapStatusUpdateFactory.newLatLng(xy1);
                    Latitude1=location.getLatitude();
                    Longtitude1=location.getLongitude();
                    baiduMap.animateMapStatus(status1);
                }
            }
        });
    }
    @Override
    protected void onStart()
    {
        // 如果要显示位置图标,必须先开启图层定位
        baiduMap.setMyLocationEnabled(true);
        if (!locationClient.isStarted())
        {
            locationClient.start();
        }
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        // 关闭图层定位
        baiduMap.setMyLocationEnabled(false);
        locationClient.stop();
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()
        locationClient.stop();
        mMapView.onDestroy();
        mMapView = null;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()
        mMapView.onResume();
        isFront = true;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()
        mMapView.onPause();
        isFront = false;
    }

    public double getLatitude(){
        return Latitude1;
    }
    public double gtLongtitude(){
        return Longtitude1;
    }

}