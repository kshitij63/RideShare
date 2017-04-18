package com.example.android.car_pool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {
    FirebaseDatabase mdatabase;
    DatabaseReference mref;
    EditText mobile;
    TextView signup_go;
    String mobile1;
    EditText password;
    Button but;
    String User_key;
    ViewPager pager;
    slider S;
    Timer timer;
    int page = 1;
    ValueEventListener mevent;
SharedPreferences sp;
    SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
sp=this.getSharedPreferences("mobile",MODE_PRIVATE);
        edit=sp.edit();
        //final SharedPreferences.Editor edit=sp.edit();

        setContentView(R.layout.activity_login);
pager=(ViewPager) findViewById(R.id.container);
        S=new slider(this);
        pager.setAdapter(S);
        //pageSwitcher(1);
//animate();

        signup_go=(TextView) findViewById(R.id.signup_go);
        mdatabase=FirebaseDatabase.getInstance();
        mobile=(EditText) findViewById(R.id.mobile);
        password=(EditText) findViewById(R.id.password);
        mref=mdatabase.getReference().child("users");
but=(Button) findViewById(R.id.login);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String x=(mobile.getText().toString());
                final String s=password.getText().toString();
                mevent=new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //Toast.makeText(LoginActivity.this,x +s,Toast.LENGTH_SHORT).show();
                        int data=0;
                        for(DataSnapshot snap:dataSnapshot.getChildren()){
                           // Toast.makeText(LoginActivity.this,"loop",Toast.LENGTH_SHORT).show();
                            UserDetail user=snap.getValue(UserDetail.class);
                            //mobile1=user.getMobile();
                            //Toast.makeText(LoginActivity.this,user.getMobile().toString() +user.getPassword(),Toast.LENGTH_SHORT).show();
                            if(user.getMobile().equals(x) && user.getPassword().equals(s)){
                                User_key=snap.getKey();
                                mobile1=x;
                                SharedPreferences.Editor edit=sp.edit();
                                edit.putString("mobile",mobile1);
                                edit.commit();

                                Toast.makeText(LoginActivity.this,User_key ,Toast.LENGTH_SHORT).show();
                                data++;
                          //      Toast.makeText(LoginActivity.this,data ,Toast.LENGTH_SHORT).show();
                                mref.child(User_key).child("login_flag").setValue(1);
                                Toast.makeText(LoginActivity.this,"welcome " +user.getFirst_name(),Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(LoginActivity.this,RideTypeActivity.class);
                                intent.putExtra("key",User_key);
                                edit.putString("key1",User_key);
                                edit.commit();
                                finish();
             //                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
               //                 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 //               intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(intent);
                            }
                        }

                            if(data==0){
                                Toast.makeText(LoginActivity.this,"login failed ",Toast.LENGTH_SHORT).show();


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(LoginActivity.this,"cancelled" +databaseError.toString(),Toast.LENGTH_SHORT).show();

                    }
                };
                mref.addListenerForSingleValueEvent(mevent);
            }
        });

        signup_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();}
        });
        }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mref.removeEventListener(mevent);
    }


}
