package com.OutCar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.Adapter.ReserveDisplayAdapter;
import com.Adapter.ReserveHistory;
import com.baidu.Const;
import com.example.admin.sitp_parkinglot.R;
import com.example.admin.sitp_parkinglot.sysClose;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class EffectDisplay extends AppCompatActivity {
    private ListView display;
    private List<ReserveHistory> fruitList = new ArrayList<ReserveHistory>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sysClose.getInstance().addActivity(this);
        setContentView(R.layout.activity_effect_display);
        BmobQuery<ReserveHistory> query = new BmobQuery<>();
        query.addWhereEqualTo("ReserveUsername", BmobUser.getCurrentUser().getUsername());
        //query.addWhereEqualTo("Status","已生效");
        query.findObjects(new FindListener<ReserveHistory>() {
            @Override
            public void done(List<ReserveHistory> list, BmobException e) {
                if(e==null){
                    Toast.makeText(EffectDisplay.this,"预约记录查询成功",Toast.LENGTH_SHORT).show();
                    fruitList.addAll(list);
                    fruitList=list;

                    final List<ReserveHistory> tmp=new ArrayList<ReserveHistory>();
                    for(int i=0;i<fruitList.size();i++){
                        if(fruitList.get(i).getStatus().equals("已生效")){
                            tmp.add(fruitList.get(i));
                        }
                    }
                    ReserveDisplayAdapter adapter = new ReserveDisplayAdapter(EffectDisplay.this, R.layout.reserveinfo_item, tmp);
                    display =(ListView) findViewById(R.id.list_reserveOut);
                    display.setAdapter(adapter);
                    display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            //Const.SendDetail=fruitList.get(i);
                            Const.OutCar_info=tmp.get(i);
                            Intent intent=new Intent(EffectDisplay.this,OutCarDetail.class);
                            startActivity(intent);
                        }
                    });
                }
                else{
                    Toast.makeText(EffectDisplay.this,"查询失败"+e.getMessage()+","+e.getErrorCode(),Toast.LENGTH_SHORT).show();
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
}
