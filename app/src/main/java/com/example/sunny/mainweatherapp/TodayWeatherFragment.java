package com.example.sunny.mainweatherapp;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import com.squareup.picasso.Picasso;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */

    // this is the today's weather fragment, where it is a instance so it works with the other classes
public class TodayWeatherFragment extends Fragment {

    // here we call the features from the UI design from the fragment
    // we get the imageViewer so the icon displays properly
    // we call get the progress bar which runs at the start of the app to show that the app is working and is getting the user's location information
    ImageView img_weather;
    TextView txt_city_name,txt_humidity, txt_sunrise, txt_sunset, txt_temperature, txt_description, txt_date_time, txt_wind, txt_geo_coords;
    LinearLayout weather_panel;
    ProgressBar loading;

    // here we get the queries from the interface and call it mService
    CompositeDisposable compositeDisposable;
    OpenWeatherMapIO mService;

    // here we make the fragment an instance so it can be called easily
    static TodayWeatherFragment instance;

    public static TodayWeatherFragment getInstance(){
        if (instance == null)
            instance = new TodayWeatherFragment();
        return instance;
    }
    // i made the fragment apply with the retrofit fragment since they are the same, but they will work together
    public TodayWeatherFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(OpenWeatherMapIO.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // informtion for the layout for this fragment
        // we also we get the ids from the UI so it works when the app is running
        View itemView = inflater.inflate(R.layout.fragment_today_weather2, container, false);

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

        getWeatherInformation();

        return itemView;
    }

    private void getWeatherInformation() {
        compositeDisposable.add(mService.getWeatherByLatLng(String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
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
    // a on destroy to destroy the task
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
