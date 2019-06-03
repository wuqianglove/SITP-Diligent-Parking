package com.Adapter;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by admin on 2019-02-01.
 */

public class ReserveHistory extends BmobObject {
    private String status;
    private String ParkingType;
    private String DstLatitude;
    private String DstLongtitude;
    private String ReserveUsername;
    private String ParkingName;
    private String service;
    private String CarNumber;
    public String getStatus(){
        return status;
    }
    public String getParkingType(){
        return ParkingType;
    }
    public String getDstLatitude(){
        return DstLatitude;
    }
    public String getDstLongtitude(){
        return DstLongtitude;
    }
    public String getReserveUsername(){
        return ReserveUsername;
    }
    public String getParkingName(){
        return ParkingName;
    }
    public String getService(){
        return service;
    }
    public String getCarNumber(){
        return CarNumber;
    }
    public void setStatus(String status1){
        status=status1;
    }
    public void setParkingType(String Park){
        ParkingType=Park;
    }
    public void setDstLatitude(String Latitude){
        DstLatitude=Latitude;
    }
    public void setDstLongtitude(String Longtitude){
        DstLongtitude=Longtitude;
    }
    public void setReserveUsername(String name){
        ReserveUsername=name;
    }
    public void setParkingName(String name){
        ParkingName=name;
    }
    public void setService(String ser){
        service=ser;
    }
    public void setCarNumber(String Car){
        CarNumber=Car;
    }
    @Override
    public String toString() {
        return "Reserver{"+
                "Status="+status+"\'"+
                "ServiceChoose="+service+"\'"+
                "ParkingType="+ParkingType+"\'"+
                "DstLatitude="+DstLatitude+"\'"+
                "DstLongtitude="+DstLongtitude+"\'"+
                "ReserverUsername="+ReserveUsername+"\'"+
                "}";
    }

}
