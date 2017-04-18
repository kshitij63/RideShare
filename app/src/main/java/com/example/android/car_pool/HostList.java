package com.example.android.car_pool;

/**
 * Created by pussyhunter on 12/03/2017.
 */

public class HostList {
    private String name;
    private String mobile;

    HostList(String name,String mobile){
        this.name=name;
        this.mobile=mobile;
    }
    HostList(){}

    public String getMobile() {
        return mobile;
    }

    public String getName() {
        return name;
    }
}
