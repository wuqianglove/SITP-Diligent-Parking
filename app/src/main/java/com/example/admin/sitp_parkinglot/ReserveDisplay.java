package com.example.admin.sitp_parkinglot;

import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.Adapter.ParkingAdapter;
import com.Adapter.ReserveDisplayAdapter;
import com.Adapter.ReserveHistory;
import com.baidu.Const;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ReserveDisplay extends AppCompatActivity {
    private ListView display;
    private List<ReserveHistory> fruitList = new ArrayList<ReserveHistory>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sysClose.getInstance().addActivity(this);
        setContentView(R.layout.activity_reserve_display);

        BmobQuery<ReserveHistory> query = new BmobQuery<>();
        query.addWhereEqualTo("ReserveUsername", BmobUser.getCurrentUser().getUsername());
        query.findObjects(new FindListener<ReserveHistory>() {
            @Override
            public void done(List<ReserveHistory> list, BmobException e) {
                if(e==null){
                    Toast.makeText(ReserveDisplay.this,"预约记录查询成功",Toast.LENGTH_SHORT).show();
                    fruitList.addAll(list);
                    fruitList=list;
                    ReserveDisplayAdapter adapter = new ReserveDisplayAdapter(ReserveDisplay.this, R.layout.reserveinfo_item, fruitList);
                    display =(ListView) findViewById(R.id.list_reserve);
                    display.setAdapter(adapter);
                    display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Const.SendDetail=fruitList.get(i);
                            Intent intent=new Intent(ReserveDisplay.this,ReserveDetailDisplay.class);
                            startActivity(intent);
                        }
                    });
                }
                else{
                    Toast.makeText(ReserveDisplay.this,"查询失败"+e.getMessage()+","+e.getErrorCode(),Toast.LENGTH_SHORT).show();
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
}
