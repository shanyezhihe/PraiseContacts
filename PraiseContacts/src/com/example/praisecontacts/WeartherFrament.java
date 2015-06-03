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

	private TextView City;
	private TextView Year;
	private TextView Date;
	private TextView Week;
	private TextView Temp;
	private TextView Day1;
	private TextView Day2;
	private TextView Day3;
	private TextView Day1Bg;
	private TextView Day2Bg;
	private TextView Day3Bg;
	private TextView YiContent;
	private TextView JiContent;
	private ImageView WeatherIcon;
	private MyClick myClick;
	
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
				
		View view = inflater.inflate(R.layout.fragment_weather, container,
				false);
		
		myClick = new MyClick();
		
		City = (TextView) view.findViewById(R.id.weather_city);
		Year = (TextView) view.findViewById(R.id.weather_year);
		Date = (TextView) view.findViewById(R.id.weather_date);
		Week = (TextView) view.findViewById(R.id.weather_week);
		Temp = (TextView) view.findViewById(R.id.weather_temperature);	
		Day1 = (TextView) view.findViewById(R.id.weather_day_now);
		Day2 = (TextView) view.findViewById(R.id.weather_day_after);
		Day3 = (TextView) view.findViewById(R.id.weather_day_after_after);
		Day1Bg = (TextView) view.findViewById(R.id.weather_day_now_color);
		Day2Bg = (TextView) view.findViewById(R.id.weather_day_after_color);
		Day3Bg = (TextView) view.findViewById(R.id.weather_day_after_after_color);
		
		
	  
		
		WeatherIcon = (ImageView) view.findViewById(R.id.weather_icon);
		
		Day1Bg.setBackgroundResource(R.drawable.btn_cal_pre);
		Day2Bg.setBackgroundResource(R.drawable.btn_cal);
		Day3Bg.setBackgroundResource(R.drawable.btn_cal);
		Day1.setTextColor(0xFFff0000);
		Day2.setTextColor(0xFFffffff);
		Day3.setTextColor(0xFFffffff);
		
		
		Day1Bg.setOnClickListener(myClick);
		Day2Bg.setOnClickListener(myClick);
		Day3Bg.setOnClickListener(myClick);
		
	
				
		// INIT -- ��ȡ��ǰ����
		Date date = new Date();// ȡʱ��
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		day1 = formatter.format(date);
		
		SharedPreferences saveRequestLHL = getActivity().getSharedPreferences("LHLREQUESTDATE",
				0);
		lastRequestLHL = saveRequestLHL.getString("LHLlastrequestdate", null);
		if (day1.equals(lastRequestLHL))
			haveRequestLHL = false;
		else
			haveRequestLHL = false;
		
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00")); 
	
		Year.setText(String.valueOf(c.get(Calendar.YEAR)) + "��");
		
		Date.setText(String.valueOf(c.get(Calendar.MONTH) + 1) + "��" 
				+ String.valueOf(c.get(Calendar.DAY_OF_MONTH)) + "��");	
		Week.setText("��" + getWeek(c.get(Calendar.DAY_OF_WEEK)));
		Day1.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
		
		if(isBigMonth(c.get(Calendar.MONTH) + 1)){                 //����
			if(c.get(Calendar.DAY_OF_MONTH) == 31){
				Day2.setText(String.valueOf((c.get(Calendar.DAY_OF_MONTH)+1)%31));
				Day3.setText(String.valueOf((c.get(Calendar.DAY_OF_MONTH)+2)%31));
			}else if(c.get(Calendar.DAY_OF_MONTH) == 30){
				Day2.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)+1));
				Day3.setText(String.valueOf((c.get(Calendar.DAY_OF_MONTH)+2)%31));
			}else{
				Day2.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)+1));
				Day3.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)+2));
			}
		}else if(isSmallMonth(c.get(Calendar.MONTH) + 1)){      //С��
			if(c.get(Calendar.DAY_OF_MONTH) == 30){
				Day2.setText(String.valueOf((c.get(Calendar.DAY_OF_MONTH)+1)%30));
				Day3.setText(String.valueOf((c.get(Calendar.DAY_OF_MONTH)+2)%30));
			}else if(c.get(Calendar.DAY_OF_MONTH) == 29){
				Day2.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)+1));
				Day3.setText(String.valueOf((c.get(Calendar.DAY_OF_MONTH)+2)%30));
			}else{
				Day2.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)+1));
				Day3.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)+2));
			}
		}else if(isLeapYear(c.get(Calendar.MONTH) + 1)) {       //����2��  
			if(c.get(Calendar.DAY_OF_MONTH) == 29){
				Day2.setText(String.valueOf((c.get(Calendar.DAY_OF_MONTH)+1)%29));
				Day3.setText(String.valueOf((c.get(Calendar.DAY_OF_MONTH)+2)%29));
			}else if(c.get(Calendar.DAY_OF_MONTH) == 28){
				Day2.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)+1));
				Day3.setText(String.valueOf((c.get(Calendar.DAY_OF_MONTH)+2)%29));
			}else{
				Day2.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)+1));
				Day3.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)+2));
			}
		}else if(!(isLeapYear(c.get(Calendar.MONTH) + 1))){     //������2�� 
			if(c.get(Calendar.DAY_OF_MONTH) == 28){
				Day2.setText(String.valueOf((c.get(Calendar.DAY_OF_MONTH)+1)%28));
				Day3.setText(String.valueOf((c.get(Calendar.DAY_OF_MONTH)+2)%28));
			}else if(c.get(Calendar.DAY_OF_MONTH) == 27){
				Day2.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)+1));
				Day3.setText(String.valueOf((c.get(Calendar.DAY_OF_MONTH)+2)%28));
			}else{
				Day2.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)+1));
				Day3.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)+2));
			}
		}
				
		mQueue = Volley.newRequestQueue(getActivity());
		sid = loadSid();	
		
		
		
		JsonObjectRequest mWeatherRequest = new JsonObjectRequest(
				Method.GET,"https://api.thinkpage.cn/v2/weather/all.json?city=����&language=zh-chs&unit=c&aqi=city&key="+ Key, null,
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
								
								//�����Թ��´�ʹ��
								SharedPreferences savedWeather = getActivity().getSharedPreferences("SAVEDWEATHER",
										0);
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
								
								City.setText(data.getString("city_name"));
								Temp.setText(now.getString("temperature") + String.valueOf(centigrade));
								WeatherIcon.setImageResource(weatherIcons[Integer.parseInt(now.getString("code"))]);													
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
		mLastRequestTime = loadLastRequestTime();
		mCurrentRequestTime = System.currentTimeMillis();
		if((mCurrentRequestTime - mLastRequestTime)/1000 >= 10800){
			saveLastRequestTime(mCurrentRequestTime);
			mQueue.add(mWeatherRequest);
		}else{
			SharedPreferences loadWeather = getActivity().getSharedPreferences("SAVEDWEATHER",
					0);
			
			City.setText("����");
			Temp.setText(loadWeather.getString("Day1Temp", null) + String.valueOf(centigrade));		
			WeatherIcon.setImageResource(weatherIcons[Integer.parseInt(loadWeather.getString("Day1IconIndex", "0"))]);
		}
		
		return view;
	}
	
	public class MyClick implements OnClickListener{
		SharedPreferences loadWeather = getActivity().getSharedPreferences("SAVEDWEATHER",
				0);
		SharedPreferences loadLHL = getActivity().getSharedPreferences("SAVEDLHL",
				0);
		
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.weather_day_now_color:
				Day1Bg.setBackgroundResource(R.drawable.btn_cal_pre);
				Day2Bg.setBackgroundResource(R.drawable.btn_cal);
				Day3Bg.setBackgroundResource(R.drawable.btn_cal);
				
				Day1.setTextColor(0xFFff0000);
				Day2.setTextColor(0xFFffffff);
				Day3.setTextColor(0xFFffffff);
				
				Year.setText(String.valueOf(c.get(Calendar.YEAR)) + "��");
				Date.setText(String.valueOf(c.get(Calendar.MONTH) + 1) + "��" 
						+ String.valueOf(c.get(Calendar.DAY_OF_MONTH)) + "��");	
				Week.setText("��" + getWeek(c.get(Calendar.DAY_OF_WEEK)));
				
				Temp.setText(loadWeather.getString("Day1Temp", null) + String.valueOf(centigrade));
				
				WeatherIcon.setImageResource(weatherIcons[Integer.parseInt(loadWeather.getString("Day1IconIndex", "0"))]);
				
				
				break;
			case R.id.weather_day_after_color:
				Day2Bg.setBackgroundResource(R.drawable.btn_cal_pre);
				Day1Bg.setBackgroundResource(R.drawable.btn_cal);
				Day3Bg.setBackgroundResource(R.drawable.btn_cal);
				
				Day2.setTextColor(0xFFff0000);
				Day1.setTextColor(0xFFffffff);
				Day3.setTextColor(0xFFffffff);
				
				if(isBigMonth(c.get(Calendar.MONTH) + 1)){                 //����
					if(c.get(Calendar.DAY_OF_MONTH) == 31){
						if((c.get(Calendar.MONTH) + 1) == 12){   
							
							Year.setText(String.valueOf(c.get(Calendar.YEAR) + 1) + "��");
							Date.setText( String.valueOf(1) + "��" 
									+ String.valueOf(1) + "��");
						}else{
							Year.setText(String.valueOf(c.get(Calendar.YEAR)) + "��");
							Date.setText(String.valueOf(c.get(Calendar.MONTH) + 1 + 1) + "��" 
									+ String.valueOf(1) + "��");
						}
					}else{
						Year.setText(String.valueOf(c.get(Calendar.YEAR)) + "��");
						Date.setText(String.valueOf(c.get(Calendar.MONTH) + 1) + "��" 
								+ String.valueOf(c.get(Calendar.DAY_OF_MONTH)+1) + "��");	
					}
				}else if(isSmallMonth(c.get(Calendar.MONTH) + 1)){      //С��
					if(c.get(Calendar.DAY_OF_MONTH) == 30){
						Year.setText(String.valueOf(c.get(Calendar.YEAR)) + "��");
						Date.setText(String.valueOf(c.get(Calendar.MONTH) + 1 + 1) + "��" 
								+ String.valueOf(1) + "��");
					}else{
						Year.setText(String.valueOf(c.get(Calendar.YEAR)) + "��");
						Date.setText(String.valueOf(c.get(Calendar.MONTH) + 1) + "��" 
								+ String.valueOf(c.get(Calendar.DAY_OF_MONTH)+1) + "��");
					}
				}else if(isLeapYear(c.get(Calendar.MONTH) + 1)) {       //����2��  
					if(c.get(Calendar.DAY_OF_MONTH) == 29){
						Year.setText(String.valueOf(c.get(Calendar.YEAR)) + "��");
						Date.setText(String.valueOf(c.get(Calendar.MONTH) + 1 + 1) + "��" 
								+ String.valueOf(1) + "��");
					}else{
						Year.setText(String.valueOf(c.get(Calendar.YEAR)) + "��");
						Date.setText(String.valueOf(c.get(Calendar.MONTH) + 1) + "��" 
								+ String.valueOf(c.get(Calendar.DAY_OF_MONTH)+1) + "��");
					}
				}else if(!(isLeapYear(c.get(Calendar.MONTH) + 1))){     //������2�� 
					if(c.get(Calendar.DAY_OF_MONTH) == 28){
						Year.setText(String.valueOf(c.get(Calendar.YEAR)) + "��");
						Date.setText(String.valueOf(c.get(Calendar.MONTH) + 1 + 1) + "��" 
								+ String.valueOf(1) + "��");
					}else{
						Year.setText(String.valueOf(c.get(Calendar.YEAR)) + "��");
						Date.setText(String.valueOf(c.get(Calendar.MONTH) + 1) + "��" 
								+ String.valueOf(c.get(Calendar.DAY_OF_MONTH)+1) + "��");
					}
				}
				
				Week.setText("��" + getWeek(c.get(Calendar.DAY_OF_WEEK)+1));
				
				Temp.setText(loadWeather.getString("Day2TempHigh", null) + String.valueOf(centigrade));
				
				WeatherIcon.setImageResource(weatherIcons[Integer.parseInt(loadWeather.getString("Day2IconIndexDay", "0"))]);
				
				
				break;
			case R.id.weather_day_after_after_color:
				Day3Bg.setBackgroundResource(R.drawable.btn_cal_pre);
				Day1Bg.setBackgroundResource(R.drawable.btn_cal);
				Day2Bg.setBackgroundResource(R.drawable.btn_cal);
				
				Day3.setTextColor(0xFFff0000);
				Day2.setTextColor(0xFFffffff);
				Day1.setTextColor(0xFFffffff);
				
				if(isBigMonth(c.get(Calendar.MONTH) + 1)){                 //����
					if(c.get(Calendar.DAY_OF_MONTH) == 31 || c.get(Calendar.DAY_OF_MONTH) == 30){
						if((c.get(Calendar.MONTH) + 1) == 12){  
							Year.setText(String.valueOf(c.get(Calendar.YEAR) + 1) + "��");
							Date.setText(String.valueOf(1) + "��" 
									+ String.valueOf((c.get(Calendar.DAY_OF_MONTH)+2)%31) + "��");
						}else{
							Year.setText(String.valueOf(c.get(Calendar.YEAR)) + "��");
							Date.setText(String.valueOf(c.get(Calendar.MONTH) + 1 + 1) + "��" 
									+ String.valueOf((c.get(Calendar.DAY_OF_MONTH)+2)%31) + "��");
						}
					}else{
						Year.setText(String.valueOf(c.get(Calendar.YEAR)) + "��");
						Date.setText( String.valueOf(c.get(Calendar.MONTH) + 1) + "��" 
								+ String.valueOf(c.get(Calendar.DAY_OF_MONTH)+2) + "��");	
					}
				}else if(isSmallMonth(c.get(Calendar.MONTH) + 1)){      //С��
					if(c.get(Calendar.DAY_OF_MONTH) == 30 || c.get(Calendar.DAY_OF_MONTH) == 29){
						Year.setText(String.valueOf(c.get(Calendar.YEAR)) + "��");
						Date.setText(String.valueOf(c.get(Calendar.MONTH) + 1 + 1) + "��" 
								+ String.valueOf((c.get(Calendar.DAY_OF_MONTH)+2)%30) + "��");
					}else{
						Year.setText(String.valueOf(c.get(Calendar.YEAR)) + "��");
						Date.setText(String.valueOf(c.get(Calendar.MONTH) + 1) + "��" 
								+ String.valueOf(c.get(Calendar.DAY_OF_MONTH)+2) + "��");
					}
				}else if(isLeapYear(c.get(Calendar.MONTH) + 1)) {       //����2��  
					if(c.get(Calendar.DAY_OF_MONTH) == 29 || c.get(Calendar.DAY_OF_MONTH) == 28){
						Year.setText(String.valueOf(c.get(Calendar.YEAR)) + "��");
						Date.setText(String.valueOf(c.get(Calendar.MONTH) + 1 + 1) + "��" 
								+ String.valueOf((c.get(Calendar.DAY_OF_MONTH)+2)%29) + "��");
					}else{
						Year.setText(String.valueOf(c.get(Calendar.YEAR)) + "��");
						Date.setText(String.valueOf(c.get(Calendar.MONTH) + 1) + "��" 
								+ String.valueOf(c.get(Calendar.DAY_OF_MONTH)+2) + "��");
					}
				}else if(!(isLeapYear(c.get(Calendar.MONTH) + 1))){     //������2�� 
					if(c.get(Calendar.DAY_OF_MONTH) == 28 || c.get(Calendar.DAY_OF_MONTH) == 27){
						Year.setText(String.valueOf(c.get(Calendar.YEAR)) + "��");
						Date.setText(String.valueOf(c.get(Calendar.MONTH) + 1 + 1) + "��" 
								+ String.valueOf((c.get(Calendar.DAY_OF_MONTH)+2)%28) + "��");
					}else{
						Year.setText(String.valueOf(c.get(Calendar.YEAR)) + "��");
						Date.setText(String.valueOf(c.get(Calendar.MONTH) + 1) + "��" 
								+ String.valueOf(c.get(Calendar.DAY_OF_MONTH)+2) + "��");
					}
				}	
				Week.setText("��" + getWeek(c.get(Calendar.DAY_OF_WEEK)+2));
				
				Temp.setText(loadWeather.getString("Day3TempHigh", null) + String.valueOf(centigrade));
				
				WeatherIcon.setImageResource(weatherIcons[Integer.parseInt(loadWeather.getString("Day3IconIndexDay", "0"))]);
			
				break;
			default:
				break;
			}
		}
		
	}
	
	

	public String getWeek(int i){
		String week = null;
		if(i > 7) i = i - 7;
		if(i == 1){
			week ="��";
		}else if(i == 2){
			week ="һ";
		}else if(i == 3){
			week ="��";
		}else if(i == 4){
			week ="��";
		}else if(i == 5){
			week ="��";
		}else if(i == 6){
			week ="��";
		}else if(i == 7){
			week ="��";
		}
		return week;
	}
	
	public boolean isBigMonth(int m) {
		if(m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) 
			return true;	
		return false;
	}
	
	public boolean isSmallMonth(int m) {
		if(m == 4 || m == 6 || m == 9 || m == 11) 
			return true;	
		return false;
	}
	
	public boolean isLeapYear(int y) {
		if((y%4==0 && y%100!=0) || y%400==0) 
			return true;	
		return false;
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
