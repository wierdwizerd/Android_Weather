package com.example.androidweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
EditText etCity, etCountry;
TextView tvResults;
private final String url = "http://api.openweathermap.org/data/2.5/weather";
private final String appid = "a2eb7ee73020d31b3a6c48f6b51b44f6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getWeatherDetails(View view) {
    }
}