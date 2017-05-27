package com.example.duy.permission_loadimage;

/**
 * Created by duy on 25/05/2017.
 */

public class Data {
    private String Name,Number;

    public Data(String name, String number) {
        Name = name;
        Number = number;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }
}
