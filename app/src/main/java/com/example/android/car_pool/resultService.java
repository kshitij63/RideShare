package com.example.android.car_pool;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by pussyhunter on 16/04/2017.
 */

public class resultService extends Service {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FirebaseDatabase database;
    DatabaseReference databaseReference,ref2;
    String user_key;
    String name;
    String samne_wala;
    final class myThread implements Runnable{

        int start_id;
        myThread(int start_id){
            this.start_id=start_id;
        }

        @Override
        public void run() {
            database=FirebaseDatabase.getInstance();
databaseReference=database.getReference().child("requested_table");

            databaseReference.orderByChild("requested_by").equalTo(user_key).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    //Toast.makeText(resultService.this,       dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
int i=dataSnapshot.child("status").getValue(Integer.class);
                    samne_wala=dataSnapshot.child("requested_to").getValue().toString();
//Integer i= dataSnapshot.child("status").getValue(Integer.class);
  //                  samne_wala=dataSnapshot.child("requested_to").toString();
                    //Toast.makeText(resultService.this,samne_wala,Toast.LENGTH_SHORT).show();
                    if(i==1){
                        mNotify("Accepted");
                    }
                    else if(i==-1){
                        mNotify("declined");
                    }
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

    private void mNotify(final String accepted) {
        database.getReference().child("users").child(samne_wala).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                name=dataSnapshot.child("first_name").getValue().toString()+dataSnapshot.child("last_name").getValue().toString();
                //Toast.makeText(resultService.this, name, Toast.LENGTH_SHORT).show();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(resultService.this);
                builder.setContentTitle("Car Pool")
                        .setContentText( name +" has " +accepted +" your request")
                        .setSmallIcon(R.drawable.car);

                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                int tag = (int) System.currentTimeMillis();
                manager.notify(tag, builder.build());
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
Toast.makeText(getApplicationContext(),user_key +"re started",Toast.LENGTH_SHORT).show();
        Thread thread=new Thread(new myThread(startId));
        thread.start();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        sharedPreferences=this.getSharedPreferences("mobile",MODE_PRIVATE);
        user_key=sharedPreferences.getString("key1",null);
        Toast.makeText(resultService.this,"result," +user_key,Toast.LENGTH_SHORT).show();
        super.onCreate();
    }


}
