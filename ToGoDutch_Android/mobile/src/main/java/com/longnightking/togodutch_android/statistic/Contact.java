package com.longnightking.togodutch_android.statistic;

/**
 * Created by longNightKing on 4/22/15.
 */
public class Contact {

    private String mName;

    private double mPayment;

    public Contact(String name){
        this.mName = name;
        mPayment = 0;
    }

    public void addPayment(double payment){
        this.mPayment += payment;
    }

    public double getPayment(){
        return mPayment;
    }

    public void setContactName(String name){
        this.mName = name;
    }

    public String getContactName(){
        return mName;
    }
}
