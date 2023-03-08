package com.example.androidweather;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    EditText etCity, etCountry;
    TextView tvResults;
    private final String url = "http://api.weatherapi.com/v1/";
    private final String appid = "be082770d45d4fc781332903230703";
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etCity = findViewById(R.id.etCity);
        etCountry = findViewById(R.id.etCountry);
        tvResults = findViewById(R.id.tvResults);

    }

    public void getWeatherDetails(View view) {
        String tempUrl = "";
        String city = etCity.getText().toString().trim();
        String country = etCountry.getText().toString().trim();
        String unit = "imperial";

        if (city.equals("")) {
            tvResults.setText("City field can not be empty");
        } else {
            if (!country.equals("")) {
                tempUrl = url + "current.json" + "?q=" + city + "," + country + "&units=" + unit + "&key=" + appid;
            } else {
                tempUrl = url + "current.json"  + "?key=" + appid + "&q=" + city;
            }
            StringRequest stringRequest = new StringRequest(Request.Method.GET, tempUrl, new Response.Listener<String>(){
                @Override
                public void onResponse(String response) {
                   Log.d("response", response);
                    String output = "";
                    try {
                       JSONObject jsonResponse = new JSONObject(response);
                        JSONObject jsonObjectCurrent = jsonResponse.getJSONObject("current");
                        JSONObject jsonObjectCondition = jsonObjectCurrent.getJSONObject("condition");
                        String textObj = jsonObjectCondition.getString("text");
                        JSONObject jsonObjectLocation = jsonResponse.getJSONObject("location");
                        double temp_f = jsonObjectCurrent.getDouble("temp_f");
                        double feelslike_f = jsonObjectCurrent.getDouble("feelslike_f");
                        float pressure_in = jsonObjectCurrent.getInt("pressure_in");
                        int humidity = jsonObjectCurrent.getInt("humidity");
                        double wind_mph = jsonObjectCurrent.getDouble("wind_mph");
                        double gust_mph = jsonObjectCurrent.getDouble("gust_mph");
                        int clouds = jsonObjectCurrent.getInt("cloud");
//                        float  precip_in = jsonObjectCurrent.getInt(" precip_in");
//                       String countryName = jsonObjectSys.getString("country");
                        String cityName = jsonObjectLocation.getString("name");
                        tvResults.setTextColor(Color.rgb(255,255,255));
                        output += "Current weather of " + cityName
                                + "\n" + textObj
                                + "\n Temp: " + df.format(temp_f) + " °F"
                                + "\n Feels Like: " + df.format(feelslike_f) + " °F"
                                + "\n Wind Speed: " + wind_mph + "mph"
                                + "\n Wind Gust: " + gust_mph + "mph"
                                + "\n Cloud Cover: " + clouds + "%"
//                                + "\n Rainfall: " + df.format(precip_in) + "inches"
                                + "\n Humidity: " + humidity + "%"
                                + "\n Barometric Pressure: " + df.format(pressure_in);
                        tvResults.setText(output);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),
                            error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
   }
}