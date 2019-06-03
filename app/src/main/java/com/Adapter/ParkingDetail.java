package com.Adapter;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2019-02-01.
 */

public class ParkingDetail extends BmobObject {
    private String SmallLeft;
    private String MiddleLeft;
    private String BigLeft;
    private String ParkingName;
    private String locations;
    private String Latitude;
    private String Longtitude;
    public String getSmallLeft(){
        return SmallLeft;
    }
    public String getMiddleLeft(){
        return MiddleLeft;
    }
    public String getBigLeft(){
        return BigLeft;
    }
    public String getParkingName(){
        return ParkingName;
    }

    public String getLocations(){
        return locations;
    }
    public String getLatitude(){
        return Latitude;
    }
    public String getLongtitude(){
        return Longtitude;
    }


    public void setSmallLeft(String Small){
        SmallLeft=Small;
    }
    public void setMiddlelLeft(String Middle){
        MiddleLeft=Middle;
    }
    public void setBigLeft(String Big){
        BigLeft=Big;
    }
    public void setParkingName(String name){
        ParkingName=name;
    }
    public void setLocations(String location){
        locations=location;
    }
    public void setLatitude(String Latitude1){
        Latitude=Latitude1;
    }
    public void setLongtitude(String Longtitude1){
        Longtitude=Longtitude1;
    }
}
