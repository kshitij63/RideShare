package com.example.android.car_pool;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.LocalServerSocket;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    PlaceAutocompleteFragment autocompleteFragment_src, autocompleteFragment_dest;
    FirebaseDatabase mdata;
    //RequestQueue queue;
    List<List<HashMap<String,String>>> path;
    String mera_key;
    Button but2;
    DatabaseReference mref, mref1, mref2,ref;
    String User_name;
    String create_key;
    //Button but;
    JsonObjectRequest request;
    String src_id;
    String url;
    GoogleApiClient googleApiClient;
    String dest_id;
    LatLng src_latLng;
    LatLng dest_Latlng;
    String car_deatils;
    int seats;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;

    List<List<HashMap<String,String>>> data_path;
    int type;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=this.getSharedPreferences("mobile",MODE_PRIVATE);
        edit=sharedPreferences.edit();
        //googleApiClient= new GoogleApiClient.Builder(this).
          //      addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LOCATION_SERVICE);
//        final String temp="https://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination=Montreal&key=AIzaSyCHmKT1Oe-2OGybj5pifV1z56xJrZ_NWtc";
        //url="https://maps.googleapis.com/maps/api/directions/json?origin=Chicago,IL&destination=Los+Angeles,CA&waypoints=Joplin,MO|Oklahoma+City,OK&key=YOUR_API_KEY";
        but2 = (Button) findViewById(R.id.but2);
        Bundle bundle = getIntent().getExtras();
        User_name = bundle.getString("key");
        type = bundle.getInt("type");
        if(type!=100){
            but2.setText("SEARCH");
        }
        seats=bundle.getInt("seats");
        car_deatils=bundle.getString("car_details");

        //but = (Button) findViewById(R.id.but1);
        mdata = FirebaseDatabase.getInstance();
        mref = mdata.getReference().child("users");
        ref=mdata.getReference().child("vehicle_details");
        mref1 = mdata.getReference().child("Create Trip");
        mref2 = mdata.getReference().child("Start_Trip");
        mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        autocompleteFragment_src = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_source);

        autocompleteFragment_src.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(final Place place) {
                src_id = place.getId().toString();
src_latLng=place.getLatLng();
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                       // CameraPosition cameraPosition=new CameraPosition(Map).builder();
                        MarkerOptions op=new MarkerOptions().position(src_latLng).title(place.getName().toString());
                        googleMap.addMarker(op);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(src_latLng));
                    }
                });
                Toast.makeText(MainActivity.this, place.getName().toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(Status status) {
                Toast.makeText(MainActivity.this, status.toString(), Toast.LENGTH_LONG).show();

            }
        });

        autocompleteFragment_dest = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_destination);
        autocompleteFragment_dest.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(final Place place) {

                dest_id = place.getId().toString();
                dest_Latlng=place.getLatLng();
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(final GoogleMap googleMap) {
                        googleMap.addMarker(new MarkerOptions().position(dest_Latlng).title(place.getName().toString()));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(dest_Latlng));
                      //  googleMap.addPolyline(new PolylineOptions().width(5).color(Color.BLUE).add(dest_Latlng,src_latLng));
                        //googleMap.addPolyline(new PolylineOptions().add(src_latLng,dest_Latlng).width(3).color(R.color.brand));
                       url="https://maps.googleapis.com/maps/api/directions/json?" +"origin=place_id:" +src_id +"&destination=place_id:" +dest_id +"&key=AIzaSyCHmKT1Oe-2OGybj5pifV1z56xJrZ_NWtc";


                        final RequestQueue queue=Volley.newRequestQueue(MainActivity.this);
                         request=new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                      //  JSONparser parser=new JSONparser(MainActivity.this);
                                        JSONparser parser=new JSONparser(MainActivity.this,googleMap);                                        //path= parser.getList(response);
                                        path=parser.getList(response);

                                        Toast.makeText(MainActivity.this,"on create" +path.size(),Toast.LENGTH_SHORT).show();
                                        //DrawRoute drawRoute=new DrawRoute(MainActivity.this,googleMap);
                                    //     PolylineOptions options=draw(path,googleMap);
                                      //  googleMap.addPolyline(options);
                     //                   googleMap.addPolyline(poly);
                                        //ArrayList<LatLng> coor_list;
                                        //PolylineOptions polylineOptions=null;

                                        //for(int i=0;i<path.size();i++){
                                          //  coor_list=new ArrayList<>();
                                            //polylineOptions=new PolylineOptions();

                                            //List<HashMap<String,String>> list_maps=path.get(i);
                                            //for(int k=0;k<list_maps.size();k++){
                                              //  HashMap<String,String> step_lat_long=list_maps.get(k);
                                                //Double latitude=Double.parseDouble(step_lat_long.get("lat"));
                                                //Double longi=Double.parseDouble(step_lat_long.get("long"));
                                                //Log.e("ltlng",latitude+" "+longi);
                                                //LatLng latLng=new LatLng(latitude,longi);
                                                //coor_list.add(latLng);
                                            //}
                                            //polylineOptions.addAll(coor_list).
                                               //width(3)
                                            //.color(Color.BLUE);
                                            //googleMap.addPolyline(polylineOptions);

                                        //}
                                        queue.stop();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                                        queue.stop();
                                    }
                                });
                        queue.add(request);
                    }
                });
                Toast.makeText(MainActivity.this, place.getName().toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(Status status) {
                Toast.makeText(MainActivity.this, status.toString(), Toast.LENGTH_LONG).show();
            }
        });



        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 100) {
                    CreateTrip create = new CreateTrip(User_name, dest_id, src_id);
                    create_key = mref1.push().getKey();
                    mref1.child(create_key).setValue(create);
                    Toast.makeText(MainActivity.this, "Response submitted successfully", Toast.LENGTH_SHORT).show();
                    but2.setEnabled(false);
                    mera_key=ref.push().getKey();
                    edit.putString("mera_key",mera_key);
                    edit.commit();
                    ref.child(mera_key).setValue(new Car_details(seats,car_deatils,User_name));

                    Log.e("service","started1");

                } else if (type == 200) {
                    StartTrip start = new StartTrip(User_name, dest_id, src_id);
                    create_key = mref2.push().getKey();
                    mref2.child(create_key).setValue(start);
                    Intent intent = new Intent(MainActivity.this, AvailabilityActivity.class);
                    intent.putExtra("key", create_key);
                    intent.putExtra("key3",User_name);
                    startActivity(intent);
                }
            }
        });

    }

    public void getList(JSONObject obj) {
        path = new ArrayList<>();
        JSONArray legs;
        JSONArray steps;
        JSONArray routes;
        try {
            routes = obj.getJSONArray("routes");
            Toast.makeText(this,"" +routes.length(),Toast.LENGTH_SHORT).show();

            for (int i = 0; i < routes.length(); i++) {
                legs = routes.getJSONObject(i).getJSONArray("legs");
                Toast.makeText(this,"" +legs.length(),Toast.LENGTH_SHORT).show();


                for (int j = 0; j < legs.length(); j++) {
                    List list_map = new ArrayList();
                    steps = legs.getJSONObject(i).getJSONArray("steps");
                    Toast.makeText(this,"" +steps.length(),Toast.LENGTH_SHORT).show();
                    for (int k = 0; k < steps.length(); k++) {
                        List<LatLng> m = new ArrayList<>();
                        m = decodePoly(steps.getJSONObject(k).getJSONObject("polyline").getString("points"));
                        for (int l = 0; l < m.size(); l++) {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("lat", Double.toString(m.get(i).latitude));
                            map.put("long", Double.toString(m.get(i).longitude));
                            list_map.add(map);
                        }
                    }
                    path.add(list_map);

                    Toast.makeText(MainActivity.this,"" +path.size(),Toast.LENGTH_SHORT).show();

                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }

        //return path;

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

    public PolylineOptions draw(List<List<HashMap<String,String>>> route,GoogleMap map){
        ArrayList<LatLng> coor_list;
        PolylineOptions polylineOptions=null;

        for(int i=0;i<route.size();i++){
            polylineOptions=new PolylineOptions();
             coor_list=new ArrayList<>();
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
        }



        //map.addPolyline(polylineOptions);
        return polylineOptions;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}





