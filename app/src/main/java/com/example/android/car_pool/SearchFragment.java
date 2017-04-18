package com.example.android.car_pool;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by pussyhunter on 13/04/2017.
 */

public class SearchFragment extends android.support.v4.app.Fragment {
    FloatingActionButton create,Create1,create2;
    ObjectAnimator animator1,animator2,animator3,animator4,animator5,animator6,animator7;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.searchfragment,container,false);
        create=(FloatingActionButton) v.findViewById(R.id.start_trip);
        create2=(FloatingActionButton) v.findViewById(R.id.start_trip2);
        Create1=(FloatingActionButton) v.findViewById(R.id.start_trip3);
        //animator1=ObjectAnimator.ofFloat(create,View.TRANSLATION_Y,-200f);
        //animator2=ObjectAnimator.ofFloat(create2,View.TRANSLATION_Y,-120f);
        //animator3=ObjectAnimator.ofFloat(create,View.SCALE_Y,0.6f);
        //animator4=ObjectAnimator.ofFloat(create,View.SCALE_X,0.6f);
        //animator5=ObjectAnimator.ofFloat(create2,View.SCALE_Y,0.8f);
        //animator6=ObjectAnimator.ofFloat(create2,View.SCALE_X,0.8f);
        //animator7=ObjectAnimator.ofFloat(Create1,View.ROTATION,360);

        //final AnimatorSet set=new AnimatorSet();
        //set.play(animator7).with(animator1).with(animator2).with(animator3).with(animator4).with(animator5).with(animator6);

        //animator3=new ObjectAnimator();


       final String key=getArguments().getString("user_key");

        Create1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          //      set.start();
                Intent intent=new Intent(getActivity(),MainActivity.class);
                intent.putExtra("key",key);

                intent.putExtra("type",200);
                startActivity(intent);
            }
        });
        return v;
    }

}
