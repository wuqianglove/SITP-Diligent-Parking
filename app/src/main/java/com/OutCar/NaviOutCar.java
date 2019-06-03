package com.OutCar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.sitp_parkinglot.R;
import com.example.admin.sitp_parkinglot.sysClose;

public class NaviOutCar extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sysClose.getInstance().addActivity(this);
        setContentView(R.layout.activity_navi_out_car);

    }
}
