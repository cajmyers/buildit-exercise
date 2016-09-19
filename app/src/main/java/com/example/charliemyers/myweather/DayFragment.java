package com.example.charliemyers.myweather;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by charlie.myers on 18/09/2016.
 */
public class DayFragment extends ListFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_LONGITUDE = "location_long";
    private static final String ARG_LATITUDE = "location_lat";
    private WeatherData mloadData;

    public DayFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DayFragment newInstance(int sectionNumber, double longitude, double latitude) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putDouble(ARG_LONGITUDE, longitude);
        args.putDouble(ARG_LATITUDE, latitude);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_day, container, false);

        Integer dayIndex = getArguments().getInt(ARG_SECTION_NUMBER);
        double longitude = getArguments().getDouble(ARG_LONGITUDE);
        double latitude = getArguments().getDouble(ARG_LATITUDE);

        DayWeatherListAdapter adapter = new DayWeatherListAdapter(getActivity());
        setListAdapter(adapter);

        mloadData = new WeatherData(adapter, dayIndex);
        load(longitude, latitude);

        return rootView;
    }

    public void load(double longitude, double latitude) {
        mloadData.setLongLat(longitude, latitude);
        mloadData.execute();
    }
}


