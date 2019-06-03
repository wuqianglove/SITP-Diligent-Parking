package com.example.admin.sitp_parkinglot;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2019-01-24.
 */

public class _User extends BmobObject implements Comparable<_User>{
    private String username;
    private String password;
    private boolean mobilePhoneNumberVerified;
    private String mobilePhoneNumber;
    private int Money;
    private String Gender;
    private boolean emailVerified;
    private String email;
    public void setusername( String username ) {
        this.username = username;
    }
    public void setpassword( String password) {
        this.password = password;
    }
    public void setmobilePhoneNumberVerified( boolean mobilePhoneNumberVerified ) {
        this.mobilePhoneNumberVerified = mobilePhoneNumberVerified;
    }
    public void setMobilePhoneNumber( String mobilePhoneNumber ) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }
    public void setMoney( int Money ) {
        this.Money = Money;
    }
    public void setGender( String Gender ) {
        this.Gender = Gender;
    }
    public void setemailVerified( boolean emailVerified ) {
        this.emailVerified = emailVerified;
    }
    public void setemail( String email ) {
        this.email = email;
    }



    public String getusername() {
        return username;
    }
    public String getpassword() {
        return password;
    }
    public boolean getmobilePhoneNumberVerified() {
        return mobilePhoneNumberVerified;
    }
    public String getmobilePhoneNumber() {
        return mobilePhoneNumber;
    }
    public int getMoney() {
        return Money;
    }
    public String getGender() {
        return Gender;
    }
    public boolean getemailVerified() {
        return emailVerified;
    }
    public String getemail() {
        return email;
    }


    @Override
    public int compareTo(_User userClassJava) {
        return 0;
    }
}
