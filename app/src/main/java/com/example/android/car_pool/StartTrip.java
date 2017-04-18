package com.example.android.car_pool;

/**
 * Created by pussyhunter on 12/03/2017.
 */

public class StartTrip {
   private  String user_key;
    private String Destinatin_id;
    private String Source_id;

    StartTrip(String user_key,String Destination_id,String Source_id){
        Destinatin_id=Destination_id;
        this.Source_id=Source_id;
        this.user_key=user_key;
    }

    StartTrip(){}

    public String getDestinatin_id() {
        return Destinatin_id;
    }

    public String getSource_id() {
        return Source_id;
    }

    public String getUser_key() {
        return user_key;
    }
}
