package com.example.android.car_pool;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pussyhunter on 25/03/2017.
 */

public class DrawRoute {
    Context context;
    GoogleMap map;

    DrawRoute(Context context, GoogleMap map ){
        this.context=context;
        this.map=map;
    }
    public PolylineOptions draw(List<List<HashMap<String,String>>> route){
        List<LatLng> coor_list=new ArrayList<>();
        PolylineOptions polylineOptions=null;

        for(int i=0;i<route.size();i++){
            polylineOptions=new PolylineOptions();
           // coor_list=new ArrayList<>();
            List<HashMap<String,String>> list_maps=route.get(i);
            for(int k=0;k<list_maps.size();k++){
                HashMap<String,String> step_lat_long=list_maps.get(i);
                Double latitude=Double.parseDouble(step_lat_long.get("lat"));
                Double longi=Double.parseDouble(step_lat_long.get("long"));
                LatLng latLng=new LatLng(latitude,longi);
                coor_list.add(latLng);
            }
            polylineOptions.addAll(coor_list);
            polylineOptions.width(10);
            polylineOptions.color(Color.BLUE);
            map.addPolyline(polylineOptions);
        }



        //map.addPolyline(polylineOptions);
return polylineOptions;
    }


}
