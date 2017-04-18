package com.example.android.car_pool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by pussyhunter on 16/04/2017.
 */

public class AcceptReciever extends BroadcastReceiver {
    FirebaseDatabase data;
    DatabaseReference ref,ref2,ref3;
    String client_id;
    String user_id;
    String main_key;
    SharedPreferences sp;
    String vehicle_key;
    SharedPreferences.Editor edit;
    @Override
    public void onReceive(Context context, Intent intent) {
        sp=context.getSharedPreferences("mobile",Context.MODE_PRIVATE);
        edit=sp.edit();
        data=FirebaseDatabase.getInstance();
        client_id=intent.getStringExtra("client");
        main_key=intent.getStringExtra("main_key");
        user_id=sp.getString("key1",null);
        Toast.makeText(context,user_id,Toast.LENGTH_SHORT).show();
vehicle_key=sp.getString("mera_key",null);
        ref=data.getReference().child("requested_table").child(main_key).child("status");
        ref.setValue(1);



        Toast.makeText(context,"accepted",Toast.LENGTH_SHORT).show();


    }
}
