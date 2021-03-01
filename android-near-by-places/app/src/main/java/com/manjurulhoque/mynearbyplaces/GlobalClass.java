package com.manjurulhoque.mynearbyplaces;

import android.app.Application;

public class GlobalClass extends Application {
    private String Name;
    private String Issue;
    private String Phone_number;
    private String Address;

    private int Identity;

    private Double dlatitude;
    private Double dlongitude;

    public int getIdentity() {
        return Identity;
    }
    public void setIdentity(int identity) {
        Identity = identity;
    }

    public String getAddress() {
        return Address;
    }
    public void setAddress(String address) {
        Address = address;
    }

    public Double getDlatitude() {
        return dlatitude;
    }

    public void setDlatitude(Double dlatitude) {
        this.dlatitude = dlatitude;
    }

    public Double getDlongitude() {
        return dlongitude;
    }

    public void setDlongitude(Double dlongitude) {
        this.dlongitude = dlongitude;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void set_phone_number(String phone_number) { Phone_number = phone_number; }

    public String getIssue() {
        return Issue;
    }

    public void setIssue(String issue) {
        Issue = issue;
    }

    public String getPhone_number() {
        return Phone_number;
    }
}
