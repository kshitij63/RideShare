package com.example.android.car_pool;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by pussyhunter on 13/04/2017.
 */

public class CreateFragment extends android.support.v4.app.Fragment {
    FloatingActionButton create;
    String key;
    FirebaseDatabase datbase;
    DatabaseReference ref;
    EditText deatils_text,seats;
    String car_key;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.createfragment,container,false);
        create=(FloatingActionButton) v.findViewById(R.id.create_trip);
        key=getArguments().getString("user_key");
        deatils_text=(EditText) v.findViewById(R.id.edit_detail);
        seats=(EditText) v.findViewById(R.id.edit_seat);
        //datbase=FirebaseDatabase.getInstance();
        //ref=datbase.getReference().child("vehicle_details");


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deatils_text.getText().toString().equals("") ||Integer.parseInt(seats.getText().toString()) ==0){
                    Toast.makeText(getActivity(),"Invalid Entry",Toast.LENGTH_SHORT).show();
                }
                //car_key=ref.push().getKey();
                // ref.child(car_key).setValue(new Car_details(Integer.parseInt(seats.getText().toString()),deatils_text.getText().toString(),))
                else {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("key", key);
                    intent.putExtra("seats", Integer.parseInt(seats.getText().toString()));
                    intent.putExtra("car_details", deatils_text.getText().toString());
                    intent.putExtra("type", 100);
                    startActivity(intent);
                }

            }
        });
        return v;
    }
}
