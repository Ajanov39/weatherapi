package com.ajanovski.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button btnSearch;
    TextView tvLocation, tvTime, tvTemperature, tvCondition, tvWind,tvHumidity, tvCloud, tvVisibility;
    ImageView imgCondition;
    EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = findViewById(R.id.btnSearch);
        tvLocation = findViewById(R.id.tvLocation);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvTime = findViewById(R.id.tvTime);
        tvCondition = findViewById(R.id.tvCondition);
        tvWind = findViewById(R.id.tvWind);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvCloud = findViewById(R.id.tvCloud);
        tvVisibility = findViewById(R.id.tvVisibility);
        imgCondition = findViewById(R.id.imgCondition);
        etSearch = findViewById(R.id.etSearch);


        Retrofit retrofitClient = new Retrofit.Builder()
                .baseUrl("http://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface service = retrofitClient.create(ApiInterface.class);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<WeatherResponse> call = service
                        .getWeather(getString(R.string.API_KEY), etSearch.getText().toString(), "yes");

                call.enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if (response.code() == 200) {
                            WeatherResponse weather = response.body();

                            tvLocation.setText(weather.getLocation().getName() + ", " + weather.getLocation().getCountry());
                            tvTemperature.setText(weather.getCurrent().getTempC() + " C");
                            tvTime.setText(weather.getLocation().getLocaltime());
                            tvCondition.setText(weather.getCurrent().getCondition().getText());
                            tvWind.setText(weather.getCurrent().getWindKph().toString() + "kmph "
                                    + weather.getCurrent().getWindDir());
                            tvHumidity.setText(weather.getCurrent().getHumidity() + " %");
                            tvCloud.setText(weather.getCurrent().getCloud() + " %");
                            tvVisibility.setText(weather.getCurrent().getVisKm() + " km");

                            Picasso.get().load("https:" + weather.getCurrent().getCondition().getIcon()).into(imgCondition);

                        } else {
                            try {
                                Toast.makeText(MainActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        try {
                            Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



    }
}