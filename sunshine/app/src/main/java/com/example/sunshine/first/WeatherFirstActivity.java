package com.example.sunshine.first;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sunshine.R;
import com.example.sunshine.data.SunshinePreferences;
import com.example.sunshine.utilities.NetworkUtils;
import com.example.sunshine.utilities.OpenWeatherJsonUtils;

import java.net.URL;

public class WeatherFirstActivity extends AppCompatActivity {

    private TextView mWeatherTextView;
    // COMPLETED (6) Add a TextView variable for the error message display
    private TextView mErrorMessageDisplay;

    // COMPLETED (16) Add a ProgressBar variable to show and hide the progress bar
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        /*
         * Using findViewById, we get a reference to our TextView from xml. This allows us to
         * do things like set the text of the TextView.
         */
        mWeatherTextView = findViewById(R.id.tv_weather_data);
        // COMPLETED (7) Find the TextView for the error message using findViewById
        /* This TextView is used to display errors and will be hidden if there are no errors */
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        // COMPLETED (17) Find the ProgressBar using findViewById

        /*
         * The ProgressBar that will indicate to the user that we are loading data. It will be
         * hidden when no data is loading.
         *
         * Please note: This so called "ProgressBar" isn't a bar by default. It is more of a
         * circle. We didn't make the rules (or the names of Views), we just follow them.
         */
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        // COMPLETED (4) Delete the dummy weather data. You will be getting REAL data from the Internet in this lesson.

        // COMPLETED (3) Delete the for loop that populates the TextView with dummy data

        // COMPLETED (9) Call loadWeatherData to perform the network request to get the weather
        /* Once all of our views are setup, we can load the weather data. */
        loadWeatherData();
    }

    // COMPLETED (8) Create a method that will get the user's preferred location and execute your new AsyncTask and call it loadWeatherData

    /**
     * This method will get the user's preferred location for weather, and then tell some
     * background method to get the weather data in the background.
     */
    private void loadWeatherData() {
        // COMPLETED (20) Call showWeatherDataView before executing the AsyncTask
        showWeatherDataView();
        String location = SunshinePreferences.getPreferredWeatherLocation(this);
        new FetchWeatherTask().execute(location);
    }
    // COMPLETED (8) Create a method called showWeatherDataView that will hide the error message and show the weather data

    /**
     * This method will make the View for the weather data visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showWeatherDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mWeatherTextView.setVisibility(View.VISIBLE);
    }

    // COMPLETED (9) Create a method called showErrorMessage that will hide the weather data and show the error message

    /**
     * This method will make the error message visible and hide the weather
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mWeatherTextView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    // COMPLETED (5) Create a class that extends AsyncTask to perform network requests
    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {
        // COMPLETED (18) Within your AsyncTask, override the method onPreExecute and show the loading indicator
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        // COMPLETED (6) Override the doInBackground method to perform your network requests
        @Override
        protected String[] doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String location = params[0];
            //location = "94043,USA"
            URL weatherRequestUrl = NetworkUtils.buildUrl(location);
            Log.d("Url", weatherRequestUrl.toString());
//            String url= "https://andfun-weather.udacity.com/staticweather?q=94043%2CUSA&mode=json&units=metric&cnt=14";
//            try {
//                URL per = new URL(url);
//                HttpURLConnection urlConnection2 = (HttpURLConnection) per.openConnection();
//                Log.d("HttpURLConnection2",urlConnection2.toString());
//                InputStream in= urlConnection2.getInputStream();
//                BufferedReader buf = new BufferedReader(new InputStreamReader(in));
//                while(true) {
//                    String ex = buf.readLine();
////                    buf.r;
//                    Log.d("exampel",ex);
//                    if(ex.length()==0||ex==null){
//                        break;
//                    }
//                }
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


            try {
                String jsonWeatherResponse = NetworkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl);
                Log.d("jsonWeatherResponse", jsonWeatherResponse);
                // jsonWeatherResponse is the full json text
                String[] simpleJsonWeatherData = OpenWeatherJsonUtils
                        .getSimpleWeatherStringsFromJson(WeatherFirstActivity.this, jsonWeatherResponse);
                for (int i = 0; i < simpleJsonWeatherData.length; i++) {
                    Log.d("simpleJsonWeatherData", simpleJsonWeatherData[i]);
                }

                return simpleJsonWeatherData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        //        // COMPLETED (7) Override the onPostExecute method to display the results of the network request
//        @Override
//        protected void onPostExecute(String[] weatherData) {
//            if (weatherData != null) {
//                /*
//                 * Iterate through the array and append the Strings to the TextView. The reason why we add
//                 * the "\n\n\n" after the String is to give visual separation between each String in the
//                 * TextView. Later, we'll learn about a better way to display lists of data.
//                 */
//                for (String weatherString : weatherData) {
//                    mWeatherTextView.append((weatherString) + "\n\n\n");
//                }
//            }
//        }
        @Override
        protected void onPostExecute(String[] weatherData) {
            // COMPLETED (19) As soon as the data is finished loading, hide the loading indicator
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (weatherData != null) {
                // COMPLETED (11) If the weather data was not null, make sure the data view is visible
                showWeatherDataView();
                /*
                 * Iterate through the array and append the Strings to the TextView. The reason why we add
                 * the "\n\n\n" after the String is to give visual separation between each String in the
                 * TextView. Later, we'll learn about a better way to display lists of data.
                 */
                for (String weatherString : weatherData) {
                    mWeatherTextView.append((weatherString) + "\n\n\n");
                }
            } else {
                // COMPLETED (10) If the weather data was null, show the error message
                showErrorMessage();
            }
        }
    }

    // COMPLETED (5) Override onCreateOptionsMenu to inflate the menu for this Activity
    // COMPLETED (6) Return true to display the menu
//     TODO Return true to display the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.forecast, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    // COMPLETED (7) Override onOptionsItemSelected to handle clicks on the refresh button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            mWeatherTextView.setText("");
            loadWeatherData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}