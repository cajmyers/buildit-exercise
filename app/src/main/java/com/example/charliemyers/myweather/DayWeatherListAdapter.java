package com.example.charliemyers.myweather;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by charlie.myers on 18/09/2016.
 */
public class DayWeatherListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<WeatherEntry> mEntries = new ArrayList<WeatherEntry>();

    public DayWeatherListAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mEntries.size();
    }

    @Override
    public Object getItem(int position) {
        return mEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemView;
        if (convertView == null) {
            itemView = (LinearLayout) mLayoutInflater.inflate(
                    R.layout.list_item, parent, false);

        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView timeText = (TextView)itemView.findViewById(R.id.time);
        String time = mEntries.get(position).getTime();
        if (time.trim().length() == 0) {
            time = mContext.getString(R.string.time_unknown);
        }
        timeText.setText(time);

        TextView tempText = (TextView)itemView.findViewById(R.id.temp);
        Float temperature = mEntries.get(position).getTemperature();
        Integer rounded = Math.round(temperature);
        tempText.setText(rounded.toString() + (char)0x00B0 + "C");

        TextView titleText = (TextView)itemView.findViewById(R.id.title);
        String title = mEntries.get(position).getTitle();
        if (title.trim().length() == 0) {
            title = mContext.getString(R.string.conditions_unknown);
        }
        titleText.setText(title);

        TextView descriptionText = (TextView)itemView.findViewById(R.id.description);
        String description = mEntries.get(position).getDescription();
        if (description.trim().length() == 0) {
            description = mContext.getString(R.string.conditions_unknown);
        }
        descriptionText.setText(description);

        String location = mEntries.get(position).getCity();
        if (location.trim().length() == 0) {
            location = mContext.getString(R.string.location_unknown);
        }
        ((AppCompatActivity)mContext).getSupportActionBar().setTitle(location);

        Resources resources = mContext.getResources();
        String name = "icon_" + mEntries.get(position).getIconName();
        final int resourceId = resources.getIdentifier(name, "drawable",
                mContext.getPackageName());

        if (resourceId != 0) {
            ImageView iv = (ImageView) itemView.findViewById(R.id.weather_icon);
            iv.setImageResource(resourceId);
        }

        return itemView;
    }

    public void upDateEntries(ArrayList<WeatherEntry> entries) {
        mEntries = entries;
        notifyDataSetChanged();
    }
}