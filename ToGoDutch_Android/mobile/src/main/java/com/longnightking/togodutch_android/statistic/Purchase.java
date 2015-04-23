package com.longnightking.togodutch_android.statistic;

/**
 * Created by longNightKing on 4/22/15.
 */
public class Purchase {

    private String mTitle;

    private double mConsumption;

    public Purchase(String title, double consumption){
        this.mTitle = title;
        this.mConsumption = consumption;
    }

    public String getTitle(){
        return mTitle;
    }

    public double getConsumption(){
        return mConsumption;
    }

    public void setTitle(String title){
        this.mTitle = title;
    }

    public void setConsumption(double consumption){
        this.mConsumption = consumption;
    }
}
