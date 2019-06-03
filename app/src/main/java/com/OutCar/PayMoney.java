package com.OutCar;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Adapter.ParkingDetail;
import com.Adapter.ReserveHistory;
import com.baidu.Const;
import com.example.admin.sitp_parkinglot.ParkingLot;
import com.example.admin.sitp_parkinglot.R;
import com.example.admin.sitp_parkinglot.Recharge;
import com.example.admin.sitp_parkinglot._User;
import com.example.admin.sitp_parkinglot.sysClose;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class PayMoney extends AppCompatActivity {
    private Button Pay;
    private TextView MoneyNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sysClose.getInstance().addActivity(this);
        setContentView(R.layout.activity_pay_money);
        Pay=(Button)findViewById(R.id.OutCarBtn);
        MoneyNum=(TextView) findViewById(R.id.MoneyNum);

        int money=0;
        String ser= Const.OutCar_info.getService();
        int num=Integer.parseInt(ser);
        money=0;
        if(num/1000==1){
            money=money+30;
        }

        if((num/100%10)==1){
            money=money+30;
        }

        if((num/10)%10==1){
            money=money+30;
        }

        if(num%10==1){
            money=money+30;
        }
        String date=Const.OutCar_info.getUpdatedAt();
        int type=2;
        if(Const.OutCar_info.getParkingType().equals("大型车位")){
            type=0;
        }
        else if(Const.OutCar_info.getParkingType().equals("中型车位")){
            type=1;
        }
        else if(Const.OutCar_info.getParkingType().equals("小型车位")){
            type=2;
        }
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate =  new Date(System.currentTimeMillis());
        String str=formatter.format(curDate);
        money=money+getTimeDifference(type,date,str);
        MoneyNum.setText("当前预约费用为:"+Integer.toString(money));


        final int finalMoney = money;
        Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobUser user=BmobUser.getCurrentUser();
                BmobQuery<_User> bmobQuery = new BmobQuery<_User>();
                bmobQuery.getObject(user.getObjectId(), new QueryListener<_User>(){
                    @Override
                    public void done(_User object, BmobException e) {
                        if(e==null){
                            int moneyNow=object.getMoney();
                            if(moneyNow< finalMoney){
                                Toast.makeText(PayMoney.this,"余额不足,请您充值"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                Intent recharge=new Intent(PayMoney.this, Recharge.class);
                                startActivity(recharge);
                            }
                            else{
                                _User newUser = new _User();
                                newUser.setMoney(moneyNow-finalMoney);
                                BmobUser bmobUser = BmobUser.getCurrentUser();
                                newUser.update(bmobUser.getObjectId(),new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){
                                        Toast.makeText(PayMoney.this, "支付成功", Toast.LENGTH_SHORT).show();
                                        ReserveHistory p=new ReserveHistory();
                                        p.setObjectId(Const.OutCar_info.getObjectId());
                                        p.delete(new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e==null){
                                                    ;
                                                }
                                                else{
                                                    Toast.makeText(PayMoney.this, "删除失败", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        if(Const.OutCar_info.getParkingType().equals("大型车位")){
                                            BmobQuery<ParkingDetail> bmobQuery=new BmobQuery<ParkingDetail>();
                                            bmobQuery.addWhereEqualTo("ParkingName",Const.OutCar_info.getParkingName());
                                            bmobQuery.findObjects(new FindListener<ParkingDetail>() {
                                                @Override
                                                public void done(List<ParkingDetail> list, BmobException e) {
                                                    if(e==null){
                                                        for(int i=0;i<list.size();i++){
                                                            if(list.get(i).getLongtitude()==Const.OutCar_info.getDstLongtitude()&&list.get(i).getLatitude()==Const.OutCar_info.getDstLatitude()){
                                                                String id=list.get(i).getObjectId();
                                                                ParkingDetail newPark1=new ParkingDetail();
                                                                int left=Integer.parseInt(list.get(i).getBigLeft());
                                                                left=left+1;
                                                                newPark1.setBigLeft(Integer.toString(left));
                                                                newPark1.update(id, new UpdateListener() {
                                                                    @Override
                                                                    public void done(BmobException e) {
                                                                        if(e==null){
                                                                            ;
                                                                        }
                                                                        else{
                                                                            Toast.makeText(PayMoney.this,"增加车位数失败",Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    }





                                                }
                                            });
                                        }
                                        else if(Const.OutCar_info.getParkingType().equals("中型车位")){
                                            BmobQuery<ParkingDetail> bmobQuery=new BmobQuery<ParkingDetail>();
                                            bmobQuery.addWhereEqualTo("ParkingName",Const.OutCar_info.getParkingName());
                                            bmobQuery.findObjects(new FindListener<ParkingDetail>() {
                                                @Override
                                                public void done(List<ParkingDetail> list, BmobException e) {
                                                    if(e==null){
                                                        for(int i=0;i<list.size();i++){
                                                            if(list.get(i).getLongtitude()==Const.OutCar_info.getDstLongtitude()&&list.get(i).getLatitude()==Const.OutCar_info.getDstLatitude()){
                                                                String id=list.get(i).getObjectId();
                                                                ParkingDetail newPark1=new ParkingDetail();
                                                                int left=Integer.parseInt(list.get(i).getMiddleLeft());
                                                                left=left+1;
                                                                newPark1.setMiddlelLeft(Integer.toString(left));
                                                                newPark1.update(id, new UpdateListener() {
                                                                    @Override
                                                                    public void done(BmobException e) {
                                                                        if(e==null){
                                                                            ;
                                                                        }
                                                                        else{
                                                                            Toast.makeText(PayMoney.this,"增加车位数失败",Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    }





                                                }
                                            });
                                        }
                                        else if(Const.OutCar_info.getParkingType().equals("小型车位")){
                                            BmobQuery<ParkingDetail> bmobQuery=new BmobQuery<ParkingDetail>();
                                            bmobQuery.addWhereEqualTo("ParkingName",Const.OutCar_info.getParkingName());
                                            bmobQuery.findObjects(new FindListener<ParkingDetail>() {
                                                @Override
                                                public void done(List<ParkingDetail> list, BmobException e) {
                                                    if(e==null){
                                                        for(int i=0;i<list.size();i++){
                                                            if(list.get(i).getLongtitude()==Const.OutCar_info.getDstLongtitude()&&list.get(i).getLatitude()==Const.OutCar_info.getDstLatitude()){
                                                                String id=list.get(i).getObjectId();
                                                                ParkingDetail newPark1=new ParkingDetail();
                                                                int left=Integer.parseInt(list.get(i).getSmallLeft());
                                                                left=left+1;
                                                                newPark1.setSmallLeft(Integer.toString(left));
                                                                newPark1.update(id, new UpdateListener() {
                                                                    @Override
                                                                    public void done(BmobException e) {
                                                                        if(e==null){
                                                                            ;
                                                                        }
                                                                        else{
                                                                            Toast.makeText(PayMoney.this,"增加车位数失败",Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    }





                                                }
                                            });
                                        }

                                        Toast.makeText(PayMoney.this,"系统正在为您取车....",Toast.LENGTH_SHORT).show();
                                        Toast.makeText(PayMoney.this,"系统正在为您取车....",Toast.LENGTH_SHORT).show();
                                        Toast.makeText(PayMoney.this,"系统正在为您取车....",Toast.LENGTH_SHORT).show();
                                        Toast.makeText(PayMoney.this,"系统正在为您取车....",Toast.LENGTH_SHORT).show();
                                        Toast.makeText(PayMoney.this,"系统正在为您取车....",Toast.LENGTH_SHORT).show();

                                        new Handler(new Handler.Callback() {
                                            //处理接收到的消息的方法
                                            @Override
                                            public boolean handleMessage(Message arg0) {
                                                //实现页面跳转
                                                Intent Park=new Intent(PayMoney.this, ParkingLot.class);
                                                startActivity(Park);
                                                return false;
                                            }
                                        }).sendEmptyMessageDelayed(0, 3000); //表示延时三秒进行任务的执行
                                        Toast.makeText(PayMoney.this,"取车成功!",Toast.LENGTH_SHORT).show();
//                                        Intent Park=new Intent(PayMoney.this, ParkingLot.class);
//                                        startActivity(Park);
                                    }else{
                                        Toast.makeText(PayMoney.this, "支付失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            }
                        }else{
                            Toast.makeText(PayMoney.this,"余额查询失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });




            }
        });
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
