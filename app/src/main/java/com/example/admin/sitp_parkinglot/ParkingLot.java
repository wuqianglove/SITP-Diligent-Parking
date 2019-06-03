package com.example.admin.sitp_parkinglot;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.OutCar.EffectDisplay;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import cn.bmob.v3.BmobUser;

//import viewmodel.BannerViewModel;

public class ParkingLot extends AppCompatActivity {
//    private List<BannerviewModel> mData;
    /**
     * DrawerLayout
     */
    private DrawerLayout drawerLayout;
    //侧滑菜单栏
    private NavigationView navigationView;
    //沉浸式状态栏
    private SystemBarTintManager tintManager;
    //标题栏-侧滑-按钮
    ImageView menu;
    private Button ReserveInfo;
    private Button ReserveAct;
private Button OutInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sysClose.getInstance().addActivity(this);
        setContentView(R.layout.activity_parking_lot);
        //自定义顶部导航栏方法
        //initView();
        getWindow().setTitle("智能停车场管理系统");
        ReserveInfo=(Button)findViewById(R.id.button5);
        ReserveAct=(Button)findViewById(R.id.button2);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav);
        View headerView = navigationView.getHeaderView(0);
        //开启手势滑动打开侧滑菜单栏，如果要关闭手势滑动，将后面的UNLOCKED替换成LOCKED_CLOSED 即可
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        navigationView.setItemIconTintList(null);
        /**
         * 侧滑菜单item点击事件
         */
        OutInfo=(Button)findViewById(R.id.OutCar);
        OutInfo.setOnClickListener(mLister);
        ReserveAct.setOnClickListener(mLister);
        ReserveInfo.setOnClickListener(mLister);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_History:
                        Toast.makeText(ParkingLot.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                        Intent UserInfo=new Intent(ParkingLot.this,PersonInfo.class);
                        startActivity(UserInfo);
                        break;
                    case R.id.menu_Setting:
                        Toast.makeText(ParkingLot.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                        Intent ChangePwd1=new Intent(ParkingLot.this,ChangePwd.class);
                        startActivity(ChangePwd1);
                        break;
                    case R.id.menu_Feedback:
                        Toast.makeText(ParkingLot.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                        Intent feedback=new Intent(ParkingLot.this,Feedback.class);
                        startActivity(feedback);
                        break;
                    case R.id.menu_AboutUs:
                        Toast.makeText(ParkingLot.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                        Intent aboutusIn=new Intent(ParkingLot.this,AboutUs.class);
                        startActivity(aboutusIn);
                        break;
                    case R.id.logout:
                        Toast.makeText(ParkingLot.this, "注销成功", Toast.LENGTH_SHORT).show();
                        finish();
                        BmobUser.logOut();
                        //ParkingLot.super.onDestroy();
                        sysClose.getInstance().exit();
                        startActivity(new Intent(ParkingLot.this,MainActivity.class));
                        break;
                    case R.id.menu_Money:
                        Toast.makeText(ParkingLot.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                        Intent moneyleft=new Intent(ParkingLot.this,MoneyLeft.class);
                        startActivity(moneyleft);
                        break;

                }

                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });
    }
    public View.OnClickListener mLister=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.button5:
                    Intent ReserInfo=new Intent(ParkingLot.this,ReserveDisplay.class);
                    startActivity(ReserInfo);
                    break;
                case R.id.button2:
                    Intent Reserver=new Intent(ParkingLot.this,POIDisplay.class);
                    startActivity(Reserver);
                    break;
                case R.id.OutCar:
                    Intent Out=new Intent(ParkingLot.this, EffectDisplay.class);
                    startActivity(Out);
            }
        }
    };
}