package com.example.admin.sitp_parkinglot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;


public class MoneyLeft extends AppCompatActivity {
    private TextView MoneyLft;
    private Button MoneyUp;
    private Button Updatemoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sysClose.getInstance().addActivity(this);
        setContentView(R.layout.activity_money_left);
        MoneyLft=(TextView)findViewById(R.id.textView32);
        MoneyUp=(Button)findViewById(R.id.button10);
        BmobUser user=BmobUser.getCurrentUser();
        BmobQuery<_User> bmobQuery = new BmobQuery<_User>();
        bmobQuery.getObject(user.getObjectId(), new QueryListener<_User>(){
            @Override
            public void done(_User object, BmobException e) {
                if(e==null){
                    Toast.makeText(MoneyLeft.this,"查询成功", Toast.LENGTH_SHORT).show();
                    String s = String.valueOf(object.getMoney());
                    MoneyLft.setText(s);
                }else{
                    Toast.makeText(MoneyLeft.this,"余额查询失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        MoneyUp.setOnClickListener(mLister);
        Updatemoney=(Button)findViewById(R.id.button12);
        Updatemoney.setOnClickListener(mLister);
    }


    public View.OnClickListener mLister=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.button10:
                    Intent RechageMoney=new Intent(MoneyLeft.this,Recharge.class);
                    startActivity(RechageMoney);
                    //MoneyLeft.super.onDestroy();
                    break;
                case R.id.button12:
                    BmobUser user=BmobUser.getCurrentUser();
                    BmobQuery<_User> bmobQuery = new BmobQuery<_User>();
                    bmobQuery.getObject(user.getObjectId(), new QueryListener<_User>(){
                        @Override
                        public void done(_User object, BmobException e) {
                            if(e==null){
                                Toast.makeText(MoneyLeft.this,"查询成功", Toast.LENGTH_SHORT).show();
                                String s = String.valueOf(object.getMoney());
                                MoneyLft.setText(s);
                            }else{
                                Toast.makeText(MoneyLeft.this,"余额查询失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    break;
            }
        }
    };
}
