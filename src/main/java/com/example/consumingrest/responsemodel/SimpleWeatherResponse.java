package com.example.consumingrest.responsemodel;

import com.example.consumingrest.responsemodel.openweathermap.WeatherResponse;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class SimpleWeatherResponse {
    private String city;
    private Timestamp timeStamp;
    private double feelsLike;
    private double humidity;
    private double pressure;
    private double temp;
    private double tempMin;
    private double tempMax;
    private String description;
    private String icon;

    public SimpleWeatherResponse(WeatherResponse weatherResponse) {
        this.city = weatherResponse.city();
        this.timeStamp = weatherResponse.time_stamp();
        this.feelsLike = weatherResponse.main().feelsLike();
        this.humidity = weatherResponse.main().humidity();
        this.pressure = weatherResponse.main().pressure();
        this.temp = weatherResponse.main().temp();
        this.tempMin = weatherResponse.main().tempMin();
        this.tempMax = weatherResponse.main().tempMax();
        this.description = weatherResponse.weathers().get(0).description();
        this.icon = weatherResponse.weathers().get(0).icon();
    }

    public SimpleWeatherResponse(String city, Timestamp timeStamp, double feelsLike, double temp, String description) {
        this.city = city;
        this.timeStamp = timeStamp;
        this.feelsLike = feelsLike;
        this.temp = temp;
        this.description = description;
    }
}
