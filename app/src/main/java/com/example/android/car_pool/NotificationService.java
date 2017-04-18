package com.example.android.car_pool;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by pussyhunter on 15/04/2017.
 */

public class NotificationService extends Service {
    String user_key;
    String client;
    FirebaseDatabase dat;
    String name;
    SharedPreferences sp;
    String main_key;
    SharedPreferences.Editor edit;
DatabaseReference ref,ref2;


    final class myThread implements Runnable{
        int start_id;

       // DatabaseReference ref;

        myThread(int start_id) {
            this.start_id=start_id;

        }

        @Override
        public void run() {
            dat=FirebaseDatabase.getInstance();
            ref=dat.getReference().child("requested_table");
            ref.orderByChild("requested_to").equalTo(user_key).limitToLast(1).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Toast.makeText(NotificationService.this,"child added",Toast.LENGTH_SHORT).show();

                    if(dataSnapshot.child("status").getValue(Integer.class)==0) {
                    String x=dataSnapshot.child("requested_to").getValue().toString();
                    Toast.makeText(NotificationService.this,x +"," +user_key,Toast.LENGTH_SHORT).show();

                    if(x.equals(user_key)) {
                        Toast.makeText(NotificationService.this, "yeaah", Toast.LENGTH_SHORT).show();
                        main_key = dataSnapshot.getKey();
                        client = dataSnapshot.child("requested_by").getValue().toString();
                        Toast.makeText(NotificationService.this, client, Toast.LENGTH_SHORT).show();
                        mNotify();
                    }
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags,startId);
        //user_key=(String) intent.getExtras().get("user_key");
        Toast.makeText(this,user_key +" soti started",Toast.LENGTH_SHORT).show();
        Thread thread=new Thread(new myThread(startId));
        thread.start();
        return START_STICKY;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        sp=this.getSharedPreferences("mobile",MODE_PRIVATE);
        user_key=sp.getString("key1",null);
        Toast.makeText(this,"created,"+user_key,Toast.LENGTH_SHORT).show();
    }

    public void mNotify(){
        ref=dat.getReference().child("users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snap:dataSnapshot.getChildren()){
                    if(client.equals(snap.getKey())){
                        Toast.makeText(NotificationService.this,"name",Toast.LENGTH_SHORT).show();
//                        Toast.

                name=snap.child("first_name").getValue().toString() +snap.child("last_name").getValue().toString();
                        Toast.makeText(NotificationService.this,name,Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(name!=null) {

            Intent Acceptintent=new Intent(NotificationService.this,AcceptReciever.class);
            Intent Declineintent=new Intent(NotificationService.this,DeclineReceiver.class);
            Declineintent.putExtra("client",client);
            Declineintent.putExtra("main_key",main_key);
            Acceptintent.putExtra("main_key",main_key);
            Acceptintent.putExtra("client",client);
            PendingIntent PDeclineintent=PendingIntent.getBroadcast(getApplicationContext(),(int)(System.currentTimeMillis()),Declineintent,PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent PAcceptintent=PendingIntent.getBroadcast(getApplicationContext(),(int)(System.currentTimeMillis()),Acceptintent,PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setContentTitle("Car Pool")
                    .setContentText(name + " has requested to ride with you!!!")
                    .setSmallIcon(R.drawable.car).addAction(R.drawable.common_google_signin_btn_icon_dark,"ACCEPT",PAcceptintent)
                    .addAction(R.drawable.common_google_signin_btn_icon_light,"DECLINE",PDeclineintent);

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            int tag = (int) System.currentTimeMillis();
            manager.notify(tag, builder.build());
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"destroyed",Toast.LENGTH_SHORT).show();
    }
}
