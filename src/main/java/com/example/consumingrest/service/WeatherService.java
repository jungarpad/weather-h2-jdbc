package com.example.consumingrest.service;

import com.example.consumingrest.responsemodel.SimpleWeatherResponse;
import com.example.consumingrest.responsemodel.openweathermap.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class WeatherService {
    @Value("${apiKey}")
    private String apiKey;
    final RestTemplate restTemplate;
    final String baseUrl = "https://api.openweathermap.org/data/2.5/weather";
    Logger logger = LoggerFactory.getLogger(WeatherService.class);
    private int cacheDuration = 2;
    private LocalDateTime localDateTime;
    private SimpleWeatherResponse simpleWeatherResponse;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SimpleWeatherResponse getSimpleWeatherResponse() {
        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.minusMinutes(cacheDuration).isAfter(localDateTime) || simpleWeatherResponse == null || localDateTime == null){
            localDateTime = LocalDateTime.now();
            WeatherResponse weatherResponse = restTemplate.getForObject(
                    baseUrl + "?q=Budapest,HU&appid=" + apiKey + "&units=metric&lang=hu", WeatherResponse.class);
            simpleWeatherResponse = new SimpleWeatherResponse(weatherResponse);
        }
        return simpleWeatherResponse;
    }

    public SimpleWeatherResponse getSimpleWeatherResponse(String city) {
        String url = baseUrl + "?q=" + city + "&appid=" + apiKey + "&units=metric&lang=hu";
        WeatherResponse weatherResponse = restTemplate.getForObject(
                url, WeatherResponse.class);
        return new SimpleWeatherResponse(weatherResponse);
    }
}
