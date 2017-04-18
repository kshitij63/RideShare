package com.example.android.car_pool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    EditText first_name_edit;
    EditText last_name_edit;
    EditText email;
    EditText mobile;
    EditText password;
    EditText confirm;
    Button ok;
    String l;
    String User_key;
    FirebaseDatabase mdatabase;
    SharedPreferences sp;
    DatabaseReference mref;
    SharedPreferences.Editor edit;
Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp=getSharedPreferences("mobile",MODE_PRIVATE);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edit=sp.edit();
        setContentView(R.layout.activity_sign_up);

        first_name_edit = (EditText) findViewById(R.id.first_id);
        last_name_edit = (EditText) findViewById(R.id.last_id);
        email = (EditText) findViewById(R.id.email_id);
        mobile = (EditText) findViewById(R.id.mobile_id);
        password = (EditText) findViewById(R.id.password_id);
        confirm = (EditText) findViewById(R.id.confirm_id);
        ok = (Button) findViewById(R.id.ok);
        mdatabase = FirebaseDatabase.getInstance();
        mref = mdatabase.getReference().child("users");


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a = first_name_edit.getText().toString();
                String b = last_name_edit.getText().toString();
                String c = email.getText().toString();
                 l = (mobile.getText().toString());
                String p = password.getText().toString();
                String h = confirm.getText().toString();

                if(a==null || b==null || c==null || l==null || p==null || h==null){
                    Toast.makeText(SignUpActivity.this,"Invalid Entry",Toast.LENGTH_LONG).show();
                }
                else {
                    if (p.equals(h)) {

                        UserDetail user = new UserDetail(a, b, c, l, p, 1, 1);
                        User_key = mref.push().getKey();
                        mref.child(User_key).setValue(user);
                        last_name_edit.setText("");
                        mobile.setText("");
                        email.setText("");
                        //SharedPreferences.Editor edit=sp.edit();

                        edit.putString("mobile", l);
                        edit.commit();
                        Toast.makeText(SignUpActivity.this, "Sign up Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, RideTypeActivity.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        //  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        // intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.putExtra("key", User_key);
                        edit.putString("key1", User_key);
                        edit.commit();
                        startActivity(intent);
                        first_name_edit.setText("");
                        finish();


                    } else {
                        Toast.makeText(SignUpActivity.this, "not match", Toast.LENGTH_SHORT).show();
                        first_name_edit.setText("");
                        last_name_edit.setText("");
                        mobile.setText("");
                        email.setText("");
                    }
                }

            }
        });


    }



}


