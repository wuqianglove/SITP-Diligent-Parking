package com.example.admin.sitp_parkinglot;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class Recharge extends AppCompatActivity {
    private EditText Loginpass;
    private EditText Paypass;
    private EditText PayNum;
    private Spinner PayType;
    private TextView username;
    private Button pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sysClose.getInstance().addActivity(this);
        setContentView(R.layout.activity_recharge);
        Loginpass=(EditText)findViewById(R.id.editText9);
        PayNum=(EditText)findViewById(R.id.editText2);
        Paypass=(EditText)findViewById(R.id.editText10);
        pay=(Button)findViewById(R.id.button11);
        username=(TextView)findViewById(R.id.textView41);
        PayType=(Spinner)findViewById(R.id.spinner2);
        username.setText(BmobUser.getCurrentUser().getUsername());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                Recharge.this, android.R.layout.simple_spinner_item, getData());
        PayType.setAdapter(adapter);
        pay.setOnClickListener(mLister);
        Drawable PassIcon=getResources().getDrawable(R.drawable.lock);
        PassIcon.setBounds(0,0,21,21);
        Paypass.setCompoundDrawables(PassIcon,null,null,null);
        Loginpass.setCompoundDrawables(PassIcon,null,null,null);
    }
    private List<String> getData() {
        List<String> dataList = new ArrayList<String>();
        dataList.add("绑定的银行卡转向---账户");
        dataList.add("微信转向---账户");
        dataList.add("支付宝转向---账户");
        return dataList;
    }

    public View.OnClickListener mLister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button11:
                    payAct();
                    break;
            }
        }
    };
    public void payAct(){
        String s = PayNum.getText().toString();
        int num=Integer.parseInt(s);
        if(num>500){
            Toast.makeText(Recharge.this,"充值金额过多,请重新输入",Toast.LENGTH_SHORT).show();
            return;
        }
        String passlogin=Loginpass.getText().toString();
        final Common UserClass=(Common)getApplication();
        String passtrue=UserClass.getPassword();
        if(!passlogin.equals(passtrue)){
            Toast.makeText(Recharge.this,"登录密码不正确",Toast.LENGTH_SHORT).show();
            return;
        }
        if(Paypass.getText().toString().equals("")){
            Toast.makeText(Recharge.this,"支付密码错误",Toast.LENGTH_SHORT).show();
            return;
        }

        BmobUser user=BmobUser.getCurrentUser();
        BmobQuery<_User> bmobQuery = new BmobQuery<_User>();
        String s1=user.getObjectId();
        bmobQuery.getObject(s1, new QueryListener<_User>(){
            @Override
            public void done(_User object, BmobException e) {
                if(e==null){
                    String s = String.valueOf(object.getMoney());
                    int moneytmp=Integer.parseInt(s);
                    moneytmp=moneytmp+Integer.parseInt(PayNum.getText().toString());
                    _User p2=new _User();
                    p2.setMoney(moneytmp);
                    p2.update(BmobUser.getCurrentUser().getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(Recharge.this,"充值成功",Toast.LENGTH_SHORT).show();
                                Loginpass.setText("");
                                PayNum.setText("");
                                Paypass.setText("");
                            }
                            else{
                                Toast.makeText(Recharge.this,"充值失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(Recharge.this,"充值余额查询失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
