package com.example.android.car_pool;

/**
 * Created by pussyhunter on 15/04/2017.
 */

public class Car_details {

    int number_of_seats;
    String details;
    String key;

    Car_details(int number_of_seats,String details,String key){
        this.number_of_seats=number_of_seats;
        this.details=details;
        this.key=key;
    }
    Car_details(){}

    public int getNumber_of_seats() {
        return number_of_seats;
    }

    public String getDetails() {
        return details;
    }

    public String getKey() {
        return key;
    }

}
