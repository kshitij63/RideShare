package com.example.android.car_pool;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pussyhunter on 24/03/2017.
 */

public class JSONparser {
    List<List<HashMap<String, String>>> route;
    GoogleMap map;
    //List<List<LatLng>> temp_list;
    Context context;
    JSONparser(Context context,GoogleMap map){
        this.context=context;
        this.map=map;
    }

    public List<List<HashMap<String, String>>> getList(JSONObject obj) {
        route = new ArrayList<>();
        JSONArray legs;
        JSONArray steps;
        JSONArray routes;
        try {
            routes = obj.getJSONArray("routes");
            Toast.makeText(context,"" +routes.length(),Toast.LENGTH_SHORT).show();

            for (int i = 0; i < routes.length(); i++) {
                legs = routes.getJSONObject(i).getJSONArray("legs");
                Toast.makeText(context,"" +legs.length(),Toast.LENGTH_SHORT).show();


                for (int j = 0; j < legs.length(); j++) {
                    List list_map = new ArrayList();

                    steps = legs.getJSONObject(i).getJSONArray("steps");
                    Toast.makeText(context,"" +steps.length(),Toast.LENGTH_SHORT).show();
                    for (int k = 0; k < steps.length(); k++) {
                        List<LatLng> m = new ArrayList<>();
                        m = decodePoly(steps.getJSONObject(k).getJSONObject("polyline").getString("points"));
                        PolylineOptions poly=new PolylineOptions();
                        poly.color(Color.BLUE).width(5).addAll(m);
                        map.addPolyline(poly);

                        for (int l = 0; l < m.size(); l++) {
                            HashMap<String, String> map = new HashMap<>();
                            Log.v("Json",Double.toString(m.get(i).latitude) +"," +Double.toString(m.get(i).longitude) );
                            //temp_list.add (m.get(i));
                            map.put("lat", Double.toString(m.get(i).latitude));
                            map.put("long", Double.toString(m.get(i).longitude));
                            list_map.add(map);

                        }
                        //Toast.makeText(context,"" +list_map.size(), Toast.LENGTH_SHORT).show();
                    }
                    route.add(list_map);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        return route;

    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;

    }
}