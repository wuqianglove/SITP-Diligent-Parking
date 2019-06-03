package com.example.admin.sitp_parkinglot;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class PersonInfo extends AppCompatActivity {
    private String locationProvider;
    private static final String TAG = "PersonInfo";
    private TextView Email;
    private TextView Phone;
    private TextView Username;
    private TextView Time;
    private TextView Gender;
    private Button exitLog;
    private Button location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sysClose.getInstance().addActivity(this);
        setContentView(R.layout.activity_person_info);
        getWindow().setTitle("个人信息");
        Email = (TextView) findViewById(R.id.textViewEmail);
        Phone = (TextView) findViewById(R.id.textViewPhone);
        Username = (TextView) findViewById(R.id.textViewName);
        Time = (TextView) findViewById(R.id.textViewTime);
        Gender = (TextView) findViewById(R.id.textViewGender);
        location=(Button)findViewById(R.id.button14);
        location.setOnClickListener(mLister);
        final Common User = (Common) getApplication();
        Username.setText(User.getUsername());
        BmobUser userCurrent = BmobUser.getCurrentUser();
        Phone.setText(userCurrent.getMobilePhoneNumber());
        Email.setText(userCurrent.getEmail());
        Gender.setText("男");
        Time.setText(userCurrent.getCreatedAt());
        exitLog=(Button)findViewById(R.id.button13);
        exitLog.setOnClickListener(mLister);
        LocationManager locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);//低精度，如果设置为高精度，依然获取不了location。
        criteria.setAltitudeRequired(false);//不要求海拔
        criteria.setBearingRequired(false);//不要求方位
        criteria.setCostAllowed(true);//允许有花费
        criteria.setPowerRequirement(Criteria.POWER_LOW);//低功耗

        //从可用的位置提供器中，匹配以上标准的最佳提供器
        locationProvider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onCreate: 没有权限 ");
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationProvider);
        Log.d(TAG, "onCreate: " + (location == null) + "..");
        if (location != null) {
            Log.d(TAG, "onCreate: location");
            //不为空,显示地理位置经纬度
            showLocation(location);
        }
        //监视地理位置变化
        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
    }

    /**
     * LocationListern监听器
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
     */

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d(TAG, "onProviderEnabled: " + provider + ".." + Thread.currentThread().getName());
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "onProviderDisabled: " + provider + ".." + Thread.currentThread().getName());
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "onLocationChanged: " + ".." + Thread.currentThread().getName());
            //如果位置发生变化,重新显示
            showLocation(location);
        }
    };
    public View.OnClickListener mLister=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.button13:
                    exitAll();
                    break;
                case R.id.button14:
                    Intent DisplayLoc=new Intent(PersonInfo.this,MyLocation.class);
                    startActivity(DisplayLoc);
                    break;
            }
        }
    };
    private void showLocation(Location location) {
        Log.d(TAG, "定位成功------->" + "location------>经度为：" + location.getLatitude() + "\n纬度为" + location.getLongitude());

    }


    public void exitAll(){
        Toast.makeText(PersonInfo.this, "注销成功", Toast.LENGTH_SHORT).show();
        finish();
        BmobUser.logOut();
        //ParkingLot.super.onDestroy();
        sysClose.getInstance().exit();
        startActivity(new Intent(PersonInfo.this,MainActivity.class));
    }
}
