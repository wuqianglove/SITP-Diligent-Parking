package com.example.admin.sitp_parkinglot;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {
    private Button WeHtml;
    private TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sysClose.getInstance().addActivity(this);
        setContentView(R.layout.activity_about_us);
        textview = (TextView) findViewById(R.id.textView31);
        textview.setMovementMethod(LinkMovementMethod.getInstance());
        WeHtml=(Button)findViewById(R.id.button9);
        WeHtml.setOnClickListener(mLister);
        getWindow().setTitle("关于我们");

    }
    public View.OnClickListener mLister=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.button9:
                    Wehome();
                    break;
            }
        }
    };
    public void Wehome(){
        Intent intent=new Intent();
        intent.setData(Uri.parse("https://github.com/Nevertorlate"));
        intent.setAction(Intent.ACTION_VIEW);
        this.startActivity(intent);
    }
}
