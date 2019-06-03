package com.NaviDemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.Adapter.ReserveHistory;
import com.baidu.Const;
import com.example.admin.sitp_parkinglot.ParkingLot;
import com.example.admin.sitp_parkinglot.R;
import com.example.admin.sitp_parkinglot.sysClose;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class StoreCar extends AppCompatActivity {
    private Button StoreCarBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sysClose.getInstance().addActivity(this);
        setContentView(R.layout.activity_store_car);
        StoreCarBtn=(Button)findViewById(R.id.StoreCarBtn);
        final CustomView customView = (CustomView) findViewById(R.id.custom_view);
        customView.setVisibility(View.INVISIBLE);
        StoreCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = Const.NaviDisplay_info.getObjectId();
                ReserveHistory newhis = new ReserveHistory();
                newhis.setStatus("已生效");
                newhis.update(id, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Timer timer1 = new Timer();
                            timer1.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    //do something
                                    Toast.makeText(StoreCar.this, "存车中，正在等待系统响应.....", Toast.LENGTH_SHORT).show();

                                }
                            },8000);//延时2s执行
                            customView.setVisibility(View.VISIBLE);
                            customView.circleAnimation();
                            Toast.makeText(StoreCar.this, "存车成功", Toast.LENGTH_SHORT).show();
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    //do something
                                }
                            },1000);//延时2s执行

                            Intent Main = new Intent(StoreCar.this, ParkingLot.class);
                            startActivity(Main);
                        } else {
                            Toast.makeText(StoreCar.this, "存车失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
