package com.example.admin.sitp_parkinglot;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.baidu.Const;

import java.text.ParseException;
import java.util.Date;
//大型车位15/h,中型10/h，小型5/h，四种服务均为30元
public class ReserveDetailDisplay extends AppCompatActivity {
    private TextView CarNumber;
    private TextView Money;
    private TextView ParkType;
    private TextView ParkName;
    private TextView status;
    private TextView location;
    private TextView service;
    private static int money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sysClose.getInstance().addActivity(this);
        setContentView(R.layout.activity_reserve_detail_display);
        CarNumber=(TextView)findViewById(R.id.DetailCarNumber);
        Money=(TextView)findViewById(R.id.DetailMoney);
        ParkType=(TextView)findViewById(R.id.DetailType);
        ParkName=(TextView)findViewById(R.id.DetailParkName);
        status=(TextView)findViewById(R.id.DetailStatus);
        location=(TextView)findViewById(R.id.DetailLatitudeLongtitude);
        service=(TextView)findViewById(R.id.DetailService);
        CarNumber.setText(Const.SendDetail.getCarNumber());
        ParkType.setText(Const.SendDetail.getParkingType());
        ParkName.setText(Const.SendDetail.getParkingName());
        status.setText(Const.SendDetail.getStatus());
        location.setText(Const.SendDetail.getDstLatitude()+" ,"+ Const.SendDetail.getDstLongtitude());
        String ser=Const.SendDetail.getService();
        String SerText="";
        int num=Integer.parseInt(ser);
        money=0;
        if(num/1000==1){
            SerText+="充电服务";
            money=money+30;
        }

        if((num/100%10)==1){
            SerText+="  洗车服务";
            money=money+30;
        }

        if((num/10)%10==1){
            SerText+="  行车检查";
            money=money+30;
        }

        if(num%10==1){
            SerText+="  车辆保养";
            money=money+30;
        }
        service.setText(SerText);
        String date=Const.SendDetail.getUpdatedAt();
        if(Const.SendDetail.getStatus().equals("已生效")){
            int type=2;
            if(Const.SendDetail.getParkingType().equals("大型车位")){
                type=0;
            }
            else if(Const.SendDetail.getParkingType().equals("中型车位")){
                type=1;
            }
            else if(Const.SendDetail.getParkingType().equals("小型车位")){
                type=2;
            }
            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm");

            Date curDate =  new Date(System.currentTimeMillis());
            String str=formatter.format(curDate);
            money=money+getTimeDifference(type,date,str);
            Money.setText(Integer.toString(money));
        }else{
            Money.setText("--");
        }

    }
    public int getTimeDifference(int type,String starTime, String endTime) {
        String timeString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        int money=0;
        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endTime);

            long diff = parse1.getTime() - parse.getTime()+8*60*60*1000;

            long hour1 = diff / (60 * 60 * 1000);
            long min1 = ((diff / (60 * 1000)) - hour1 * 60);
            if(type==0){
                money+=hour1*15;
                if(min1>0)
                    money+=15;
            }
            else if(type==1){
                money+=hour1*10;
                if(min1>0)
                    money+=10;
            }
            else if(type==2){
                money+=hour1*5;
                if(min1>0)
                    money+=5;
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return money;

    }
}
