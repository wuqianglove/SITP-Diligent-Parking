package com.example.admin.sitp_parkinglot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.Adapter.ParkingDetail;
import com.Adapter.ReserveHistory;
import com.NaviDemo.NaviActivity;
import com.baidu.Const;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class ServiceChoose extends AppCompatActivity {
    private Button NaviButton;
    private RadioGroup PowerGroup;
    private RadioGroup WashGroup;
    private RadioGroup CheckGroup;
    private RadioGroup ProtectGroup;
    private RadioGroup CarSize;
    private EditText Carnum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sysClose.getInstance().addActivity(this);
        setContentView(R.layout.activity_service_choose);
        NaviButton=(Button)findViewById(R.id.buttonEnsure);
        PowerGroup=(RadioGroup)findViewById(R.id.PowerGroup);
        WashGroup=(RadioGroup)findViewById(R.id.WashGroup);
        CheckGroup=(RadioGroup)findViewById(R.id.CheckGroup);
        ProtectGroup=(RadioGroup)findViewById(R.id.ProtectGroup);
        CarSize=(RadioGroup)findViewById(R.id.CarSize);
        Carnum=(EditText)findViewById(R.id.editText11);
        NaviButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(is_Car_number_NO()) {
                    final RadioButton BigCar=(RadioButton)findViewById(R.id.radioButton10);
                    final RadioButton MiddleCar=(RadioButton)findViewById(R.id.radioButton9);
                    final RadioButton SmallCar=(RadioButton)findViewById(R.id.radioButton5);
                    ParkingDetail newPark=new ParkingDetail();
                    if(BigCar.isChecked()){
                        int num=Integer.parseInt(Const.ReserveJudge.getBigLeft());
                        if(num>0){
                            num=num-1;
                            newPark.setBigLeft(Integer.toString(num));
                            newPark.update(Const.ReserveJudge.getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){


                                        final ReserveHistory current = new ReserveHistory();
                                        current.setDstLatitude(Double.toString(Const.ParkLatitude));
                                        current.setDstLongtitude(Double.toString(Const.ParkLongtitude));
                                        current.setParkingName(Const.ParkName);
                                        current.setReserveUsername(BmobUser.getCurrentUser().getUsername());
                                        String ServiceAll = "";
                                        RadioButton btg1 = (RadioButton) findViewById(PowerGroup.getCheckedRadioButtonId());
                                        if (btg1.getText().equals("是"))
                                            ServiceAll += "1";
                                        else
                                            ServiceAll += "0";


                                        RadioButton btg2 = (RadioButton) findViewById(WashGroup.getCheckedRadioButtonId());
                                        if (btg2.getText().equals("是"))
                                            ServiceAll += "1";
                                        else
                                            ServiceAll += "0";

                                        RadioButton btg3 = (RadioButton) findViewById(CheckGroup.getCheckedRadioButtonId());
                                        if (btg3.getText().equals("是"))
                                            ServiceAll += "1";
                                        else
                                            ServiceAll += "0";

                                        RadioButton btg4 = (RadioButton) findViewById(ProtectGroup.getCheckedRadioButtonId());
                                        if (btg4.getText().equals("是"))
                                            ServiceAll += "1";
                                        else
                                            ServiceAll += "0";

                                        RadioButton btg5 = (RadioButton) findViewById(CarSize.getCheckedRadioButtonId());

                                        if(BigCar.isChecked()){
                                            current.setParkingType("大型车位");
                                        }
                                        else if(MiddleCar.isChecked()){
                                            current.setParkingType("中型车位");
                                        }
                                        else if(SmallCar.isChecked()){
                                            current.setParkingType("小型车位");
                                        }

                                        current.setService(ServiceAll);
                                        current.setStatus("未生效");
                                        current.setCarNumber(Carnum.getText().toString());
                                        current.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String s, BmobException e) {
                                                if (e == null) {
                                                    Toast.makeText(getApplication(), "预约信息添加成功，未生效，添加数据成功，返回objectId为：" + s, Toast.LENGTH_SHORT).show();
                                                    Const.NaviDisplay_info=current;
                                                    Intent NaviAct = new Intent(ServiceChoose.this, NaviActivity.class);
                                                    startActivity(NaviAct);
                                                } else {
                                                    Toast.makeText(getApplication(), "创建数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });;
                                    }else{
                                        Toast.makeText(getApplication(),"车位更新出错:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(getApplication(),"车位不足，预约失败", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else if(MiddleCar.isChecked()){
                        int num=Integer.parseInt(Const.ReserveJudge.getMiddleLeft());
                        if(num>0){
                            num=num-1;
                            newPark.setMiddlelLeft(Integer.toString(num));
                            newPark.update(Const.ReserveJudge.getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){


                                        final ReserveHistory current = new ReserveHistory();
                                        current.setDstLatitude(Double.toString(Const.ParkLatitude));
                                        current.setDstLongtitude(Double.toString(Const.ParkLongtitude));
                                        current.setParkingName(Const.ParkName);
                                        current.setReserveUsername(BmobUser.getCurrentUser().getUsername());
                                        String ServiceAll = "";
                                        RadioButton btg1 = (RadioButton) findViewById(PowerGroup.getCheckedRadioButtonId());
                                        if (btg1.getText().equals("是"))
                                            ServiceAll += "1";
                                        else
                                            ServiceAll += "0";


                                        RadioButton btg2 = (RadioButton) findViewById(WashGroup.getCheckedRadioButtonId());
                                        if (btg2.getText().equals("是"))
                                            ServiceAll += "1";
                                        else
                                            ServiceAll += "0";

                                        RadioButton btg3 = (RadioButton) findViewById(CheckGroup.getCheckedRadioButtonId());
                                        if (btg3.getText().equals("是"))
                                            ServiceAll += "1";
                                        else
                                            ServiceAll += "0";

                                        RadioButton btg4 = (RadioButton) findViewById(ProtectGroup.getCheckedRadioButtonId());
                                        if (btg4.getText().equals("是"))
                                            ServiceAll += "1";
                                        else
                                            ServiceAll += "0";

                                        RadioButton btg5 = (RadioButton) findViewById(CarSize.getCheckedRadioButtonId());

                                        if(BigCar.isChecked()){
                                            current.setParkingType("大型车位");
                                        }
                                        else if(MiddleCar.isChecked()){
                                            current.setParkingType("中型车位");
                                        }
                                        else if(SmallCar.isChecked()){
                                            current.setParkingType("小型车位");
                                        }

                                        current.setService(ServiceAll);
                                        current.setStatus("未生效");
                                        current.setCarNumber(Carnum.getText().toString());
                                        current.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String s, BmobException e) {
                                                if (e == null) {
                                                    Toast.makeText(getApplication(), "预约信息添加成功，未生效，添加数据成功，返回objectId为：" + s, Toast.LENGTH_SHORT).show();
                                                    Const.NaviDisplay_info=current;
                                                    Intent NaviAct = new Intent(ServiceChoose.this, NaviActivity.class);
                                                    startActivity(NaviAct);
                                                } else {
                                                    Toast.makeText(getApplication(), "创建数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                                    }else{
                                        Toast.makeText(getApplication(),"车位更新出错:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(getApplication(),"车位不足，预约失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(SmallCar.isChecked()){
                        int num=Integer.parseInt(Const.ReserveJudge.getSmallLeft());
                        if(num>0){
                            num=num-1;
                            newPark.setSmallLeft(Integer.toString(num));
                            newPark.update(Const.ReserveJudge.getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){


                                        final ReserveHistory current = new ReserveHistory();
                                        current.setDstLatitude(Double.toString(Const.ParkLatitude));
                                        current.setDstLongtitude(Double.toString(Const.ParkLongtitude));
                                        current.setParkingName(Const.ParkName);
                                        current.setReserveUsername(BmobUser.getCurrentUser().getUsername());
                                        String ServiceAll = "";
                                        RadioButton btg1 = (RadioButton) findViewById(PowerGroup.getCheckedRadioButtonId());
                                        if (btg1.getText().equals("是"))
                                            ServiceAll += "1";
                                        else
                                            ServiceAll += "0";


                                        RadioButton btg2 = (RadioButton) findViewById(WashGroup.getCheckedRadioButtonId());
                                        if (btg2.getText().equals("是"))
                                            ServiceAll += "1";
                                        else
                                            ServiceAll += "0";

                                        RadioButton btg3 = (RadioButton) findViewById(CheckGroup.getCheckedRadioButtonId());
                                        if (btg3.getText().equals("是"))
                                            ServiceAll += "1";
                                        else
                                            ServiceAll += "0";

                                        RadioButton btg4 = (RadioButton) findViewById(ProtectGroup.getCheckedRadioButtonId());
                                        if (btg4.getText().equals("是"))
                                            ServiceAll += "1";
                                        else
                                            ServiceAll += "0";

                                        RadioButton btg5 = (RadioButton) findViewById(CarSize.getCheckedRadioButtonId());

                                        if(BigCar.isChecked()){
                                            current.setParkingType("大型车位");
                                        }
                                        else if(MiddleCar.isChecked()){
                                            current.setParkingType("中型车位");
                                        }
                                        else if(SmallCar.isChecked()){
                                            current.setParkingType("小型车位");
                                        }

                                        current.setService(ServiceAll);
                                        current.setStatus("未生效");
                                        current.setCarNumber(Carnum.getText().toString());
                                        current.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String s, BmobException e) {
                                                if (e == null) {
                                                    Toast.makeText(getApplication(), "预约信息添加成功，未生效，添加数据成功，返回objectId为：" + s, Toast.LENGTH_SHORT).show();
                                                    Const.NaviDisplay_info=current;
                                                    Intent NaviAct = new Intent(ServiceChoose.this, NaviActivity.class);
                                                    startActivity(NaviAct);
                                                } else {
                                                    Toast.makeText(getApplication(), "创建数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                                    }else{
                                        Toast.makeText(getApplication(),"车位更新出错:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(getApplication(),"车位不足，预约失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    //current.setDstLatitude();
//                    Intent NaviAct = new Intent(ServiceChoose.this, NaviActivity.class);
//                    startActivity(NaviAct);
                }
                else{
                    Toast.makeText(getApplication(), "车牌格式错误,请您修改", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public  boolean is_Car_number_NO() {
        String car_number=Carnum.getText().toString().trim();
        String car_num_Regex = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z][A-Z][警京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]?[A-Z0-9]{4}[A-Z0-9挂学警港澳]$";
        if(car_number.matches(car_num_Regex)){
            //Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
            return true;
        }else{
            return false;
        }
    }
}
