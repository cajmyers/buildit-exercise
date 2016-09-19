package com.example.charliemyers.myweather;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by charlie.myers on 18/09/2016.
 *
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class DayPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private double mLongitude;
    private double mLatitude;

    public DayPagerAdapter(FragmentManager fm, Context context, double longitude, double latitude) {
        super(fm);
        mContext = context;
        mLongitude = longitude;
        mLatitude = latitude;
    }

    @Override
    public Fragment getItem(int position) {
        return DayFragment.newInstance(position, mLongitude, mLatitude);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;

        if (position == 0) {
            title = mContext.getString(R.string.today);
        } else if (position < getCount()) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf1 = new SimpleDateFormat("E");
            cal.add(Calendar.DATE, position);
            title = sdf1.format(cal.getTime());
        }

        return title;
    }
}

