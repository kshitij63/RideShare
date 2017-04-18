package com.example.android.car_pool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PrivateKey;

public class RideTypeActivity extends AppCompatActivity {
    String User_name;
    SharedPreferences sp;
    String name;
    Button but;
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    viewpagerAdapter viewpagerAdapter;
    CreateFragment frag1;
    SearchFragment frag2;
    //String User_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_type);
        Intent intent=new Intent(RideTypeActivity. this,NotificationService.class);
        Intent intent1=new Intent(this,resultService.class);

        Log.e("service","started");

        sp= getSharedPreferences("mobile", MODE_PRIVATE);
        User_name=sp.getString("key1",null);
        intent.putExtra("user_key",User_name);
        startService(intent);
        startService(intent1);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        viewPager=(ViewPager) findViewById(R.id.viewpager);
        tabLayout=(TabLayout) findViewById(R.id.Tablayout);
        viewpagerAdapter=new viewpagerAdapter(getSupportFragmentManager());
        frag1=new CreateFragment();
        frag2=new SearchFragment();
        Bundle bun=new Bundle();
        bun.putString("user_key",User_name);
        frag1.setArguments(bun);
        frag2.setArguments(bun);
        viewpagerAdapter.addFragment(frag1,"CREATE");
        viewpagerAdapter.addFragment(frag2,"SEARCH");
        viewPager.setAdapter(viewpagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        name=sp.getString("mobile",null);
        Toast.makeText(this,name +"," +User_name,Toast.LENGTH_SHORT).show();
        if(name==null || User_name==null){
            Intent i=new Intent(RideTypeActivity.this,LoginActivity.class);
            startActivity(i);
            finish();

        }


        /*start=(FloatingActionButton) findViewById(R.id.start_but);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit=sp.edit();

                edit.clear();
                edit.apply();
                Intent i= new Intent(RideTypeActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
start.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});*/
    }
}
