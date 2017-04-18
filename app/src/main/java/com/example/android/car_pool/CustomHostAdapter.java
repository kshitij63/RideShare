package com.example.android.car_pool;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pussyhunter on 12/03/2017.
 */

public class CustomHostAdapter extends ArrayAdapter<HostList> {
    List<HostList> host_list;
    Context context;
    public CustomHostAdapter(@NonNull Context context, List<HostList> hostlist) {
        super(context, 0,hostlist);
        host_list=hostlist;
        this.context=context;
    }
    @Nullable
    @Override
    public HostList getItem(int position) {
        return host_list.get(position);
    }

    @Override
    public int getCount() {
        return host_list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v=convertView;
        if(v==null){
            v=LayoutInflater.from(context).inflate(R.layout.host,parent,false);
        }
        final HostList h=host_list.get(position);

        TextView name=(TextView) v.findViewById(R.id.name);
        name.setText(h.getName());

        TextView mobile=(TextView) v.findViewById(R.id.mobile);
        mobile.setText(h.getMobile());

        return  v;
    }
}
