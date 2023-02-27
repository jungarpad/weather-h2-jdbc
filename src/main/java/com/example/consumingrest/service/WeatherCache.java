package com.example.consumingrest.service;

import com.example.consumingrest.responsemodel.SimpleWeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class WeatherCache {
    private Map<String, SimpleWeatherResponse> lastUpdateWeather = new HashMap<>();
    private Map<String, LocalDateTime> lastUpdated = new HashMap<>();

    @Value("${weather.cache.timout:2}")
    private Integer timeOutMinutes;

    public boolean containsValidResponse(String city){
        if (!lastUpdated.containsKey(city)) return false;
        LocalDateTime updateTime = lastUpdated.get(city);
        LocalDateTime now = LocalDateTime.now();
        return updateTime.plusMinutes(timeOutMinutes).isAfter(now);
    }

    public SimpleWeatherResponse get(String city) {
        return lastUpdateWeather.get(city);
    }

    public void put(String city, SimpleWeatherResponse simpleWeatherResponse){
        lastUpdateWeather.put(city, simpleWeatherResponse);
        lastUpdated.put(city, LocalDateTime.now());
    }
}
