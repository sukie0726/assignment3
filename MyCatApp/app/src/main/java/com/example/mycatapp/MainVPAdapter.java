package com.example.mycatapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainVPAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> list;
    public MainVPAdapter(FragmentManager fm, ArrayList<Fragment> list){
        super(fm);
        this.list = list;
    }
    @Override
    public Fragment getItem(int arg0) {
        return list.get(arg0);
    }
    @Override
    public int getCount() {
        return list.size();
    }
}
