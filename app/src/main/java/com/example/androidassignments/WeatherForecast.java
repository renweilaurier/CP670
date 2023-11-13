package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class WeatherForecast extends AppCompatActivity {

    private Spinner citySpinner;
    private ImageView currentWeatherImageView;
    private TextView currentTemperatureTextView;
    private TextView minTemperatureTextView;
    private TextView maxTemperatureTextView;
    private ProgressBar normalProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("WeatherForecast", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        citySpinner = findViewById(R.id.spinnerCity);
        currentWeatherImageView = findViewById(R.id.imageViewCurrentWeather);
        currentTemperatureTextView = findViewById(R.id.textViewCurrentTemperature);
        minTemperatureTextView = findViewById(R.id.textViewMinTemperature);
        maxTemperatureTextView = findViewById(R.id.textViewMaxTemperature);
        normalProgressBar = findViewById(R.id.progressBarNormal);


        List<String> cityList = Arrays.asList(getResources().getStringArray(R.array.cities));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource (this,
                R.array.cities,android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);
        citySpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        normalProgressBar.setVisibility(View.VISIBLE);
                        citySpinner.setEnabled(false);
                        new ForecastQuery(cityList.get(position)).execute();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

//        new ForecastQuery().execute();
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String currentTemp;
        private String minTemp;
        private String maxTemp;
        private Bitmap picture;
        protected String city;

        ForecastQuery(String city) {
            this.city = city;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(
                        "https://api.openweathermap.org/data/2.5/weather?q=" +
                                city + ",ca&APPID=79cecf493cb6e52d25bb7b7050ff723c&" +
                                "mode=xml&units=metric");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                InputStream in = conn.getInputStream();
                try {
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(XmlPullParser.
                            FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(in, null);
                    int type;
                    //While you're not at the end of the document:
                    while ((type = parser.getEventType())
                            != XmlPullParser.END_DOCUMENT) {
                        //Are you currently at a Start Tag?
                        if (parser.getEventType() == XmlPullParser.START_TAG) {
                            if (parser.getName().equals("temperature")) {
                                currentTemp = parser.getAttributeValue(null, "value");
                                publishProgress(25);
                                minTemp = parser.getAttributeValue(null, "min");
                                publishProgress(50);
                                maxTemp = parser.getAttributeValue(null, "max");
                                publishProgress(75);
                            } else if (parser.getName().equals("weather")) {
                                String iconName = parser.getAttributeValue(null, "icon");
                                String fileName = iconName + ".png";
                                Log.i("WeatherForecast", "Looking for file: " + fileName);
                                if (fileExistance(fileName)) {
                                    FileInputStream fis = null;
                                    try {
                                        fis = openFileInput(fileName);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    Log.i("WeatherForecast", "Found the file locally");
                                    picture = BitmapFactory.decodeStream(fis);
                                } else {
                                    String iconUrl =
                                            "https://openweathermap.org/img/w/"
                                                    + fileName;
                                    picture = getImage(new URL(iconUrl));
                                    FileOutputStream outputStream =
                                            openFileOutput(fileName, MODE_PRIVATE);
                                    picture.compress(Bitmap.CompressFormat.PNG,
                                            80, outputStream);
                                    Log.i("WeatherForecast",
                                            "Downloaded the file from the Internet");
                                    outputStream.flush();
                                    outputStream.close();
                                }
                                publishProgress(100);
                            }
                        }
                        parser.next();
                    }
                } finally {
                    in.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String a) {
            normalProgressBar.setVisibility(View.INVISIBLE);
            citySpinner.setEnabled(true);
            currentWeatherImageView.setImageBitmap(picture);
            currentTemperatureTextView.setText(currentTemp + "°C");
            minTemperatureTextView.setText(minTemp + "°C");
            maxTemperatureTextView.setText(maxTemp + "°C");
        }

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        public Bitmap getImage(URL url) {
            HttpsURLConnection connection = null;
            try {
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
    }
}
