package com.baidu;

import android.icu.text.SimpleDateFormat;

import com.Adapter.ParkingDetail;
import com.Adapter.ReserveHistory;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by admin on 2019-01-31.
 */

public class Const {
    public static double LONGITUDE = 0;//经度
    public static double LATITUDE = 0;//纬度
    public static double ParkLatitude=0;
    public static double ParkLongtitude=0;
    public static String ParkName="";
    public static ReserveHistory SendDetail=new ReserveHistory();
    public static ReserveHistory NaviDisplay_info=new ReserveHistory();
    public static ReserveHistory OutCar_info=new ReserveHistory();
    public static ParkingDetail ReserveJudge=new ParkingDetail();
    public static int guidetype=0;

    private static long getTimeMillis(String strTime) {
        long returnMillis = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = null;
        try {
            d = sdf.parse(strTime);
            returnMillis = d.getTime();
        } catch (ParseException e) {
            ;
        }
        return returnMillis;
    }
    public static int getTimeDifference(int type,String starTime, String endTime) {
        long longStart = getTimeMillis(starTime); //获取开始时间毫秒数
        long longEnd = getTimeMillis(endTime);  //获取结束时间毫秒数
        long longExpend = longEnd - longStart;  //获取时间差

        long longHours = longExpend / (60 * 60 * 1000); //根据时间差来计算小时数
        long longMinutes = (longExpend - longHours * (60 * 60 * 1000)) / (60 * 1000);   //根据时间差来计算分钟数

        int money=0;
        if(type==0){
            money+=longHours*15;
            if(longMinutes>0)
                money+=15;
        }
        else if(type==1){
            money+=longHours*10;
            if(longMinutes>0)
                money+=10;
        }
        else if(type==2){
            money+=longHours*5;
            if(longMinutes>0)
                money+=5;
        }
        return money;

    }
}
