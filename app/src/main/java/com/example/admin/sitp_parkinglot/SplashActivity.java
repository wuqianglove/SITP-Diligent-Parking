package com.example.admin.sitp_parkinglot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.Adapter.GuideActivity;
import com.Adapter.ShareUtils;
import com.Adapter.UtilTools;

public class SplashActivity extends AppCompatActivity {

    /**
     * 1.延时2000毫秒
     * 2.判断程序是否是第一次运行
     * 3.自定义字体
     * 5.Activity全屏主题
     * @param savedInstanceState
     * @param persistentState
     */

    private TextView tv_splash;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        intiview();
    }

    //延时用Handler
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 1001:
                    //判断程序是否是第一次运行
                    if(isFirst()){
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    }else{
//                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));//跳登陆注册页
                    }

                    finish();
                    break;
            }
        }
    };

    //判断程序是否是第一次运行
    private boolean isFirst(){
        boolean isFirst = ShareUtils.getBoolean(this, "isFirst", true);

        if(isFirst){
            //
            ShareUtils.putBoolean(this, "isFirst", false);
            return true;
        }else{
            //不是第一次运行
            return false;
        }
    }

    //禁止返回键
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    //初始化TextView
    private void intiview(){
        //延时2000ms
        handler.sendEmptyMessageDelayed(1001, 2000);

        tv_splash = (TextView) findViewById(R.id.tv_splash);

        //设置字体
        UtilTools.setFont(this, tv_splash);
    }
}
