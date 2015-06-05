package com.example.praisecontacts;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


public class WeartherFrament extends Fragment {

	private TextView city_name;
	private TextView tempre_day1;
	private TextView weather_day1;
	private TextView date_day1;
	private ImageView icon_day1;
	private TextView date_day2;
	private TextView day2_low;
	private TextView  day2_high;
	private ImageView icon_day2;
	private TextView date_day3;
	private TextView day3_low;
	private TextView day3_high;
	private ImageView icon_day3;
	
	//private MyClick myClick;
	
	public final Calendar c =  Calendar.getInstance();
	
	public char centigrade = 176;
	
	

	private URL weatherURL;
	private String Key = "XSI7AKYYBY";
	private RequestQueue mQueue;
	
	
	private String sid;
	
	private String day1;
	private String lastRequestLHL;
	private boolean haveRequestLHL;
	
	private long mLastRequestTime;
	private long mCurrentRequestTime;
	
	private SharedPreferences firstLoadWeatherShare ;
	private Editor firstLoadWeather;
	private static int[] weatherIcons = new int[] { R.drawable.sunny,
			R.drawable.clear, R.drawable.fair, R.drawable.fair1,
			R.drawable.cloudy, R.drawable.party_cloudy,
			R.drawable.party_cloudy1, R.drawable.mostly_cloudy,
			R.drawable.mostly_cloudy1, R.drawable.overcast, R.drawable.shower,
			R.drawable.thundershower, R.drawable.thundershower_with_hail,
			R.drawable.light_rain, R.drawable.moderate_rain,
			R.drawable.heavy_rain, R.drawable.storm, R.drawable.heavy_storm,
			R.drawable.severe_storm, R.drawable.ice_rain, R.drawable.sleet,
			R.drawable.snow_flurry, R.drawable.light_snow,
			R.drawable.moderate_snow, R.drawable.heavy_snow,
			R.drawable.snowstorm, R.drawable.dust, R.drawable.sand,
			R.drawable.duststorm, R.drawable.sandstorm, R.drawable.foggy,
			R.drawable.haze, R.drawable.windy, R.drawable.blustery,
			R.drawable.hurricane, R.drawable.tropical_storm,
			R.drawable.tornado, R.drawable.cold, R.drawable.hot };

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
				
		View view = inflater.inflate(R.layout.activity_weather_detail, container,
				false);
		firstLoadWeatherShare = getActivity().getSharedPreferences("FIRSTSHARE",
				0);
		firstLoadWeather = firstLoadWeatherShare.edit();
		firstLoadWeather.putBoolean("ISFIRSTLOAD", true).commit();
		
		//myClick = new MyClick();
		
		city_name = (TextView) view.findViewById(R.id.city_name);
		city_name.setText("广州");
		tempre_day1 = (TextView) view.findViewById(R.id.tempre_day1);
		weather_day1 = (TextView) view.findViewById(R.id.weather_day1);
		date_day1= (TextView) view.findViewById(R.id.date_day1);
		
		icon_day1= (ImageView) view.findViewById(R.id.icon_day1);
		date_day2 = (TextView) view.findViewById(R.id.date_day2);
		day2_low= (TextView) view.findViewById(R.id.day2_low);	
		day2_high = (TextView) view.findViewById(R.id.day2_high);
		icon_day2 = (ImageView) view.findViewById(R.id.icon_day2);
		date_day3 = (TextView) view.findViewById(R.id.date_day3);
		day3_low = (TextView) view.findViewById(R.id.day3_low );
		day3_high= (TextView) view.findViewById(R.id.day3_high );
		icon_day3 = (ImageView) view.findViewById(R.id.icon_day3);

		mQueue = Volley.newRequestQueue(getActivity());
		JsonObjectRequest mWeatherRequest = new JsonObjectRequest(
				Method.GET,"https://api.thinkpage.cn/v2/weather/all.json?"
						+ "city=CHGD000000&language=zh-chs&unit=c&aqi=city&key="+Key, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.e("Test", response.toString());
						try {
							if(response.getString("status").equals("OK")){
								JSONArray weather= response.getJSONArray("weather");
								JSONObject data = (JSONObject)weather.get(0);							
								JSONObject now = data.getJSONObject("now");
								JSONArray future = data.getJSONArray("future");
								JSONObject tomorrow= (JSONObject)future.get(0);	
								JSONObject tomorrow2= (JSONObject)future.get(1);	
								
								//保存以供下次使用
								SharedPreferences savedWeather = getActivity().getSharedPreferences("SAVEDWEATHER",
										0);
								city_name.setText(data.getString("city_name"));
								tempre_day1.setText(now.getString("temperature"));
								weather_day1.setText(now.getString("text"));
								date_day1.setText("今天");
								icon_day1.setImageResource(weatherIcons[Integer.parseInt(now.getString("code"))]);
								date_day2.setText(tomorrow.getString("day"));
								day2_low.setText(tomorrow.getString("low"));
								day2_high.setText(tomorrow.getString("high"));
								icon_day2.setImageResource(weatherIcons[Integer.parseInt(tomorrow.getString("code1"))]);
								date_day3.setText(tomorrow2.getString("day"));
								day3_low.setText(tomorrow2.getString("low"));
								day3_high.setText(tomorrow2.getString("high"));
								icon_day3.setImageResource(weatherIcons[Integer.parseInt(tomorrow2.getString("code1"))]);
								Editor editor = savedWeather.edit();
								editor.putString("City", data.getString("city_name"));
								editor.putString("Day1Temp", now.getString("temperature"));
								editor.putString("Day1Weather", now.getString("text"));
								editor.putString("Day1IconIndex", now.getString("code"));
								editor.putString("Day2TempLow", tomorrow.getString("low"));
								editor.putString("Day2TempHigh", tomorrow.getString("high"));
								editor.putString("Day2Weather", tomorrow.getString("text"));
								editor.putString("Day2IconIndexDay", tomorrow.getString("code1"));
								editor.putString("Day2IconIndexNight", tomorrow.getString("code2"));
								editor.putString("Day3TempLow", tomorrow2.getString("low"));
								editor.putString("Day3TempHigh", tomorrow2.getString("high"));
								editor.putString("Day3Weather", tomorrow2.getString("text"));
								editor.putString("Day3IconIndexDay", tomorrow2.getString("code1"));
								editor.putString("Day3IconIndexNight", tomorrow2.getString("code2"));
								editor.commit();
								
									
							} 
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}
				}) {
		};
		if(firstLoadWeatherShare.getBoolean("ISFIRSTLOAD", true))
		{
			mQueue.add(mWeatherRequest);
			firstLoadWeather.putBoolean("ISFIRSTLOAD", false).commit();
		}
		mLastRequestTime = loadLastRequestTime();
		mCurrentRequestTime = System.currentTimeMillis();
		if((mCurrentRequestTime - mLastRequestTime)/1000 >=10800){
			saveLastRequestTime(mCurrentRequestTime);
			mQueue.add(mWeatherRequest);
		}
			

		return view;
	}

	
	public void saveLastRequestTime(long time) {
		SharedPreferences savedTime = getActivity().getSharedPreferences("SAVEDTIME",
				0);
		Editor editor = savedTime.edit();
		editor.putLong("TIME", time);
		editor.commit();
	}

	public long loadLastRequestTime() {
		SharedPreferences loadTime = getActivity().getSharedPreferences("SAVEDTIME", 0);
		return loadTime.getLong("TIME", 0);
	}
	
	public void saveSid(String sid) {
		SharedPreferences savedSid = getActivity().getSharedPreferences("SAVEDSID",
				0);
		Editor editor = savedSid.edit();
		editor.putString("SID", sid);
		editor.commit();
	}

	public String loadSid() {
		SharedPreferences loadSid = getActivity().getSharedPreferences("SAVEDSID", 0);
		return loadSid.getString("SID", null);
	}

}
