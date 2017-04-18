package com.example.android.car_pool;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by pussyhunter on 15/04/2017.
 */

public class DetailsFragment extends Fragment {
    String clicked_user_key,your_key;
    FirebaseDatabase data;
    DatabaseReference ref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.detailfragment,container,false);
        data=FirebaseDatabase.getInstance();
        ref= data.getReference().child("requested_table");
        //Bundle bun=new Bundle();
        int s=getArguments().getInt("mseat");
        String t=getArguments().getString("detail");
        clicked_user_key=getArguments().getString("clicked_user_key");
        your_key=getArguments().getString("your_key");
        Log.e("final",s+","+t);
        TextView v1=(TextView) v.findViewById(R.id.text_dearils);
        TextView v2=(TextView) v.findViewById(R.id.text_dea);
        v1.setText(t);
        v2.setText(Integer.toString(s));
        final Button but=(Button) v.findViewById(R.id.but);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                but.setText("requested");
                but.setEnabled(false);
                mRequest r=new mRequest(clicked_user_key,your_key,0);
                String request_key=ref.push().getKey();
                ref.child(request_key).setValue(r);
            }
        });
        return v;
    }
}
