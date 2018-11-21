package com.example.edu.restapi_asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttonLondon, buttonSeoul;
    EditText editTextResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLondon = findViewById(R.id.buttonLondon);
        buttonLondon.setOnClickListener(this);
        buttonSeoul = findViewById(R.id.buttonSeoul);
        buttonSeoul.setOnClickListener(this);
        editTextResult = findViewById(R.id.editTextResult);
    }

    @Override
    public void onClick(View view) {
        String weather = null;
        OpenWeatherAPITASK task = new OpenWeatherAPITASK();

        if(view.getId() == R.id.buttonLondon) {
            try {
                weather = task.execute("London").get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            editTextResult.setText(weather);
        }
    }



    class OpenWeatherAPITASK extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String urlString = "https://api.openweathermap.org/data/2.5/weather" + "?q=" + id + "&appid=0b1183604c063f3a2bd9849d5bffb91b";
            // https://openweathermap.org/data/2.5/weather?q=id&appid=0b1183604c063f3a2bd9849d5bffb91b
            //https://openweathermap.org/data/2.5/weather?q=id&appid=0b1183604c063f3a2bd9849d5bffb91b
            String weather = null;
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();
                /*byte[] buffer = new byte[1000];
                weather = String.valueOf(in.read(buffer));*/

                byte[] buffer = new byte[1000];
                in.read(buffer);
                weather = new String(buffer);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return weather;
        }


    }
}
