package com.example.charliemyers.myweather;

import android.content.Context;
import android.os.AsyncTask;
import android.text.format.DateFormat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by charlie.myers on 18/09/2016.
 */
public class WeatherData extends
        AsyncTask<String, String, JSONObject> {

    private String TAG = WeatherData.class.getSimpleName();
    private Context mContext;
    private final DayWeatherListAdapter mAdapter;
    private String mBaseURL;
    private String mPositionParams;
    private Integer mDayIndex;

    public WeatherData(Context context, DayWeatherListAdapter adapter, Integer dayIndex) {
        mContext = context;
        mAdapter = adapter;
        mBaseURL = "http://api.openweathermap.org/data/2.5/forecast?mode=json&units=metric&appid=d9b7441dea671177a81f3b42c620cbe1";
        mDayIndex = dayIndex;
        mPositionParams = "&lon=0&lat=52"; //default to London
    }

    public void setLongLat (double longitude, double latitude) {
        String lon = String.valueOf(longitude);
        String lat = String.valueOf(latitude);
        mPositionParams = "&lon="+lon+"&lat="+lat;
    }


    private String getTime(String time) {
        long timestamp = Long.parseLong(time) * 1000;
        Date netDate = (new Date(timestamp));
        String dateStr = DateFormat.format("H:m a", netDate).toString();
        return dateStr;
    }

    private Integer calcDayIndex(String time) {
        Integer index = 0;
        long timestamp = Long.parseLong(time) * 1000;
        Date netDate = (new Date(timestamp));
        Integer day = Integer.parseInt(DateFormat.format("d", netDate).toString());
        Date now = new Date();
        Integer currDay = Integer.parseInt(DateFormat.format("d", now).toString());
        if (day < currDay) {
            //we've spanned a month
            Calendar mycal = new GregorianCalendar();
            int maxdays = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
            index = (maxdays - currDay) + day;
        } else {
            index = day - currDay;
        }
        return index;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        JSONObject jsonObj = null;
        if (Config.UseLocalFixture) {
            try {
                String jsonStr;
                InputStream is = mContext.getAssets().open("fixture.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                jsonStr = new String(buffer, "UTF-8");
                jsonObj = new JSONObject(jsonStr);
            } catch (IOException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            } catch (JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            try {
                String url = mBaseURL + mPositionParams;
                HTTPHandler handler = new HTTPHandler();
                String jsonStr = handler.makeServiceCall(url);
                Log.d(TAG, "Response from url: " + jsonStr);
                jsonObj = new JSONObject(jsonStr);
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }
        return jsonObj;
    }

    protected void onPostExecute(JSONObject jsonObj) {
        ArrayList<WeatherEntry> entries = new ArrayList<WeatherEntry>();
        try {
            String loc = jsonObj.getJSONObject("city").getString("name");
            JSONArray allEntries = jsonObj.getJSONArray("list");
            for (int i = 0; i < allEntries.length(); i++) {
                JSONObject currObj = allEntries.getJSONObject(i);
                String timestamp = currObj.getString("dt");

                Integer dayIndex = calcDayIndex(timestamp);
                if (dayIndex < mDayIndex) {
                    continue;
                } else if (dayIndex > mDayIndex) {
                    break;
                }

                WeatherEntry entry = new WeatherEntry();
                entry.setCity(loc);
                entry.setTime(getTime(timestamp));

                String temp = currObj.getJSONObject("main").getString("temp");
                entry.setTemperature(Float.parseFloat(temp));

                JSONArray weatherArray = currObj.getJSONArray("weather");
                if (weatherArray.length() > 0) {
                    // for now I'm just going to take the first entry
                    JSONObject first = weatherArray.getJSONObject(0);
                    entry.setIconName(first.getString("icon"));
                    entry.setTitle(first.getString("main"));
                    entry.setDescription(first.getString("description"));
                }

                entries.add(entry);
            }
        }
        catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }

        mAdapter.upDateEntries(entries);
    }
}