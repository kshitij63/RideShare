package com.example.android.car_pool;

import android.animation.ObjectAnimator;
import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AvailabilityActivity extends AppCompatActivity {
    String key;
    String key3;
    FirebaseDatabase mdatabase;
    DatabaseReference mref, mref1, mref2,reference;
    //String host_dest_id;
    //String host_src_id;
    ArrayList<HostList> host_list;
    String Client_src_id;
    String client_dest_id;
    ArrayList<String> Host_key;
    ArrayList<String> c;
    ArrayList<HostList> j;
    ListView mlistview;
    String mobile;
    String name;
       String car_info;
    int seats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);
        Bundle bundle = getIntent().getExtras();
        key = bundle.getString("key");
        key3=bundle.getString("key3");
        mdatabase=FirebaseDatabase.getInstance();
        mlistview = (ListView) findViewById(R.id.list);
       // ShowDataAsyncTask task = new ShowDataAsyncTask(key, mlistview, this);
       // task.execute();
        Host_key=new ArrayList<>();
        host_list=new ArrayList<>();

        mref=mdatabase.getReference().child("users");
        mref1=mdatabase.getReference().child("Create Trip");
        mref2=mdatabase.getReference().child("Start_Trip");
        reference=mdatabase.getReference().child("vehicle_details");

        mref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(AvailabilityActivity.this,"inside mref2 data",Toast.LENGTH_SHORT).show();
                int count=0;
                for(DataSnapshot snap:dataSnapshot.getChildren()){
                  //  Toast.makeText(AvailabilityActivity.this,"inside mref2 loop",Toast.LENGTH_SHORT).show();

                    //Toast.makeText(AvailabilityActivity.this,snap.getValue(StartTrip.class).getDestinatin_id(),Toast.LENGTH_SHORT).show();
                    String h=snap.getKey();
                    //Toast.makeText(AvailabilityActivity.this, h +" " +key, Toast.LENGTH_SHORT).show();
                    //StartTrip t=snap.getValue(StartTrip.class);
                    if(h.equals(key)){
                    //    Toast.makeText(AvailabilityActivity.this,"inside mref2 if statmenet",Toast.LENGTH_SHORT).show();

                        // StartTrip trip=snap.getValue(StartTrip.class);
                        client_dest_id=snap.child("destinatin_id").getValue().toString();
                        Client_src_id=snap.child("source_id").getValue().toString();

                        //Toast.makeText(AvailabilityActivity.this,client_dest_id +" " +Client_src_id,Toast.LENGTH_SHORT).show();
                        //getClose();
                        mref1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //Toast.makeText(AvailabilityActivity.this,"inside mref1 data",Toast.LENGTH_SHORT).show();
                                for(DataSnapshot snap:dataSnapshot.getChildren()){
                                    //CreateTrip trip=snap.getValue(CreateTrip.class);
                                  //  Toast.makeText(AvailabilityActivity.this,"inside mref1 data loop",Toast.LENGTH_SHORT).show();

                                    String host_dest_id=snap.child("destinatin_id").getValue().toString();
                                    String host_src_id=snap.child("source_id").getValue().toString();
                                    //Toast.makeText(AvailabilityActivity.this,host_dest_id + host_src_id,Toast.LENGTH_SHORT).show();

                                    if (Client_src_id.equals(host_src_id) && client_dest_id.equals(host_dest_id)) {
                                    //    Toast.makeText(AvailabilityActivity.this,"inside mref1 if",Toast.LENGTH_SHORT).show();

                                        final String p=snap.child("user_key").getValue().toString();
                                        mref.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                      //          Toast.makeText(AvailabilityActivity.this,"inside mref data",Toast.LENGTH_SHORT).show();

                                                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                        //            Toast.makeText(AvailabilityActivity.this,"inside mref loop data",Toast.LENGTH_SHORT).show();

                                                    String x = snap.getKey();
                                                    //  Toast.makeText(AvailabilityActivity.this,x,Toast.LENGTH_SHORT).show();
                                                    if (x.equals(p)) {
                                          //              Toast.makeText(AvailabilityActivity.this,"inside mref data if",Toast.LENGTH_SHORT).show();

                                                        // UserDetail det = snap.getValue(UserDetail.class);
                                                        HostList host = new HostList(snap.child("first_name").getValue().toString() + " " + snap.child("last_name").getValue().toString(), snap.child("mobile").getValue().toString());
                                                        Toast.makeText(AvailabilityActivity.this,snap.child("first_name").getValue().toString(),Toast.LENGTH_SHORT).show();
                                                        host_list.add(host);
                                            //            Toast.makeText(AvailabilityActivity.this,"added",Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                                if(host_list.size()==0){
                                                    Toast.makeText(AvailabilityActivity.this,"Nothing to show",Toast.LENGTH_LONG).show();
                                                }
                                                else {
                                                    //  Toast.makeText(AvailabilityActivity.this,"" +host_list.size(),Toast.LENGTH_SHORT).show();
                                                    mlistview.setAdapter(new CustomHostAdapter(AvailabilityActivity.this, host_list));
                                                }


                                            }
                                                            //Toast.makeText(AvailabilityActivity.this,"inside mref2 data",Toast.LENGTH_SHORT).show();


                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                    // Toast.makeText(AvailabilityActivity.this,"found equal",Toast.LENGTH_SHORT).show();
                                }
                               // Toast.makeText(AvailabilityActivity.this,"loop end mref1",Toast.LENGTH_SHORT).show();


                            }
                            //Host_key.addAll(c);
                            //Toast.makeText(AvailabilityActivity.this, "in getclose" +" " +Host_key.size(), Toast.LENGTH_SHORT).show();
                            //closer(Host_key);
                            //Toast.makeText(AvailabilityActivity.this," " +host_list.size(),Toast.LENGTH_SHORT).show();

                            // mlistview.setAdapter(new CustomHostAdapter(AvailabilityActivity.this,host_list));
                            //Toast.makeText(AvailabilityActivity.this,"adapter",Toast.LENGTH_SHORT).show();
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        //Toast.makeText(AvailabilityActivity.this,"end" +host_list.size(),Toast.LENGTH_SHORT).show();

                    }
                    else{
                       // count++;
                        //Toast.makeText(AvailabilityActivity.this," " +count,Toast.LENGTH_SHORT).show();

                    }
                }
                //Toast.makeText(AvailabilityActivity.this,"loop end mref2",Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Toast.makeText(AvailabilityActivity.this," " +host_list.size(),Toast.LENGTH_SHORT).show();
mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HostList list=host_list.get(position);
        final String mob=list.getMobile();


        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snap:dataSnapshot.getChildren()){
                    //String key=snap.getKey();
                    HostList per=snap.getValue(HostList.class);
                    Toast.makeText(AvailabilityActivity.this,per.getMobile(),Toast.LENGTH_SHORT).show();
                   if(mob.equals(per.getMobile()))
                   {
                       Log.e("mobile",per.getMobile());
               name=snap.getKey();
                       Log.e("name",name);
                   }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snap:dataSnapshot.getChildren()){
                    //String key=snap.getKey();
                    Car_details det=snap.getValue(Car_details.class);
                    String mkey=det.getKey();
                    Log.e("mkey",mkey);
                    if(name.equals(mkey)){
                    car_info=det.getDetails();
                        Log.e("det",car_info);
                        seats=det.getNumber_of_seats();
                        if(seats!=0) {

                            RelativeLayout lay = (RelativeLayout) findViewById(R.id.top_dimmer);
                            //ObjectAnimator animator=ObjectAnimator.ofFloat(lay,View.ALPHA,0.5f);
                            // animator.start();
                            DetailsFragment fragment = new DetailsFragment();
                            Bundle bun = new Bundle();
                            bun.putString("detail", car_info);
                            bun.putInt("mseat", seats);
                            bun.putString("clicked_user_key", name);
                            bun.putString("your_key", key3);
                            fragment.setArguments(bun);

                            FragmentManager manager = getFragmentManager();

                            android.app.FragmentTransaction transaction = manager.beginTransaction();
                            transaction.addToBackStack(null);
                            transaction.setCustomAnimations(R.animator.slide_from_right, R.animator.slide_to_left, R.animator.slide_to_left, R.animator.slide_to_left);
                            transaction.add(R.id.container1, fragment);
                            transaction.commit();

                        }
                        else {Toast.makeText(AvailabilityActivity.this,"Driver Not Avialable",Toast.LENGTH_SHORT).show();}
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }
});
    }



    public void getClose(){
       //c=new ArrayList<>();
        mref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snap:dataSnapshot.getChildren()){
                    //CreateTrip trip=snap.getValue(CreateTrip.class);
                    String host_dest_id=snap.child("destinatin_id").getValue().toString();
                    String host_src_id=snap.child("source_id").getValue().toString();
                    //Toast.makeText(AvailabilityActivity.this,host_dest_id + host_src_id,Toast.LENGTH_SHORT).show();

                    if (Client_src_id.equals(host_src_id) && client_dest_id.equals(host_dest_id)) {
                        final String p=snap.child("user_key").getValue().toString();
                        mref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                    String x = snap.getKey();
                                    //  Toast.makeText(AvailabilityActivity.this,x,Toast.LENGTH_SHORT).show();
                                    if (x.equals(p)) {
                                       // UserDetail det = snap.getValue(UserDetail.class);
                                        HostList host = new HostList(snap.child("first_name").getValue().toString() + " " + snap.child("last_name").getValue().toString(), snap.child("mobile").getValue().toString());
                                        Toast.makeText(AvailabilityActivity.this,snap.child("first_name").getValue().toString(),Toast.LENGTH_SHORT).show();
                                        host_list.add(host);
                                        Toast.makeText(AvailabilityActivity.this,"added",Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                       // Toast.makeText(AvailabilityActivity.this,"found equal",Toast.LENGTH_SHORT).show();
                    }
                }
                //Host_key.addAll(c);
                //Toast.makeText(AvailabilityActivity.this, "in getclose" +" " +Host_key.size(), Toast.LENGTH_SHORT).show();
                //closer(Host_key);
                //Toast.makeText(AvailabilityActivity.this," " +host_list.size(),Toast.LENGTH_SHORT).show();

               // mlistview.setAdapter(new CustomHostAdapter(AvailabilityActivity.this,host_list));
                //Toast.makeText(AvailabilityActivity.this,"adapter",Toast.LENGTH_SHORT).show();
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Toast.makeText(AvailabilityActivity.this,"end" +host_list.size(),Toast.LENGTH_SHORT).show();
    }



    public void closer(ArrayList<String> m) {
        j=new ArrayList<>();
        for (int i = 0; i < m.size(); i++) {

            final  String v = m.get(i);
            mref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        String x = snap.getKey();
                        //  Toast.makeText(AvailabilityActivity.this,x,Toast.LENGTH_SHORT).show();
                        if (x.equals(v)) {
                            UserDetail det = snap.getValue(UserDetail.class);
                            HostList host = new HostList(det.getFirst_name() + " " + det.getLast_name(), det.getMobile());
                            Toast.makeText(AvailabilityActivity.this,det.getFirst_name(),Toast.LENGTH_SHORT).show();
                            host_list.add(host);
                            Toast.makeText(AvailabilityActivity.this,"added",Toast.LENGTH_SHORT).show();

                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        j.addAll(host_list);
        Toast.makeText(AvailabilityActivity.this,"size of j" +" " +j.size(),Toast.LENGTH_SHORT).show();

    }


}

