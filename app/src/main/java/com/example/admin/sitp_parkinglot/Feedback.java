package com.example.admin.sitp_parkinglot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import cn.bmob.v3.BmobUser;

public class Feedback extends AppCompatActivity {
    private EditText feedinfo;
    private Button signUp;
    private EditText MyAdviceTel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sysClose.getInstance().addActivity(this);
        setContentView(R.layout.activity_feedback);
        feedinfo=(EditText)findViewById(R.id.help_feedback);
        signUp=(Button)findViewById(R.id.button8);
        MyAdviceTel=(EditText)findViewById(R.id.MyAdviceTel);
        signUp.setOnClickListener(mLister);
        getWindow().setTitle("反馈与建议");
    }
    public View.OnClickListener mLister=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.button8:
                    deal();
                    break;
            }
        }
    };
    public void deal(){
        String feedLine=feedinfo.getText().toString();
        if(feedLine.length()>501){
            Toast.makeText(Feedback.this,"字数超过限制,请修改",Toast.LENGTH_SHORT).show();
            return;
        }
        if(feedLine.length()==0){
            Toast.makeText(Feedback.this,"内容为空,请修改",Toast.LENGTH_SHORT).show();
            return;
        }
        sendMailMy();
    }
    public void sendMailMy(){
        Intent i = new Intent(Intent.ACTION_SEND);
        // i.setType("text/plain"); //模拟器请使用这行
        i.setType("message/rfc822"); // 真机上使用这行
        i.putExtra(Intent.EXTRA_EMAIL,
                new String[] { "2458048592@qq.com" });
        i.putExtra(Intent.EXTRA_SUBJECT, "用户"+BmobUser.getCurrentUser().getUsername()+"的建议");
        i.putExtra(Intent.EXTRA_TEXT,feedinfo.getText().toString() );
        startActivity(Intent.createChooser(i,"Select email application."));
        feedinfo.setText("");
        MyAdviceTel.setText("");
    }
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.163.com");
//        Session session = Session.getInstance(props, null);
//        try {
//            MimeMessage msg = new MimeMessage(session);
//            msg.setFrom("15879036879@163.com");
//            msg.setRecipients(Message.RecipientType.TO, "2458048592@qq.com");
//            final Common UserClass=(Common)getApplication();
//            msg.setSubject("来自"+UserClass.getUsername()+"的反馈");
//            msg.setSentDate(new Date());
//            msg.setText(feedinfo.getText().toString());
//            Transport.send(msg, "15879036879@163.com", "helloworld1999");
//        } catch (MessagingException mex) {
//            System.out.println("send failed, exception: " + mex);
//            Toast.makeText(Feedback.this,"send failed, exception: " + mex,Toast.LENGTH_SHORT).show();
//        }
    //}

}
