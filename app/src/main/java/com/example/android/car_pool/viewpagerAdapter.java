package com.example.android.car_pool;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by pussyhunter on 13/04/2017.
 */

public class viewpagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments=new ArrayList<>();
    ArrayList<String> String_list=new ArrayList<>();

    public void addFragment(Fragment fragment,String title){
        this.fragments.add(fragment);
        this.String_list.add(title);
    }

    viewpagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String_list.get(position);
    }
}
