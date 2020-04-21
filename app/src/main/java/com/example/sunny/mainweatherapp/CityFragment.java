package com.example.sunny.mainweatherapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunny.mainweatherapp.Common.Common;
import com.example.sunny.mainweatherapp.Model.WeatherResult;
import com.example.sunny.mainweatherapp.Retrofit.OpenWeatherMapIO;
import com.example.sunny.mainweatherapp.Retrofit.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.label305.asynctask.SimpleAsyncTask;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

// a fragment is needed for the city list to allow it to function
 // creates a UI temp in layout to edit
// the cityFragment contains stuff used in Today's weather Fragment
/**
 * A simple {@link Fragment} subclass.
 */
public class CityFragment extends Fragment {

    private List<String> lstCities;
    private MaterialSearchBar searchBar;

    ImageView img_weather;
    TextView txt_city_name,txt_humidity, txt_sunrise, txt_sunset, txt_temperature, txt_description, txt_date_time, txt_wind, txt_geo_coords;
    LinearLayout weather_panel;
    ProgressBar loading;

    CompositeDisposable compositeDisposable;
    OpenWeatherMapIO mService;

    static CityFragment instance;

    public static CityFragment getInstance(){
        if (instance == null)
            instance = new CityFragment();
        return instance;
    }

    public CityFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(OpenWeatherMapIO.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_city, container, false);

        img_weather = (ImageView) itemView.findViewById(R.id.img_weather);
        txt_city_name = (TextView) itemView.findViewById(R.id.txt_city_name);
        txt_humidity = (TextView) itemView.findViewById(R.id.txt_humidity);
        txt_sunrise = (TextView) itemView.findViewById(R.id.txt_sunrise);
        txt_sunset = (TextView) itemView.findViewById(R.id.txt_sunset);
        txt_temperature = (TextView) itemView.findViewById(R.id.txt_temperature);
        txt_description = (TextView) itemView.findViewById(R.id.txt_description);
        txt_date_time = (TextView) itemView.findViewById(R.id.txt_date_time);
        txt_wind = (TextView) itemView.findViewById(R.id.txt_wind);
        txt_geo_coords = (TextView) itemView.findViewById(R.id.txt_geo_coords);

        weather_panel = (LinearLayout) itemView.findViewById(R.id.weather_panel);
        loading = (ProgressBar) itemView.findViewById(R.id.loading);

        searchBar = (MaterialSearchBar) itemView.findViewById(R.id.searchBar);
        searchBar.setEnabled(false);

        // the asyncTask library will be used in a class ot load the cities list for the user
        new LoadCities().execute();

        return itemView;
    }
    // a inner class that will let LoadCities() work
    private class LoadCities extends SimpleAsyncTask<List<String>> {
    // an override for the extends so that the function works
        @Override
        protected List<String> doInBackgroundSimple() {
            lstCities = new ArrayList<>();
            // a try that will let the city list dataset to work
            // a string builder is created and a stream to the dataset is made
            try{
                StringBuilder builder = new StringBuilder();
                InputStream is = getResources().openRawResource(R.raw.city_list);
                GZIPInputStream gzipInputStream = new GZIPInputStream(is);
                // adding a catch so it doesnt give an error, since streams get an error
                // when a input stream is connected
                // a input stream reader is created so that stream can work
                // also a bufferedReader so it can read the text from the input stream
                InputStreamReader reader = new InputStreamReader(gzipInputStream);
                BufferedReader in = new BufferedReader(reader);

                String read;
                while ((read = in.readLine()) != null)
                    builder.append(read);
                lstCities = new Gson().fromJson(builder.toString(), new TypeToken<List<String>>(){}.getType());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return lstCities;
        }

        @Override
        protected void onSuccess(final List<String> listCity) {
            super.onSuccess(listCity);

            // so the searchbar works and the user can interact with it
            // also a text watcher so it responds to the input the user has done
            searchBar.setEnabled(true);
            searchBar.addTextChangeListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    List<String> suggest = new ArrayList<>();
                    // a for loop to make sure the search is done in lower case
                    for (String search : listCity) {
                        if (search.toLowerCase().contains(searchBar.getText().toLowerCase()))
                            suggest.add(search);
                    }
                    // the last location will be suggested to the user
                    searchBar.setLastSuggestions(suggest);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            // an action listner for the search bar
            searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
                @Override
                public void onSearchStateChanged(boolean enabled) {

                }

                @Override
                public void onSearchConfirmed(CharSequence text) {
                    getWeatherInformation(text.toString());

                    searchBar.setLastSuggestions(listCity);
                }

                @Override
                public void onButtonClicked(int buttonCode) {

                }
            });
            searchBar.setLastSuggestions(listCity);
            // so the loading bar can go once the dataset has been loaded
            loading.setVisibility(View.GONE);

        }
    }

    private void getWeatherInformation(String cityName) {
            compositeDisposable.add(mService.getWeatherByCity(cityName,
                    Common.APP_ID,
                    "metric")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<WeatherResult>() {
                        @Override
                        public void accept(WeatherResult weatherResult) throws Exception {

                            // Loading image for the app. Displays the weather as an image
                            Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                                    .append(weatherResult.getWeather().get(0).getIcon())
                                    .append(".png").toString()).into(img_weather);

                            // Loading the information needed for the display panel for the app
                            txt_city_name.setText(weatherResult.getName());
                            txt_description.setText(new StringBuilder("Weather in ")
                                    .append(weatherResult.getName()).toString());
                            txt_temperature.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp()))
                                    .append("°C").toString());
                            txt_date_time.setText(Common.convertUnixToDate(weatherResult.getDt()));
                            txt_humidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity()))
                                    .append(" %").toString());
                            txt_sunrise.setText(Common.convertUnixToHour(weatherResult.getSys().getSunrise()));
                            txt_sunset.setText(Common.convertUnixToHour(weatherResult.getSys().getSunset()));
                            txt_geo_coords.setText(new StringBuilder(weatherResult.getCoord().toString()).toString());

                            // Displaying the panel of weather information
                            weather_panel.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.GONE);



                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Toast.makeText(getActivity(), ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })


            );
    }
    // a destroy to stop the search task
    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
    // a on stop to stop the search task
    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
