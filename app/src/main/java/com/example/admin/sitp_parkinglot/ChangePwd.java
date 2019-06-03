package com.example.admin.sitp_parkinglot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ChangePwd extends AppCompatActivity {
    private EditText OriginalPwd;
    private EditText NewPwd;
    private EditText EnsurePwd;
    private Button SignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sysClose.getInstance().addActivity(this);
        setContentView(R.layout.activity_change_pwd);
        OriginalPwd=(EditText)findViewById(R.id.editTextOriginal);
        NewPwd=(EditText)findViewById(R.id.editTextNew);
        EnsurePwd=(EditText)findViewById(R.id.editTextEnsure);
        SignUp=(Button)findViewById(R.id.button6);
        SignUp.setOnClickListener(mLister);
        getWindow().setTitle("修改密码");
    }

    public View.OnClickListener mLister=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.button6:
                    deal();
                    break;
            }
        }
    };
    public void deal(){
        String getOriginal=OriginalPwd.getText().toString();
        String NewPass=NewPwd.getText().toString();
        String EnsurePass=EnsurePwd.getText().toString();

        final Common UserClass=(Common)getApplication();
        String PassTmp=UserClass.getPassword();
        if(!PassTmp.equals(getOriginal)){
            Toast.makeText(ChangePwd.this,"原密码错误\n修改密码失败",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!NewPass.equals(EnsurePass)){
            Toast.makeText(ChangePwd.this,"两次密码输入不一致\n修改密码失败",Toast.LENGTH_SHORT).show();
            return;
        }

        if(NewPass==""){
            Toast.makeText(ChangePwd.this,"新密码为空\n修改密码失败",Toast.LENGTH_SHORT).show();
            return;
        }
        BmobUser UpdateUser=new BmobUser();
        UpdateUser.setPassword(NewPass);
        BmobUser bmobUser=BmobUser.getCurrentUser();
        UpdateUser.update(bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(ChangePwd.this,"修改密码成功",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ChangePwd.this,"修改密码失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
