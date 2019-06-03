package com.example.admin.sitp_parkinglot;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity {
    private Button mloginButton;
    private Button mRegisterButton;
    private EditText mUsername;
    private EditText mPassword;
    private SQLiteDatabase mSQLiteDatabase = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "ec83eb80367b0004ea1c8a5a48b1fa47");
        mloginButton=(Button)findViewById(R.id.button4);
        mRegisterButton=(Button)findViewById(R.id.button3);
        mUsername=(EditText)findViewById(R.id.editText3);
        mPassword=(EditText)findViewById(R.id.editText4);
        mloginButton.setOnClickListener(mLister);
        mRegisterButton.setOnClickListener(mLister);
        getWindow().setTitle("登录");

    }

    public View.OnClickListener mLister=new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.button4:
                    register();
                    break;
                case R.id.button3:
                    login();
                    break;
            }
        }
    };
    public void register(){
        Intent Register=new Intent(MainActivity.this,RegisterAct.class);
        startActivity(Register);
    }
    public void login(){
        final String username=mUsername.getText().toString();
        final String password=mPassword.getText().toString();

        BmobUser userlogin=new BmobUser();
        userlogin.setUsername(mUsername.getText().toString());
        userlogin.setPassword(mPassword.getText().toString());
        userlogin.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if(e==null){
                    Toast.makeText(MainActivity.this,bmobUser.getUsername()+"登录成功",Toast.LENGTH_SHORT).show();
                    final Common UserClass=(Common)getApplication();
                    UserClass.setUsername(username);
                    UserClass.setPassword(password);
                    Intent MainWin=new Intent(MainActivity.this,ParkingLot.class);
                    startActivity(MainWin);
                    mUsername.setText("");
                    mPassword.setText("");
                }else {
                    Log.e("登录失败", "原因: ", e);
                    Toast.makeText(MainActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
