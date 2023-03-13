package com.example.androidweather;



import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText etCity;
    TextView tvResults;
    Button weatherNow, weather24, weather1Day, weather5Day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherNow = findViewById(R.id.btnGetnow);
        weather24 = findViewById(R.id.btnGet24);
        weather1Day = findViewById(R.id.btnGet1x24);
        weather5Day = findViewById(R.id.btnGet5x24);
        etCity = findViewById(R.id.etCity);
        tvResults = findViewById(R.id.tvResults);

    }


    public void getWeatherDetails(View view) {
        String tempUrl = "";
        String city = etCity.getText().toString().trim();


        if (city.equals("")) {
            tvResults.setText("City field can not be empty");
        } else {
            String appid = "be082770d45d4fc781332903230703";
            String url = "http://api.weatherapi.com/v1/";

                tempUrl = url + "forecast.json"  + "?key=" + appid + "&q=" + city;
            }
            StringRequest stringRequest = new StringRequest(Request.Method.GET, tempUrl, response -> {
               Log.d("response", response);
                String output = "";
                try {
                   JSONObject jsonResponse = new JSONObject(response);
                    JSONObject jsonObjectCurrent = jsonResponse.getJSONObject("current");
                    JSONObject jsonObjectCondition = jsonObjectCurrent.getJSONObject("condition");
                    String currentCondition = jsonObjectCondition.getString("text");
//                    String iconObj = jsonObjectCondition.getString("icon");
                    JSONObject jsonObjectLocation = jsonResponse.getJSONObject("location");
                    double temp_f = jsonObjectCurrent.getDouble("temp_f");
                    double feelslike_f = jsonObjectCurrent.getDouble("feelslike_f");
//                    float pressure_in = jsonObjectCurrent.getInt("pressure_in");
//                    int humidity = jsonObjectCurrent.getInt("humidity");
                    double wind_mph = jsonObjectCurrent.getDouble("wind_mph");
                    double gust_mph = jsonObjectCurrent.getDouble("gust_mph");
                    int clouds = jsonObjectCurrent.getInt("cloud");
                    float  precip_in = jsonObjectCurrent.getInt("precip_in");
                       String regionName = jsonObjectLocation.getString("region");
                    String cityName = jsonObjectLocation.getString("name");
                    tvResults.setTextColor(Color.rgb(255,255,255));
                    output += cityName + ", " + regionName + "\n"
                            + "\n Current weather: " + currentCondition
                            + "\n Temp: " + temp_f + " °F"
                            + "\n Feels Like: " + feelslike_f + " °F"
                            + "\n Wind Speed: " + wind_mph + "mph"
                            + "\n Wind Gust: " + gust_mph + "mph"
                            + "\n Cloud Cover: " + clouds + "%"
                            + "\n Rainfall: " + precip_in + " in.";
//                            + "\n Humidity: " + humidity + "%"
//                            + "\n Barometric Pressure: " + pressure_in + " in.";
                    tvResults.setText(output);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> Toast.makeText(getApplicationContext(),
                        error.toString().trim(), Toast.LENGTH_SHORT).show());

            MySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
        }
   }
