package com.example.consumingrest.service;

import com.example.consumingrest.repository.WeatherDaoJdbc;
import com.example.consumingrest.responsemodel.SimpleWeatherResponse;
import com.example.consumingrest.responsemodel.openweathermap.WeatherResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class WeatherServiceJdbc {
    final RestTemplate restTemplate;
    final String baseUrl = "https://api.openweathermap.org/data/2.5/weather";
    WeatherDaoJdbc weatherDaoJdbc;

    @Value("${apiKey:0}")
    private String apiKey;

    public WeatherServiceJdbc(WeatherDaoJdbc weatherDaoJdbc, RestTemplate restTemplate) {
        this.weatherDaoJdbc = weatherDaoJdbc;
        this.restTemplate = restTemplate;
    }

    public SimpleWeatherResponse getSimpleWeatherResponse(String city) {

        if (weatherDaoJdbc.getWeather(city) != null) {
            log.info("Wather from database {}", city);
            return weatherDaoJdbc.getWeather(city);
        } else {
            log.info("API called to {}", city);
            SimpleWeatherResponse simpleWeatherResponse = callApi(city);
            weatherDaoJdbc.addWeather(simpleWeatherResponse);
            return simpleWeatherResponse;
        }
    }

    private SimpleWeatherResponse callApi(String city) {
        String url = baseUrl + "?q=" + city + "&appid=" + apiKey + "&units=metric&lang=hu";
        WeatherResponse weatherResponse = restTemplate.getForObject(
                url, WeatherResponse.class);
        return new SimpleWeatherResponse(weatherResponse);
    }

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    private boolean deleteCities() {
        return weatherDaoJdbc.deleteAll();
    }

}
