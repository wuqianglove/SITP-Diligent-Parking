package com.example.admin.sitp_parkinglot;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterAct extends AppCompatActivity {
    private Button mRegister;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mPhone;
    private EditText mEmail;
    private Spinner mGender;
    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        str="男";
        mRegister = (Button) findViewById(R.id.button);
        mUsername = (EditText) findViewById(R.id.editText);
        mPassword = (EditText) findViewById(R.id.editText5);
        mPhone = (EditText) findViewById(R.id.editText6);
        mEmail = (EditText) findViewById(R.id.editText7);
        mGender = (Spinner) findViewById(R.id.spinner);
        getWindow().setTitle("注册");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                RegisterAct.this, android.R.layout.simple_spinner_item, getData());
        mGender.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mRegister.setOnClickListener(mLister);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

        mGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                //拿到被选择项的值
                str = (String) mGender.getSelectedItem();
                //把该值传给 TextView
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    private List<String> getData() {
        List<String> dataList = new ArrayList<String>();
        dataList.add("男");
        dataList.add("女");
        return dataList;
    }

    public View.OnClickListener mLister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button:
                    register();
                    break;
            }
        }
    };

    public void register() {
        BmobUser user = new BmobUser();
        user.setValue("Money", 500);
        user.setUsername(mUsername.getText().toString());
        user.setPassword(mPassword.getText().toString());
        user.setMobilePhoneNumber(mPhone.getText().toString());
        user.setMobilePhoneNumberVerified(Boolean.TRUE);
        user.setEmail(mEmail.getText().toString());
        user.setEmailVerified(Boolean.TRUE);
        user.setValue("Gender", str);
        user.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    Toast.makeText(RegisterAct.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent MainWin = new Intent(RegisterAct.this, ParkingLot.class);
                    startActivity(MainWin);
                } else {
                    Log.e("注册失败", "原因:", e);
                    Toast.makeText(RegisterAct.this, "注册失败,原因:" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }


        });

    }
}
