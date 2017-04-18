package com.example.android.car_pool;

/**
 * Created by pussyhunter on 15/04/2017.
 */

public class mRequest {
    String requested_to;
    String requested_by;
    int status;

    mRequest(String requested_to,String requested_by,int status){
        this.requested_to=requested_to;
        this.requested_by=requested_by;
        this.status=status;
    }

    public int getStatus() {
        return status;
    }

    public String getRequested_by() {
        return requested_by;
    }

    public String getRequested_to() {
        return requested_to;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
