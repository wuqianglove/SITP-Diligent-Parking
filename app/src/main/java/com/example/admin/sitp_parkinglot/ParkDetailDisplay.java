package com.example.admin.sitp_parkinglot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Adapter.ParkingDetail;
import com.baidu.Const;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class ParkDetailDisplay extends AppCompatActivity {
    private Button NaviButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sysClose.getInstance().addActivity(this);
        setContentView(R.layout.activity_park_detail_display);
        Bundle bundle=this.getIntent().getExtras();
        final String Name=bundle.getString("Name");
        final String Address=bundle.getString("Address");
        String Distance=bundle.getString("Distance");
        TextView Addr=(TextView)findViewById(R.id.parking_detail_address);
        Addr.setText(Address);

        TextView NamePark=(TextView)findViewById(R.id.parking_detail_name);
        NamePark.setText(Name);

        TextView dis=(TextView)findViewById(R.id.parking_detail_distance);
        Distance=Distance.substring(Distance.indexOf("为")+1,Distance.indexOf("米"));
        dis.setText(Distance);

        final TextView BigPark=(TextView)findViewById(R.id.parking_detail_left_count);
        final TextView MiddlePark=(TextView)findViewById(R.id.parking_detail_total_count);
        final TextView SmallPark=(TextView)findViewById(R.id.parking_detail_freetime);
        BmobQuery<ParkingDetail> query = new BmobQuery<ParkingDetail>();
        query.addWhereEqualTo("locations",Address);
        query.findObjects(new FindListener<ParkingDetail>() {
            @Override
            public void done(List<ParkingDetail> object, BmobException e) {
                if(e==null){
                    BigPark.setText(object.get(0).getBigLeft().toString());
                    MiddlePark.setText(object.get(0).getMiddleLeft().toString());
                    SmallPark.setText(object.get(0).getSmallLeft().toString());
                    Const.ReserveJudge=object.get(0);
                }else{
                    BigPark.setText(Integer.toString(10));
                    MiddlePark.setText(Integer.toString(15));
                    SmallPark.setText(Integer.toString(30));
                    final ParkingDetail newPark=new ParkingDetail();
                    newPark.setBigLeft("10");
                    newPark.setSmallLeft("30");
                    newPark.setMiddlelLeft("15");
                    newPark.setParkingName(Name);
                    newPark.setLocations(Address);
                    newPark.setLatitude(Double.toString(Const.ParkLatitude));
                    newPark.setLongtitude(Double.toString(Const.ParkLongtitude));
                    newPark.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Const.ReserveJudge=newPark;
                            } else {
                                Toast.makeText(getApplication(), "创建数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            }
        });

        NaviButton=(Button)findViewById(R.id.Service);
        NaviButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ServiceGet=new Intent(ParkDetailDisplay.this, ServiceChoose.class);
                startActivity(ServiceGet);
            }
        });
    }
}
